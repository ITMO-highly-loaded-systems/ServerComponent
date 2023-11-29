package com.storage;

import com.storage.FileSystem.DiskFileSystem;
import com.storage.MM.Interfaces.IMemTable;
import com.storage.MM.MemTable;
import com.storage.MM.MemTableDecorator;
import com.storage.SS.SSManager;
import com.storage.SS.SSTableNameGenerator;
import com.storage.service.GzipCompressor;
import com.storage.service.Interfaces.IWal;
import com.storage.service.SsService;
import com.storage.service.WAL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.FileReader;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;


@EnableScheduling
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class);
    }

    @Bean
    public LSM<String, String> createLsm() throws IOException {
        FileReader reader = new FileReader("src/main/resources/application.properties");
        Properties lsmProp = new Properties();
        lsmProp.load(reader);

        String dir = lsmProp.getProperty("dir");
        int blockSize = Integer.parseInt(lsmProp.getProperty("blockSize"));
        IMemTable<String, String> memTable = new MemTable<String, String>(3);
        var fs2 = new DiskFileSystem(new File(dir), new GzipCompressor());
        var fs = new DiskFileSystem(new File(dir), new GzipCompressor());
        IWal wal = new WAL(fs2, "-", ";", "wal.txt");
        IMemTable<String, String> memTableDecorator = new MemTableDecorator<String, String>(memTable, wal);
        var ssService = new SsService(fs, blockSize, "-", ";");
        var ssTable = new SSManager<String, String>(new SSTableNameGenerator(), ssService);
        return new LSM<>(memTableDecorator, ssTable);
    }
}

package com.storage;

import com.storage.utils.IMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Merge {

    @Autowired
    public Merge(IMerger lsm) {
        this.lsm = lsm;
    }

    private final IMerger lsm;

    @Scheduled(fixedDelayString = "${interval}")
    public void merge() throws IOException {
        lsm.merge();
    }

}

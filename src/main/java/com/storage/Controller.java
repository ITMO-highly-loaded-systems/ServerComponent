package com.storage;


import com.storage.Entities.KVPair;
import com.storage.utils.DBOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/keys")
public class Controller {

    DBOperations<String, String> service;

    @Autowired
    Controller(DBOperations<String, String> lsm) {
        service = lsm;
    }

    @GetMapping("/get/{key}")
    public String get(@PathVariable String key) {
        return service.get(key);
    }

    @RequestMapping("/set/{key}/{value}")
    public String set(@PathVariable String key, @PathVariable String value) throws IOException {
        KVPair pair = new KVPair(key, value);
        service.set(pair);
        return value;
    }

}

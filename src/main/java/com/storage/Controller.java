package com.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.storage.Entities.KVPair;
import com.storage.exceptions.ApplicationException;
import com.storage.utils.DBOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

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

    @GetMapping("/getWal")
    public String get() {
        var list = service.geyWal();
        Gson gson = new Gson();
        String jsonList = gson.toJson(list);
        return jsonList;
    }

}

package com.eminenceinnovation.enc.controller;

import com.eminenceinnovation.enc.model.MatchAllDetails;
import com.eminenceinnovation.enc.model.MatchDatum;
import com.eminenceinnovation.enc.service.MyService;
import com.eminenceinnovation.enc.util.AESUtil;
import com.eminenceinnovation.enc.util.AsyncHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/enc/v1")
class MyController {

    @Autowired
    MyService service;

    @PostMapping("/matches")
    public String getMatches(@RequestBody String input) throws Exception {

        List<MatchDatum> drawMatches = service.getMatches(input);

        String res = String.valueOf(drawMatches.size());
        Thread.sleep(5000);

        return service.encryptData(res);
    }

}
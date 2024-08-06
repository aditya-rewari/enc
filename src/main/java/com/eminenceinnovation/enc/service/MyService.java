package com.eminenceinnovation.enc.service;

import com.eminenceinnovation.enc.model.MatchAllDetails;
import com.eminenceinnovation.enc.model.MatchDatum;
import com.eminenceinnovation.enc.util.AESUtil;
import com.eminenceinnovation.enc.util.AsyncHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class MyService {

    private final String keyBase64 = "H2I/Rz1GnV/2f3/z7iG8j4zO0flHqFqdtFJH2LmltAk=";

    private final String url = "https://jsonmock.hackerrank.com/api/football_matches?year=2011&page=1";

    public List<MatchDatum> getMatches(String input) throws Exception {
        // decrypt the input
        byte[] decodedKey = Base64.getDecoder().decode(keyBase64);
        SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        String data = AESUtil.decrypt(input, key); // exception to be handles centrally
        // fetch input year from it

        // async call 3rd party api
        AtomicReference<String> res = new AtomicReference<>("");
//        AtomicReference<ArrayList<MatchDatum>> res = new AtomicReference<>();
        CompletableFuture<String> futureResponse = AsyncHttpClient.makeAsyncGetRequest(url);
        futureResponse.thenAccept(response -> {
            res.set(response);
            System.out.println("Response: " + response);
        }).exceptionally(ex -> {
            System.err.println("Error: " + ex.getMessage());
            return null;
        });

        // filter the data as per year and matches which are drawn
        ObjectMapper mapper = new ObjectMapper();
        MatchAllDetails matchAllDetails = mapper.readValue(res.get(), MatchAllDetails.class);
        List<MatchDatum> drawMatches = matchAllDetails.getData()
                .stream()
                .filter(matchDatum -> matchDatum.getTeam1goals()==matchDatum.getTeam2goals())
                .collect(Collectors.toList());
        return drawMatches;
    }

    public String encryptData(String data) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(keyBase64);
        SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        return AESUtil.encrypt(data, key);
    }
}

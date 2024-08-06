package com.eminenceinnovation.enc.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class AsyncHttpClient {

    // Method to make an asynchronous HTTP GET request
    public static CompletableFuture<String> makeAsyncGetRequest(String urlString) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Create URL object
                URL url = new URL(urlString);

                // Open a connection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                // Check the response code
                int responseCode = conn.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new RuntimeException("HTTP GET Request Failed with Error code : " + responseCode);
                }

                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                // Return the response
                return response.toString();

            } catch (Exception e) {
                throw new RuntimeException("Exception occurred while making HTTP request: " + e.getMessage(), e);
            }
        });
    }

}
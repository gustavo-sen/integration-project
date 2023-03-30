package com.eletra.helper.db;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PUTRequest {

    private static final String BASEURL = "http://localhost:8080/api/";

    private static String connection(String urlParaChamada){
        StringBuilder rawJson = new StringBuilder();
        try {
            URL url = new URL(urlParaChamada.replaceAll(" ","%20"));
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("POST");

            if (conexao.getResponseCode() != 200){
                throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
            String output;
            while ((output = br.readLine()) != null){
                rawJson.append(output);
            }
            conexao.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rawJson.toString();
    }

}

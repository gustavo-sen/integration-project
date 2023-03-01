package com.eletra.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebService {

    private static final String baseURL = "http://localhost:8080/api/";

    //entity [lineups,category,models..]
    //fromFilter [functions like "FROM" operation]
    public static String getListOfEntities(String entity,String fromFilter) {
        return connection(String.format("%s/%s/%s",baseURL,entity,fromFilter));
    }
    public static String getListOfEntities(String entity) {
        return connection(String.format("%s/%s",baseURL,entity));
    }

    //Make connection with backend
    private static String connection(String urlParaChamada){

        StringBuilder rawJson = new StringBuilder();

        try {
            URL url = new URL(urlParaChamada.replaceAll(" ","%20"));
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");

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

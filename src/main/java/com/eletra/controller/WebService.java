package com.eletra.controller;

import com.google.gson.JsonObject;

import javax.swing.text.html.parser.Entity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class WebService {

    private static String baseURL = "http://localhost:8080/api/";

    public static String getListOfEntities(String entity,String filter) {
        return connection(String.format("%s/%s/%s",baseURL,entity,filter));
    }
    public static String getListOfEntities(String entity) {
        return connection(String.format("%s/%s",baseURL,entity));
    }

    public static String connection(String urlParaChamada){
        String json = "";

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
                json += output;
            }
            conexao.disconnect();

        } catch (Exception e) {
            System.out.println(e);
        }
        return json;
    }

}

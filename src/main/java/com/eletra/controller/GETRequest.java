package com.eletra.controller;

import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineupDTO;
import com.eletra.dto.ModeDTO;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static java.util.Arrays.asList;

public class GETRequest {

    private static final String baseURL = "http://localhost:8080/api/";
    //entity [lineups,category,models..]
    //fromFilter [functions like "FROM" operation]
    private static String getListOfEntities(String entity,String fromFilter) {
        return connection(String.format("%s/%s/%s",baseURL,entity,fromFilter));
    }
    public static List<LineupDTO> getListOfLineups() {
        return  asList(new Gson().fromJson(GETRequest.getListOfEntities("lineups"), LineupDTO[].class));
    }
    public static List<CategoryDTO> getListOfCategories(){
        return asList(new Gson().fromJson(GETRequest.getListOfEntities("categories"), CategoryDTO[].class));
    }
    public static List<ModeDTO> getListOfModels(){
        return asList(new Gson().fromJson(GETRequest.getListOfEntities("models"), ModeDTO.class));
    }

    private static String getListOfEntities(String entity) {
        return connection(String.format("%s/%s",baseURL,entity));
    }

    //Make connection with backend
    private static String connection(String urlParaChamada){

        StringBuilder rawJson = new StringBuilder();

        try {
            URL url = new URL(urlParaChamada.replaceAll(" ","%20"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() != 200){
                throw new RuntimeException("HTTP error code : " + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            String output;

            while ((output = br.readLine()) != null){
                rawJson.append(output);
            }

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }

        return rawJson.toString();
    }

}
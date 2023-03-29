package com.eletra.controller;

import com.eletra.dto.AbstractDTO;
import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineupDTO;
import com.eletra.dto.ModeDTO;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static java.util.Arrays.asList;

public class GETRequest {

    private static final String baseURL = "http://localhost:8080/api/";
    private static HttpURLConnection connection = null;

    //entity [lineups,category,models..]
    //fromFilter [functions like "FROM" operation]

    private static String getListOfEntities(String entity,String fromFilter) {
        return getJsonFromConnection(String.format("%s/%s/%s",baseURL,entity,fromFilter));
    }

    private static String getListOfEntities(String entity) {
        return getJsonFromConnection(String.format("%s/%s",baseURL,entity));
    }

    public static List<LineupDTO> getListOfLineups() {
        return  asList(new Gson().fromJson(GETRequest.getListOfEntities("lineups"), LineupDTO[].class));
    }

    public static List<CategoryDTO> getListOfCategories(AbstractDTO fromEntity){
        return asList(new Gson().fromJson(GETRequest.getListOfEntities("categories",fromEntity.getName()), CategoryDTO[].class));
    }

    public static List<ModeDTO> getListOfModels(AbstractDTO fromEntity){
        return asList(new Gson().fromJson(GETRequest.getListOfEntities("models",fromEntity.getName()), ModeDTO.class));
    }

    //Make connection with backend
    private static void configureConnection(String urlParaChamada) {

        try {
            URL url = new URL(urlParaChamada.replaceAll(" ","%20"));
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code : " + connection.getResponseCode());
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String getJsonFromConnection(String urlParaChamada) {

        StringBuilder rawJson = new StringBuilder();
        configureConnection(urlParaChamada);

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            String output;

            while ((output = br.readLine()) != null){
                rawJson.append(output);
            }

            connection.disconnect();

        }catch (IOException e){
            return "API JSON STRUCTURE INPUT ERROR!";
        }

        return rawJson.toString();
    }

}
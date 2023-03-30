package com.eletra.helper.db;

import com.eletra.dto.CategoryDTO;
import com.eletra.dto.LineupDTO;
import com.eletra.dto.ModelDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GETRequest {

    private static final String BASE_URL = "http://localhost:8080/api/";
    private static HttpURLConnection connection = null;

    //entity [lineups,category,models..]
    //fromFilter [functions like "FROM" operation]

    private static String getListOfEntities(String entity,String fromFilter) {
        return getJsonFromAPI(BASE_URL + "/" + entity + "/" + fromFilter);
    }

    private static String getListOfEntities(String entity) {
        return getJsonFromAPI(BASE_URL + "/" + entity);
    }

    public static List<LineupDTO> getListOfLineups() {
        return new Gson().fromJson(GETRequest.getListOfEntities("lineups"), new TypeToken<List<LineupDTO>>(){}.getType());
    }

    public static List<CategoryDTO> getListOfCategoriesFromEntity(LineupDTO fromEntity){
        return new Gson().fromJson(GETRequest.getListOfEntities("categories",fromEntity.getName()), new TypeToken<List<CategoryDTO>>(){}.getType());
    }

    public static List<ModelDTO> getListOfModelsFromEntity(CategoryDTO fromEntity){
        return new Gson().fromJson(GETRequest.getListOfEntities("models",fromEntity.getName()), new TypeToken<List<ModelDTO>>(){}.getType());
    }

    //Make connection with backend
    private static void prepareRestAPIConnection(String urlParaChamada) {

        try {
            URL url = new URL(urlParaChamada.replaceAll(" ","%20"));

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if(responseCode != 200){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Connection Error Log");
                alert.setContentText("Error: " + responseCode);
                alert.showAndWait();
            }

        } catch (IOException | RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error trying to connect");
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
        }
    }

    private static String getJsonFromAPI(String url) {

        StringBuilder rawJson = new StringBuilder();
        prepareRestAPIConnection(url);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            String output;

            while ((output = br.readLine()) != null) {
                rawJson.append(output);
            }
            connection.disconnect();

        } catch (IOException e) {
            return "API JSON STRUCTURE INPUT ERROR!";
        }

        return rawJson.toString();
    }

}
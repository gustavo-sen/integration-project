package com.eletra.helper.db;

import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GETRequest {

    protected static final String BASE_URL = "http://localhost:8080/api/";
    protected static HttpURLConnection connection = null;

    public static String getJsonOfEntities(String entity, String fromFilter) {
        return getJsonFormatted(BASE_URL + "/" + entity + "/" + fromFilter);
    }

    public static String getJsonOfEntities(String entity) {
        return getJsonFormatted(BASE_URL + "/" + entity);
    }

    public static String getJsonFormatted(String connectionUrl) {

        prepareRestAPIConnection(connectionUrl);

        StringBuilder rawJson = new StringBuilder();

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

    //Make connection with backend
    protected static void prepareRestAPIConnection(String connectionUrl) {

        try {
            URL url = new URL(connectionUrl.replaceAll(" ","%20"));

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

}
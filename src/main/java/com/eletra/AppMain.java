package com.eletra;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle( "Main Menu");

        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource("/view/MainView.fxml"));
        
        Scene scene = new Scene(fxmlLoader.load(),550,400);

        primaryStage.setResizable(true);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}

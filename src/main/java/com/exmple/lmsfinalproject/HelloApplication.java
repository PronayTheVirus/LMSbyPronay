package com.exmple.lmsfinalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class HelloApplication extends Application {
    private static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1234, 797);
        stage.setTitle("TingTingLibrary Management system");
        stage.setScene(scene);
        stage.show();
    }
    public static void changeScene(String fileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource( fileName + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1234, 797);
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Failed to load scene. File name : " + fileName);
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch();
    }
}
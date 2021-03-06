package ru.rsreu.ars;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;


public class Runner extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainView.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Система автоматических отчетов");
        primaryStage.show();
    }

    public static void main(String[] args){
//        try {
//            Process process = Runtime.getRuntime().exec("java -jar checkstyle-8.2-all.jar -c PrutzkowConfiguration.xml Checkstyle.java");
//            System.out.print(process.getOutputStream().);
//        } catch (IOException e) {
//
//        }
        Locale.setDefault(new Locale("ru"));
         launch(args);
    }
}

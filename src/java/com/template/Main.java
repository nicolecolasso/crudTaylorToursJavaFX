package com.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/* Carrega o contêiner FXML principal e inicializa o palco gráfico da aplicação. */

public class Main extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(loader.load(),900,430);

        stage.setTitle("Cadastro Tour Taylor Swift");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
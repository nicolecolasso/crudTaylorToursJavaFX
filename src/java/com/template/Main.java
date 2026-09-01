package com.template;

import com.template.controller.MainController;
import com.template.validator.ITaylorToursValidador;
import com.template.validator.TaylorToursValidator;
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
        ITaylorToursValidador tourValidador = new TaylorToursValidator();

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("main.fxml"));

        loader.setControllerFactory(controllerClass -> {
            if(controllerClass == MainController.class){
                return new MainController(tourValidador);
            }
            try{
                return controllerClass.newInstance();
            } catch(Exception e){
                throw new RuntimeException(e);
            }
        });

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
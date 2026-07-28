package com.template.util;

import javafx.scene.control.Alert;

public class DialogUtil {
    public static void showInformation(){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("Sobre o Sistema: Gerenciador de Turnês Taylor Swift");
        alerta.setHeaderText("Este programa permite o controle sobre a história dos palcos de uma das maiores artistas do século,\n desde a Fearless Tour até o fenômeno global The Eras Tour");
        alerta.setContentText("Bem-vindo ao Sistema de Histórico de Turnês!\n" +
                "Aqui você pode cadastrar novas datas, atualizar o faturamento dos shows, listar as turnês de cada era e deletar registros antigos. \n" +
                "Explore dados sobre as datas, álbuns base e quantidade de shows que definiram a trajetória da Taylor Swift nos palcos do mundo inteiro.");

        alerta.showAndWait();
    }
}

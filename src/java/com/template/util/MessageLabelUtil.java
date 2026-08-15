package com.template.util;

import javafx.scene.control.Label;

public class MessageLabelUtil {
    public static void mostrarAviso(Label lbl, String texto, String cor) {
        lbl.setText(texto);
        lbl.setWrapText(true);
        lbl.setStyle("-fx-text-fill: " + cor + "; -fx-font-weight: bold; -fx-alignment: center;");
    }

    public static void limparAviso(Label lbl) {
        lbl.setText("");
    }
}

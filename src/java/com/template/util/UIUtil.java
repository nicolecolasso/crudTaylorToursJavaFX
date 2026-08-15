package com.template.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class UIUtil {

    private static final String[] FONTES = {
            "Antonio.ttf", "EBGaramond.ttf", "GreatVibe.ttf", "ImperialScript.ttf",
            "InstrumentSerif.ttf", "Inter.ttf", "Montserrat.ttf",
            "OPTIEngraversOldEnglish.ttf", "Oswald.ttf", "PermanentMarker.ttf",
            "the Rochester.ttf"
    };

    public static void carregarFontes() {
        for (String fonte : FONTES) {
            Font.loadFont(UIUtil.class.getResourceAsStream("/com/template/fonts/" + fonte), 12);
        }
    }

    public static void carregarImagem(ImageView imageView, String caminho) {
        imageView.setImage(new Image(UIUtil.class.getResourceAsStream(caminho)));
    }
}
package com.template.validator;

import com.template.model.dto.TaylorToursDTO;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class TaylorTourValidator {

    public static boolean validarCampos(TextField nome, TextField album, DatePicker data) {
        return nome.getText().trim().isEmpty()
                || album.getText().trim().isEmpty()
                || data.getValue() == null;
    }

    public static boolean validarNumeric(TextField shows, TextField faturamento) {
        try {
            if (!shows.getText().isEmpty()) Integer.parseInt(shows.getText().trim());
            if (!faturamento.getText().isEmpty()) Double.parseDouble(faturamento.getText().trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validarLinha(TaylorToursDTO tour) {
        return tour == null;
    }

    public static int converterQtdeShows(TextField shows) {
        try {
            return Integer.parseInt(shows.getText().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public static double converterFaturamentoEstimado(TextField faturamento) {
        try {
            return Double.parseDouble(faturamento.getText().trim());
        } catch (Exception e) {
            return 0.0;
        }
    }
}
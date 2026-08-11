package com.template.validator;

import com.template.model.dto.TaylorToursDTO;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class TaylorTourValidator {

    public static boolean validarCampos(TextField txtNome, TextField txtAlbumBase, DatePicker dpDataInicio){
        return txtNome.getText().trim().isEmpty() || txtAlbumBase.getText().trim().isEmpty() || dpDataInicio.getValue() == null;
    }

    public static boolean validarNumeric(TextField txtQtdeShows, TextField txtFaturamentoEstimado){
        try {
            if (!txtQtdeShows.getText().trim().isEmpty()) {
                Integer.parseInt(txtQtdeShows.getText().trim());
            }
            if (!txtFaturamentoEstimado.getText().trim().isEmpty()) {
                Double.parseDouble(txtFaturamentoEstimado.getText().trim());
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validarLinha(TaylorToursDTO tourSelecionada) {
        return tourSelecionada == null;
    }

    public static int converterQtdeShows(TextField txtQtdeShows){
        int quantidadeShows = txtQtdeShows.getText().isEmpty() ? 0 : Integer.parseInt(txtQtdeShows.getText());
        return quantidadeShows;
    }

    public static double converterFaturamentoEstimado(TextField txtFaturamentoEstimado){
        double faturamentoEstimado = txtFaturamentoEstimado.getText().isEmpty() ? 0 : Double.parseDouble(txtFaturamentoEstimado.getText());
        return faturamentoEstimado;
    }
}

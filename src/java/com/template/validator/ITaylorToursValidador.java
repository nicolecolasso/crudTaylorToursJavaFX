package com.template.validator;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public interface ITaylorToursValidador {
    boolean validarTudo(TextField nome, TextField album, DatePicker data, TextField shows, TextField faturamento, Label lblMensagem);

}

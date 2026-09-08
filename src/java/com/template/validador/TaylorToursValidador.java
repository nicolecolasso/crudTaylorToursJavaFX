package com.template.validador;

import com.template.util.MessageLabelUtil;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;

public class TaylorToursValidador implements ITaylorToursValidador {

    public boolean validarTudo(TextField nome, TextField album, DatePicker data, TextField shows, TextField faturamento, Label lblMensagem) {
        List<Validador<?>> validadores = new ArrayList<>();

        MessageLabelUtil.limparAviso(lblMensagem);

        //Campos Obrigatórios de Texto
        validadores.add(new CamposObrigatoriosValidador("Nome", nome.getText()));
        validadores.add(new CamposObrigatoriosValidador("Álbum", album.getText()));

        // Validação da Data
        if (data.getValue() == null) {
            validadores.add(new CamposObrigatoriosValidador("Data", null));
        } else {
            validadores.add(new AnoTurneValidador(data.getValue()));
        }

        //Validações Numéricas usando o Converter
        validadores.add(new QuantidadeShowsValidador(shows.getText()));
        validadores.add(new FaturamentoValidador(faturamento.getText()));

        //Execução sequencial dos validadores
        for (Validador<?> validador : validadores) {
            if (!validador.validar()) {
                MessageLabelUtil.mostrarAviso(lblMensagem, validador.getMensagemErro(), "red");
                return false;
            }
        }
        return true;
    }
}
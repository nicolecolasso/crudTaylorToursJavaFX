package com.template.validator;

import com.template.model.dto.TaylorToursDTO;
import com.template.util.MessageLabelUtil;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;

public class TaylorToursValidator {

    public static boolean validarTudo(TextField nome, TextField album, DatePicker data, TextField shows, TextField faturamento, Label lblMensagem) {
        List<Validador<?>> validadores = new ArrayList<>();

        MessageLabelUtil.limparAviso(lblMensagem);

        //Campos Obrigatórios de Texto
        validadores.add(new CampoObrigatorioValidador("Nome", nome.getText()));
        validadores.add(new CampoObrigatorioValidador("Álbum", album.getText()));

        // Validação da Data
        if (data.getValue() == null) {
            validadores.add(new CampoObrigatorioValidador("Data", null));
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

    public static boolean validarLinhaSelecionada(Label lblMensagem, TaylorToursDTO tour) {
        LinhaSelecionadaValidador validador = new LinhaSelecionadaValidador(tour);

        if (!validador.validar()) {
            MessageLabelUtil.mostrarAviso(lblMensagem, validador.getMensagemErro(), "red");
            return false;
        }

        return true;
    }
}
package com.template.validator;

import com.template.converter.TaylorToursConverter;

public class FaturamentoValidador implements Validador<String> {
    private final String valor;

    public FaturamentoValidador(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar() {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(valor.trim());
            return TaylorToursConverter.converterFaturamentoEstimado(valor) > 0.0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getMensagemErro() {
        return "O campo Faturamento Estimado deve conter um valor numérico válido e maior que zero.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}
package com.template.validator;

import com.template.converter.TaylorToursConverter;

public class QuantidadeShowsValidador implements Validador<String> {
    private final String valor;

    public QuantidadeShowsValidador(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar() {
        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }
        try {
            // Se não for um inteiro válido, Integer.parseInt lança NumberFormatException
            Integer.parseInt(valor.trim());
            // Garante que o valor convertido não seja negativo
            return TaylorToursConverter.converterQtdeShows(valor) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getMensagemErro() {
        return "O campo Quantidade de Shows deve conter um número inteiro válido e maior que zero.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}
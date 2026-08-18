package com.template.validator;

import java.time.LocalDate;

public class AnoTurneValidador implements Validador<LocalDate>{
    private final LocalDate valor;

    public AnoTurneValidador(LocalDate valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(LocalDate valor) {
        int ano = valor.getYear();
        return !((ano > 2009) && (ano < 2023));
    }

    @Override
    public String getMensagemErro() {
        return "A primeira tour - The Fearless Tour - teve início em 2009 e a última - The Eras Tour - teve início em 2023. Adicione um ano válido";
    }

    @Override
    public LocalDate getValor() {
        return valor;
    }
}

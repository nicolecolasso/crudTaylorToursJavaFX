package com.template.validador;

public interface Validador <T> {
    boolean validar();
    String getMensagemErro();
    T getValor();
}

package com.template.validator;

public interface Validador <T> {
    boolean validar();
    String getMensagemErro();
    T getValor();
}

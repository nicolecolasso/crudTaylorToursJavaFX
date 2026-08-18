package com.template.validator;

import com.template.model.dto.TaylorToursDTO;

public class LinhaSelecionadaValidador implements Validador<TaylorToursDTO> {
    private final TaylorToursDTO tour;

    public LinhaSelecionadaValidador(TaylorToursDTO tour) {
        this.tour = tour;
    }

    @Override
    public boolean validar() {
        return this.tour != null;
    }

    @Override
    public String getMensagemErro() {
        return "Selecione uma tour na tabela para realizar esta ação!";
    }

    @Override
    public TaylorToursDTO getValor() {
        return tour;
    }
}
package com.template.services;

import com.template.model.dao.TaylorToursDAO;
import com.template.model.dto.TaylorToursDTO;
import java.util.ArrayList;

/*
    Classe de Serviço da aplicação.
    Responsabilidade: Concentrar as regras de negócio e intermediar
    as operações entre o Controller e o DAO.
*/
public class TaylorToursService {

    private final TaylorToursDAO tourDAO = new TaylorToursDAO();

    public ArrayList<TaylorToursDTO> buscarTodas() {
        return tourDAO.visualizarTour();
    }

    public void salvar(TaylorToursDTO tourDTO) {
        tourDAO.cadastrarTour(tourDTO);
    }

    public void atualizar(TaylorToursDTO tourDTO) {
        tourDAO.alterarTour(tourDTO);
    }

    public void deletar(int id) {
        tourDAO.excluirTour(id);
    }
}
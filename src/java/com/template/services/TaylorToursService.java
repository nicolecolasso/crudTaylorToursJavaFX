package com.template.services;

import com.template.converter.TaylorToursConverter;
import com.template.model.dao.TaylorToursDAO;
import com.template.model.dto.TaylorToursDTO;
import java.time.LocalDate;
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

    // TaylorToursService
    public void cadastrar(String nome, String album, LocalDate data, String qtdeShows, String faturamento) {
        TaylorToursDTO dto = montarDTO(null, nome, album, data, qtdeShows, faturamento);
        tourDAO.cadastrarTour(dto);
    }

    public void atualizar(int id, String nome, String album, LocalDate data, String qtdeShows, String faturamento) {
        TaylorToursDTO dto = montarDTO(id, nome, album, data, qtdeShows, faturamento);
        tourDAO.alterarTour(dto);
    }

    private TaylorToursDTO montarDTO(Integer id, String nome, String album, LocalDate data, String qtdeShows, String faturamento) {
        TaylorToursDTO dto = new TaylorToursDTO();
        if (id != null) dto.setIdTour(id);
        dto.setNomeTour(nome);
        dto.setAlbumBase(album);
        dto.setDataInicio(data);
        dto.setQuantidadeShows(TaylorToursConverter.converterQtdeShows(qtdeShows));
        dto.setFaturamentoEstimado(TaylorToursConverter.converterFaturamentoEstimado(faturamento));
        return dto;
    }

    public void deletar(int id) {
        tourDAO.excluirTour(id);
    }
}
package com.template.model.dao;

import com.template.model.Conexao;
import com.template.model.dto.TaylorToursDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
    Classe DAO (Data Access Object)
    Responsável por centralizar operações CRUD.
    Torna o código mais organizado, seguro e fácil de manter, evitando SQL espalhado.
 */

public class TaylorToursDAO {

    private static final Logger logger = Logger.getLogger(TaylorToursDAO.class.getName());

    public boolean cadastrarTour(TaylorToursDTO tourDTO) {
        String sql = "INSERT INTO tour_swift (nome_tour, album_base, data_inicio, quantidade_shows, faturamento_estimado) VALUES (?, ?, ?, ?, ?)";

        // Try-with-resources: garante o fechamento automático da Conexão e PreparedStatement
        try (Connection c = new Conexao().conectaBD(); PreparedStatement ps = c.prepareStatement(sql)) {
            //Uso de PreparedStatement para garantir a segurança dos dados (evita SQL Injection)
            ps.setString(1, tourDTO.getNomeTour());
            ps.setString(2, tourDTO.getAlbumBase());
            //Converte LocalDate para o formato Date suportado
            ps.setDate(3, java.sql.Date.valueOf(tourDTO.getDataInicio()));
            ps.setInt(4, tourDTO.getQuantidadeShows());
            ps.setDouble(5, tourDTO.getFaturamentoEstimado());

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao cadastrar turne.", ex);
            return false;
        }
    }

    public ArrayList<TaylorToursDTO> visualizarTour(){
        ArrayList<TaylorToursDTO> listaTours = new ArrayList<>();
        try (Connection c = new Conexao().conectaBD(); PreparedStatement ps = c.prepareStatement("SELECT * FROM tour_swift"); ResultSet rs = ps.executeQuery()) {
            //Percorre o resultado da consulta (enquanto houver linhas do banco)
            while(rs.next()){
                TaylorToursDTO tourDTO = new TaylorToursDTO();

                tourDTO.setIdTour(rs.getInt("id_tour"));
                tourDTO.setNomeTour(rs.getString("nome_tour"));
                tourDTO.setAlbumBase(rs.getString("album_base"));
                tourDTO.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                tourDTO.setQuantidadeShows(rs.getInt("quantidade_shows"));
                tourDTO.setFaturamentoEstimado(rs.getDouble("faturamento_estimado"));

                listaTours.add(tourDTO);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao cadastrar turne.", ex);
        }
        return listaTours;
    }

    public boolean alterarTour(TaylorToursDTO tourDTO) {
        String sql = "UPDATE tour_swift SET nome_tour=?, album_base=?, data_inicio=?, quantidade_shows=?, faturamento_estimado=? WHERE id_tour=?";

        try (Connection c = new Conexao().conectaBD(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, tourDTO.getNomeTour());
            ps.setString(2, tourDTO.getAlbumBase());
            ps.setDate(3, java.sql.Date.valueOf(tourDTO.getDataInicio()));
            ps.setInt(4, tourDTO.getQuantidadeShows());
            ps.setDouble(5, tourDTO.getFaturamentoEstimado());
            ps.setInt(6, tourDTO.getIdTour()); // Diferencial, define qual registro será alterado

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao cadastrar turne.", ex);
            return false;
        }
    }

    public boolean excluirTour(int idTour) {
        String sql = "DELETE FROM tour_swift WHERE id_tour=?";

        try (Connection c = new Conexao().conectaBD(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, idTour);

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao cadastrar turne.", ex);
            return false;
        }
    }

}
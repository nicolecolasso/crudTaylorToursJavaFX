package com.template;

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

    public boolean cadastrarTour(TaylorToursDTO tour) {
        String sql = "INSERT INTO tour_swift (nome_tour, album_base, data_inicio, quantidade_shows, faturamento_estimado) VALUES (?, ?, ?, ?, ?)";

        // Try-with-resources: garante o fechamento automático da Conexão e PreparedStatement
        try (Connection c = new Conexao().conectaBD(); PreparedStatement ps = c.prepareStatement(sql);) {
            //Uso de PreparedStatement para garantir a segurança dos dados (evita SQL Injection)
            ps.setString(1, tour.getNomeTour());
            ps.setString(2, tour.getAlbumBase());
            //Converte LocalDate para o formato Date suportado
            ps.setDate(3, java.sql.Date.valueOf(tour.getDataInicio()));
            ps.setInt(4, tour.getQuantidadeShows());
            ps.setDouble(5, tour.getFaturamentoEstimado());

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao cadastrar turne.", ex);
            return false;
        }
    }

    public ArrayList<TaylorToursDTO> visualizarTour(){
        ArrayList<TaylorToursDTO> listaTours = new ArrayList<>();
        try (Connection c = new Conexao().conectaBD(); PreparedStatement ps = c.prepareStatement("SELECT * FROM tour_swift"); ResultSet rs = ps.executeQuery();) {
            //Percorre o resultado da consulta (enquanto houver linhas do banco)
            while(rs.next()){
                TaylorToursDTO tour = new TaylorToursDTO();

                tour.setIdTour(rs.getInt("id_tour"));
                tour.setNomeTour(rs.getString("nome_tour"));
                tour.setAlbumBase(rs.getString("album_base"));
                tour.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                tour.setQuantidadeShows(rs.getInt("quantidade_shows"));
                tour.setFaturamentoEstimado(rs.getDouble("faturamento_estimado"));

                listaTours.add(tour);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao cadastrar turne.", ex);
        }
        return listaTours;
    }

    public boolean alterarTour(TaylorToursDTO tour) {
        String sql = "UPDATE tour_swift SET nome_tour=?, album_base=?, data_inicio=?, quantidade_shows=?, faturamento_estimado=? WHERE id_tour=?";

        try (Connection c = new Conexao().conectaBD(); PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setString(1, tour.getNomeTour());
            ps.setString(2, tour.getAlbumBase());
            ps.setDate(3, java.sql.Date.valueOf(tour.getDataInicio()));
            ps.setInt(4, tour.getQuantidadeShows());
            ps.setDouble(5, tour.getFaturamentoEstimado());
            ps.setInt(6, tour.getIdTour()); // Diferencial, define qual registro será alterado

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao cadastrar turne.", ex);
            return false;
        }
    }

    public boolean excluirTour(int idTour) {
        String sql = "DELETE FROM tour_swift WHERE id_tour=?";

        try (Connection c = new Conexao().conectaBD(); PreparedStatement ps = c.prepareStatement(sql);){
            ps.setInt(1, idTour);

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao cadastrar turne.", ex);
            return false;
        }
    }

}
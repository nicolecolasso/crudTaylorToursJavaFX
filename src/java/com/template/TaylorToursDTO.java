package com.template;

import java.time.LocalDate;

public class TaylorToursDTO {
    private int idTour;
    private String nomeTour;
    private String albumBase;
    private java.time.LocalDate dataInicio;
    private int quantidadeShows;
    private double faturamentoEstimado;

    public int getIdTour() {
        return idTour;
    }

    public void setIdTour(int idTour) {
        this.idTour = idTour;
    }

    public String getNomeTour() {
        return nomeTour;
    }

    public void setNomeTour(String nomeTour) {
        this.nomeTour = nomeTour;
    }

    public String getAlbumBase() {
        return albumBase;
    }

    public void setAlbumBase(String albumBase) {
        this.albumBase = albumBase;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public int getQuantidadeShows() {
        return quantidadeShows;
    }

    public void setQuantidadeShows(int quantidadeShows) {
        this.quantidadeShows = quantidadeShows;
    }

    public double getFaturamentoEstimado() {
        return faturamentoEstimado;
    }

    public void setFaturamentoEstimado(double faturamentoEstimado) {
        this.faturamentoEstimado = faturamentoEstimado;
    }
}

package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController
{
    @FXML private Button btnSalvar;
    @FXML private Button btnEditar;
    @FXML private Button btnDeletar;
    @FXML private Button btnLimpar;
    @FXML private TableView<TaylorToursDTO> tblTaylorTours;
    @FXML private TableColumn<TaylorToursDTO, Integer> colId;
    @FXML private TableColumn<TaylorToursDTO, String> colNome;
    @FXML private TableColumn<TaylorToursDTO, String> colAlbumBase;
    @FXML private TableColumn<TaylorToursDTO, LocalDate> colDataInicio;
    @FXML private TableColumn<TaylorToursDTO, Integer> colQtdeShows;
    @FXML private TableColumn<TaylorToursDTO, Double> colFaturamento;
    @FXML private TextField txtId;
    @FXML private DatePicker dpDataInicio;
    @FXML private TextField txtNome;
    @FXML private TextField txtQtdeShows;
    @FXML private TextField txtFaturamentoEstimado;
    @FXML private TextField txtAlbumBase;


    @FXML
    private void carregarTours(){
        TaylorToursDAO objToursDAO = new TaylorToursDAO();
        ArrayList<TaylorToursDTO> listaTours = objToursDAO.visualizarTour();
        tblTaylorTours.setItems(FXCollections.observableArrayList(listaTours));
    }

    @FXML
    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtAlbumBase.clear();
        dpDataInicio.setValue(null);
        txtQtdeShows.clear();
        txtFaturamentoEstimado.clear();
        tblTaylorTours.getSelectionModel().clearSelection();
    }

    @FXML
    private void carregarCampos() {
        TaylorToursDTO tourDto = tblTaylorTours.getSelectionModel().getSelectedItem();

        if (tourDto != null) {
            txtId.setText(String.valueOf(tourDto.getIdTour()));
            txtNome.setText(tourDto.getNomeTour());
            txtAlbumBase.setText(tourDto.getAlbumBase());
            dpDataInicio.setValue(tourDto.getDataInicio());
            txtQtdeShows.setText(String.valueOf(tourDto.getQuantidadeShows()));
            txtFaturamentoEstimado.setText(String.valueOf(tourDto.getFaturamentoEstimado()));
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb){
        colId.setCellValueFactory(new PropertyValueFactory<>("id_tour"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome_tour"));
        colAlbumBase.setCellValueFactory(new PropertyValueFactory<>("album_base"));
        colDataInicio.setCellValueFactory(new PropertyValueFactory<>("data_inicio"));
        colQtdeShows.setCellValueFactory(new PropertyValueFactory<>("quantidade_shows"));
        colFaturamento.setCellValueFactory(new PropertyValueFactory<>("faturamento_estimado"));
        carregarTours();
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        String nomeTour = txtNome.getText();
        String albumBase = txtAlbumBase.getText();
        java.time.LocalDate dataInicio = dpDataInicio.getValue();
        int quantidadeShows = Integer.parseInt(txtQtdeShows.getText());
        double faturamentoEstimado = Double.parseDouble(txtFaturamentoEstimado.getText());

        TaylorToursDTO tourDto = new TaylorToursDTO();
        tourDto.setNomeTour(nomeTour);
        tourDto.setAlbumBase(albumBase);
        tourDto.setDataInicio(dataInicio);
        tourDto.setQuantidadeShows(quantidadeShows);
        tourDto.setFaturamentoEstimado(faturamentoEstimado);

        TaylorToursDAO tourDao = new TaylorToursDAO();
        tourDao.cadastrarTour(tourDto);

        carregarTours();
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        TaylorToursDTO tourSelecionada = tblTaylorTours.getSelectionModel().getSelectedItem();

        if (tourSelecionada != null) {
            TaylorToursDTO tourDto = new TaylorToursDTO();

            tourDto.setIdTour(tourSelecionada.getIdTour());
            tourDto.setNomeTour(txtNome.getText());
            tourDto.setAlbumBase(txtAlbumBase.getText());
            tourDto.setDataInicio(dpDataInicio.getValue());
            tourDto.setQuantidadeShows(Integer.parseInt(txtQtdeShows.getText()));
            tourDto.setFaturamentoEstimado(Double.parseDouble(txtFaturamentoEstimado.getText()));

            TaylorToursDAO tourDao = new TaylorToursDAO();

            tourDao.alterarTour(tourDto);

            carregarTours();
            limparCampos();
        }

    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        TaylorToursDTO tourSelecionada = tblTaylorTours.getSelectionModel().getSelectedItem();
        if (tourSelecionada != null) {
            TaylorToursDAO tourDao = new TaylorToursDAO();
            tourDao.excluirTour(tourSelecionada.getIdTour());

            carregarTours();
            limparCampos();
        }
    }
}
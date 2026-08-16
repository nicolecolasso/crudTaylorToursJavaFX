package com.template.controller;

import com.template.model.dto.TaylorToursDTO;
import com.template.services.TaylorToursService;
import com.template.util.MessageLabelUtil;
import com.template.util.UIUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.time.LocalDate;

import static com.template.util.DialogUtil.showInformation;
import static com.template.validator.TaylorToursValidator.*;

public class MainController {

    @FXML private Button btnSalvar, btnEditar, btnDeletar, btnLimpar, btnSobre;
    @FXML private TableView<TaylorToursDTO> tblTaylorTours;
    @FXML private TableColumn<TaylorToursDTO, Integer> colId, colQtdeShows;
    @FXML private TableColumn<TaylorToursDTO, String> colNome, colAlbumBase;
    @FXML private TableColumn<TaylorToursDTO, LocalDate> colDataInicio;
    @FXML private TableColumn<TaylorToursDTO, Double> colFaturamento;
    @FXML private TextField txtId, txtNome, txtAlbumBase, txtQtdeShows, txtFaturamentoEstimado;
    @FXML private DatePicker dpDataInicio;
    @FXML private ImageView imgTaylor;
    @FXML private Label lblMensagem;

    private final TaylorToursService tourService = new TaylorToursService();

    @FXML
    public void initialize() {
        configurarColunasTabela();
        UIUtil.carregarImagem(imgTaylor, "/com/template/taylor.png");
        UIUtil.carregarFontes();
        carregarTours();
    }

    private void configurarColunasTabela() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idTour"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeTour"));
        colAlbumBase.setCellValueFactory(new PropertyValueFactory<>("albumBase"));
        colDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colQtdeShows.setCellValueFactory(new PropertyValueFactory<>("quantidadeShows"));
        colFaturamento.setCellValueFactory(new PropertyValueFactory<>("faturamentoEstimado"));
    }

    private void carregarTours() {
        tblTaylorTours.setItems(FXCollections.observableArrayList(tourService.buscarTodas()));
    }

    @FXML
    private void carregarCampos() {
        TaylorToursDTO tourDTO = tblTaylorTours.getSelectionModel().getSelectedItem();
        if (tourDTO != null) {
            txtId.setText(String.valueOf(tourDTO.getIdTour()));
            txtNome.setText(tourDTO.getNomeTour());
            txtAlbumBase.setText(tourDTO.getAlbumBase());
            dpDataInicio.setValue(tourDTO.getDataInicio());
            txtQtdeShows.setText(String.valueOf(tourDTO.getQuantidadeShows()));
            txtFaturamentoEstimado.setText(String.valueOf(tourDTO.getFaturamentoEstimado()));

            btnEditar.setDisable(false);
            btnDeletar.setDisable(false);
            btnSalvar.setDisable(true);
            MessageLabelUtil.limparAviso(lblMensagem);
        }
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

        MessageLabelUtil.limparAviso(lblMensagem);

        btnSalvar.setDisable(false);
        btnEditar.setDisable(true);
        btnDeletar.setDisable(true);
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        if (validarCampos(txtNome, txtAlbumBase, dpDataInicio)) {
            MessageLabelUtil.mostrarAviso(lblMensagem, "Preencha todos os campos obrigatórios!", "red");
            return;
        }
        if (!validarNumeric(txtQtdeShows, txtFaturamentoEstimado)) {
            MessageLabelUtil.mostrarAviso(lblMensagem, "Shows e Faturamento devem ser números válidos.", "red");
            return;
        }

        tourService.cadastrar(txtNome.getText(), txtAlbumBase.getText(), dpDataInicio.getValue(),
                txtQtdeShows.getText(), txtFaturamentoEstimado.getText());

        MessageLabelUtil.mostrarAviso(lblMensagem, "Turnê cadastrada com sucesso!", "blue");
        carregarTours();
        limparCampos();
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        TaylorToursDTO tourSelecionada = tblTaylorTours.getSelectionModel().getSelectedItem();

        if (validarLinha(tourSelecionada)) {
            MessageLabelUtil.mostrarAviso(lblMensagem, "Selecione uma tour na tabela para editar!", "red");
            return;
        }

        if (validarCampos(txtNome, txtAlbumBase, dpDataInicio)) {
            MessageLabelUtil.mostrarAviso(lblMensagem, "Campos obrigatórios não podem ficar vazios!", "red");
            return;
        }

        if (!validarNumeric(txtQtdeShows, txtFaturamentoEstimado)) {
            MessageLabelUtil.mostrarAviso(lblMensagem, "Shows e Faturamento devem ser números válidos.", "red");
            return;
        }

        tourService.atualizar(tourSelecionada.getIdTour(), txtNome.getText(), txtAlbumBase.getText(),
                dpDataInicio.getValue(), txtQtdeShows.getText(), txtFaturamentoEstimado.getText());

        MessageLabelUtil.mostrarAviso(lblMensagem, "Turnê atualizada com sucesso!", "blue");
        carregarTours();
        limparCampos();
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        TaylorToursDTO tourSelecionada = tblTaylorTours.getSelectionModel().getSelectedItem();
        if (tourSelecionada != null) {
            tourService.deletar(tourSelecionada.getIdTour());
            MessageLabelUtil.mostrarAviso(lblMensagem, "Tour deletada com sucesso!", "blue");
            carregarTours();
            limparCampos();
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
    }

    @FXML
    private void btnSobreAction(ActionEvent event) {
        showInformation();
    }
}
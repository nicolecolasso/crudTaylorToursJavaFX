package com.template.controller;

import com.template.model.dao.TaylorToursDAO;
import com.template.model.dto.TaylorToursDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.time.LocalDate;
import java.util.ArrayList;
import static com.template.util.DialogUtil.*;
import static com.template.validator.TaylorTourValidator.*;

/*
    Classe Controladora da Interface Gráfica (View).
    Gerencia os eventos dos componentes FXML, realiza a validação dos dados de entrada
    e faz a ponte de comunicação com o DAO.
*/

public class MainController
{
    @FXML private Button btnSalvar;
    @FXML private Button btnEditar;
    @FXML private Button btnDeletar;
    @FXML private Button btnLimpar;
    @FXML private Button btnSobre;
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
    @FXML private ImageView imgTaylor;
    @FXML private Label lblMensagem;


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
        lblMensagem.setText("");
        btnSalvar.setDisable(false);
        btnEditar.setDisable(true);
        btnDeletar.setDisable(true);
    }

    private void mostrarAviso(String texto, String cor) {
        lblMensagem.setText(texto);
        lblMensagem.setWrapText(true);
        lblMensagem.setStyle("-fx-text-fill: " + cor + "; -fx-font-weight: bold; -fx-alignment: center;");
    }

    @FXML
    private void carregarCampos() {
        TaylorToursDTO tourDTO = tblTaylorTours.getSelectionModel().getSelectedItem(); //Cria objeto DTO que recebe informações da tabela

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
            lblMensagem.setText("");
        }
    }

    @FXML
    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("idTour"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeTour"));
        colAlbumBase.setCellValueFactory(new PropertyValueFactory<>("albumBase"));
        colDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colQtdeShows.setCellValueFactory(new PropertyValueFactory<>("quantidadeShows"));
        colFaturamento.setCellValueFactory(new PropertyValueFactory<>("faturamentoEstimado"));
        imgTaylor.setImage(new Image(getClass().getResourceAsStream("/com/template/taylor.png")));
        // Carregamento de fontes customizadas (.ttf)
        String[] fontes = {
                "Antonio.ttf", "EBGaramond.ttf", "GreatVibe.ttf", "ImperialScript.ttf",
                "InstrumentSerif.ttf", "Inter.ttf", "Montserrat.ttf",
                "OPTIEngraversOldEnglish.ttf", "Oswald.ttf", "PermanentMarker.ttf",
                "the Rochester.ttf"
        };

        for (String fonte : fontes) {
            javafx.scene.text.Font.loadFont(
                    getClass().getResourceAsStream("/com/template/fonts/" + fonte), 12
            );
        }
        carregarTours();
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        //Validação dos campos obrigatórios
        if (validarCampos(txtNome, txtAlbumBase, dpDataInicio)) {
            mostrarAviso("Preencha todos os campos obrigatórios!", "red");
            return;
        }
        //validar campos numéricos
        if (!validarNumeric(txtQtdeShows, txtFaturamentoEstimado)) {
            mostrarAviso("Shows e Faturamento devem ser números válidos.", "red");
            return;
        }

        String nomeTour = txtNome.getText();
        String albumBase = txtAlbumBase.getText();
        java.time.LocalDate dataInicio = dpDataInicio.getValue();

        // Validação para evitar falhas de compilação caso campos não obrigatórios sejam enviados vazios
        int quantidadeShows = converterQtdeShows(txtQtdeShows);
        double faturamentoEstimado = converterFaturamentoEstimado(txtFaturamentoEstimado);

        TaylorToursDTO tourDTO = new TaylorToursDTO();
        tourDTO.setNomeTour(nomeTour);
        tourDTO.setAlbumBase(albumBase);
        tourDTO.setDataInicio(dataInicio);
        tourDTO.setQuantidadeShows(quantidadeShows);
        tourDTO.setFaturamentoEstimado(faturamentoEstimado);

        TaylorToursDAO tourDAO = new TaylorToursDAO();
        tourDAO.cadastrarTour(tourDTO);

        mostrarAviso("Turnê cadastrada com sucesso!", "blue");

        carregarTours();
        limparCampos();
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        TaylorToursDTO tourSelecionada = tblTaylorTours.getSelectionModel().getSelectedItem();

        //confere se tem linha selecionada
        if(validarLinha(tourSelecionada)){
            mostrarAviso("Selecione uma tour na tabela para editar!", "red");
            return;
        }

        //validar campos obrigatórios
        if(validarCampos(txtNome, txtAlbumBase, dpDataInicio)){
            mostrarAviso("Campos obrigatórios não podem ficar vazios!", "red");
            return;
        }

        //validar campos numéricos
        if (!validarNumeric(txtQtdeShows, txtFaturamentoEstimado)) {
            mostrarAviso("Shows e Faturamento devem ser números válidos.", "red");
            return;
        }

        TaylorToursDTO tourDTO = new TaylorToursDTO();

        tourDTO.setIdTour(tourSelecionada.getIdTour());
        tourDTO.setNomeTour(txtNome.getText());
        tourDTO.setAlbumBase(txtAlbumBase.getText());
        tourDTO.setDataInicio(dpDataInicio.getValue());
        tourDTO.setQuantidadeShows(converterQtdeShows(txtQtdeShows));
        tourDTO.setFaturamentoEstimado(converterFaturamentoEstimado(txtFaturamentoEstimado));

        TaylorToursDAO tourDAO = new TaylorToursDAO();

        tourDAO.alterarTour(tourDTO);

        carregarTours();
        limparCampos();

    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        TaylorToursDTO tourSelecionada = tblTaylorTours.getSelectionModel().getSelectedItem();
        if (tourSelecionada != null) {
            TaylorToursDAO tourDAO = new TaylorToursDAO();
            tourDAO.excluirTour(tourSelecionada.getIdTour());

            mostrarAviso("Tour deletada com sucesso!", "blue");
            carregarTours();
            limparCampos();
        }
    }

    @FXML
    private void btnSobreAction(ActionEvent event) {
        showInformation();
    }
}
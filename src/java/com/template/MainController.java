package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.time.LocalDate;
import java.util.ArrayList;

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
        TaylorToursDTO tourDto = tblTaylorTours.getSelectionModel().getSelectedItem(); //Cria objeto DTO que recebe informações da tabela

        if (tourDto != null) {
            txtId.setText(String.valueOf(tourDto.getIdTour()));
            txtNome.setText(tourDto.getNomeTour());
            txtAlbumBase.setText(tourDto.getAlbumBase());
            dpDataInicio.setValue(tourDto.getDataInicio());
            txtQtdeShows.setText(String.valueOf(tourDto.getQuantidadeShows()));
            txtFaturamentoEstimado.setText(String.valueOf(tourDto.getFaturamentoEstimado()));
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
        if (txtNome.getText().isEmpty() || txtAlbumBase.getText().isEmpty() || dpDataInicio.getValue() == null) {
            mostrarAviso("Preencha todos os campos obrigatórios!", "red");
            return;
        }
        //try catch para garantir que todos os campos numéricos são numéricos
        try{
            String nomeTour = txtNome.getText();
            String albumBase = txtAlbumBase.getText();
            java.time.LocalDate dataInicio = dpDataInicio.getValue();
            // Validação para evitar falhas de compilação caso campos não obrigatórios sejam enviados vazios
            int quantidadeShows = txtQtdeShows.getText().isEmpty() ? 0 : Integer.parseInt(txtQtdeShows.getText());
            double faturamentoEstimado = txtFaturamentoEstimado.getText().isEmpty() ? 0 : Double.parseDouble(txtFaturamentoEstimado.getText());

            TaylorToursDTO tourDto = new TaylorToursDTO();
            tourDto.setNomeTour(nomeTour);
            tourDto.setAlbumBase(albumBase);
            tourDto.setDataInicio(dataInicio);
            tourDto.setQuantidadeShows(quantidadeShows);
            tourDto.setFaturamentoEstimado(faturamentoEstimado);

            TaylorToursDAO tourDao = new TaylorToursDAO();
            tourDao.cadastrarTour(tourDto);

            mostrarAviso("Turnê cadastrada com sucesso!", "blue");

            carregarTours();
            limparCampos();
        } catch (NumberFormatException e) {
            mostrarAviso("Shows e Faturamento devem ser números.", "red");
        }
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        TaylorToursDTO tourSelecionada = tblTaylorTours.getSelectionModel().getSelectedItem();

        if (txtNome.getText().isEmpty() || txtAlbumBase.getText().isEmpty() || dpDataInicio.getValue() == null) {
            mostrarAviso("Campos obrigatórios não podem ficar vazios!", "red");
            return;
        }
        try{
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
        }catch (NumberFormatException e) {
            mostrarAviso("Valores numéricos inválidos na edição!", "red");
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

            mostrarAviso("Tour deletada com sucesso!", "blue");
            carregarTours();
            limparCampos();
        }
    }

    @FXML
    private void btnSobreAction(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("Sobre o Sistema: Gerenciador de Turnês Taylor Swift");
        alerta.setHeaderText("Este programa permite o controle sobre a história dos palcos de uma das maiores artistas do século,\n desde a Fearless Tour até o fenômeno global The Eras Tour");
        alerta.setContentText("Bem-vindo ao Sistema de Histórico de Turnês!\n" +
                "Aqui você pode cadastrar novas datas, atualizar o faturamento dos shows, listar as turnês de cada era e deletar registros antigos. \n" +
                "Explore dados sobre as datas, álbuns base e quantidade de shows que definiram a trajetória da Taylor Swift nos palcos do mundo inteiro.");

        alerta.showAndWait();
    }
}
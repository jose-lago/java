package com.template;

import com.template.dao.PlayerDAO;
import com.template.model.PlayerDTO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtNick;

    @FXML
    private TextField txtIdade;

    @FXML
    private TextField txtTime;

    @FXML
    private TableView<PlayerDTO> tabela;

    @FXML
    private TableColumn<PlayerDTO, Integer> colId;

    @FXML
    private TableColumn<PlayerDTO, String> colNome;

    @FXML
    private TableColumn<PlayerDTO, String> colNick;

    @FXML
    private TableColumn<PlayerDTO, Integer> colIdade;

    @FXML
    private TableColumn<PlayerDTO, String> colTime;

    private PlayerDTO playerSelecionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        colNome.setCellValueFactory(
                new PropertyValueFactory<>("nome"));

        colNick.setCellValueFactory(
                new PropertyValueFactory<>("nick"));

        colIdade.setCellValueFactory(
                new PropertyValueFactory<>("idade"));

        colTime.setCellValueFactory(
                new PropertyValueFactory<>("timequejoga"));

        tabela.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, antigo, novo) -> {

                    if (novo != null) {

                        playerSelecionado = novo;

                        txtNome.setText(novo.getNome());
                        txtNick.setText(novo.getNick());
                        txtIdade.setText(
                                String.valueOf(novo.getIdade())
                        );
                        txtTime.setText(
                                novo.getTimequejoga()
                        );
                    }
                });

        carregarTabela();
    }

    public void carregarTabela() {

        PlayerDAO dao = new PlayerDAO();

        ObservableList<PlayerDTO> lista =
                FXCollections.observableArrayList(
                        dao.listarPlayers()
                );

        tabela.setItems(lista);
    }

    @FXML
    public void cadastrar() {

        PlayerDAO dao = new PlayerDAO();

        dao.cadastrarPlayer(
                txtNome.getText(),
                txtNick.getText(),
                Integer.parseInt(txtIdade.getText()),
                txtTime.getText()
        );

        limparCampos();
        carregarTabela();
    }

    @FXML
    public void editar() {

        if (playerSelecionado == null) {
            return;
        }

        PlayerDAO dao = new PlayerDAO();

        dao.editarPlayer(
                playerSelecionado.getId(),
                txtNome.getText(),
                txtNick.getText(),
                Integer.parseInt(txtIdade.getText()),
                txtTime.getText()
        );

        limparCampos();
        carregarTabela();
    }

    @FXML
    public void excluir() {

        if (playerSelecionado == null) {
            return;
        }

        PlayerDAO dao = new PlayerDAO();

        dao.deletarPlayer(
                playerSelecionado.getId()
        );

        limparCampos();
        carregarTabela();
    }

    private void limparCampos() {

        txtNome.clear();
        txtNick.clear();
        txtIdade.clear();
        txtTime.clear();

        playerSelecionado = null;
    }
}
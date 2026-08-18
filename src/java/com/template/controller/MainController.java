package com.template.controller;

import com.template.model.dao.PlayerDAO;
import com.template.model.dto.PlayerDTO;
import com.template.util.DialogUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnExcluir;

    private PlayerDTO playerSelecionado;
    private final PlayerDAO dao = new PlayerDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNick.setCellValueFactory(new PropertyValueFactory<>("nick"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("timequejoga"));

        // Habilita/Desabilita botões conforme seleção na tabela
        if (btnEditar != null) {
            btnEditar.disableProperty().bind(tabela.getSelectionModel().selectedItemProperty().isNull());
        }
        if (btnExcluir != null) {
            btnExcluir.disableProperty().bind(tabela.getSelectionModel().selectedItemProperty().isNull());
        }

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            playerSelecionado = novo;

            if (novo != null) {
                txtNome.setText(novo.getNome());
                txtNick.setText(novo.getNick());
                txtIdade.setText(String.valueOf(novo.getIdade()));
                txtTime.setText(novo.getTimequejoga());
            }
        });

        carregarTabela();
    }

    public void carregarTabela() {
        ObservableList<PlayerDTO> lista = FXCollections.observableArrayList(dao.listarPlayers());
        tabela.setItems(lista);
    }

    @FXML
    public void cadastrar() {
        if (!validarCampos()) return;

        PlayerDTO player = new PlayerDTO(
                txtNome.getText().trim(),
                txtNick.getText().trim(),
                Integer.parseInt(txtIdade.getText().trim()),
                txtTime.getText().trim()
        );

        if (dao.cadastrarPlayer(player)) {
            DialogUtil.mostrarInformacao("Sucesso", "Player cadastrado com sucesso!");
            limparCampos();
            carregarTabela();
        } else {
            DialogUtil.mostrarErro("Erro", "Não foi possível cadastrar o player.");
        }
    }

    @FXML
    public void editar() {
        if (playerSelecionado == null) {
            DialogUtil.mostrarAlerta("Atenção", "Selecione um player para editar.");
            return;
        }

        if (!validarCampos()) return;

        playerSelecionado.setNome(txtNome.getText().trim());
        playerSelecionado.setNick(txtNick.getText().trim());
        playerSelecionado.setIdade(Integer.parseInt(txtIdade.getText().trim()));
        playerSelecionado.setTimequejoga(txtTime.getText().trim());

        if (dao.editarPlayer(playerSelecionado)) {
            DialogUtil.mostrarInformacao("Sucesso", "Player atualizado com sucesso!");
            limparCampos();
            carregarTabela();
        } else {
            DialogUtil.mostrarErro("Erro", "Não foi possível atualizar o player.");
        }
    }

    @FXML
    public void excluir() {
        if (playerSelecionado == null) {
            DialogUtil.mostrarAlerta("Atenção", "Selecione um player para excluir.");
            return;
        }

        boolean confirm = DialogUtil.confirmarAcao(
                "Confirmar Exclusão",
                "Tem certeza que deseja excluir o player " + playerSelecionado.getNick() + "?"
        );

        if (confirm) {
            if (dao.deletarPlayer(playerSelecionado.getId())) {
                DialogUtil.mostrarInformacao("Sucesso", "Player excluído com sucesso!");
                limparCampos();
                carregarTabela();
            } else {
                DialogUtil.mostrarErro("Erro", "Não foi possível excluir o player.");
            }
        }
    }

    @FXML
    public void limparCampos() {
        txtNome.clear();
        txtNick.clear();
        txtIdade.clear();
        txtTime.clear();

        playerSelecionado = null;
        tabela.getSelectionModel().clearSelection();
    }

    private boolean validarCampos() {
        // 1. Verifica se os campos estão vazios
        if (txtNome.getText().trim().isEmpty() ||
                txtNick.getText().trim().isEmpty() ||
                txtIdade.getText().trim().isEmpty() ||
                txtTime.getText().trim().isEmpty()) {

            DialogUtil.mostrarAlerta("Validação", "Preencha todos os campos do formulário.");
            return false;
        }

        // 2. Validação do Nome usando o NomeValidador
        NomeValidador nomeValidador = new NomeValidador(txtNome.getText().trim());
        if (!nomeValidador.validar(txtNome.getText().trim())) {
            DialogUtil.mostrarAlerta("Validação", nomeValidador.getMensagemErro());
            return false;
        }

        // 3. Validação do campo Idade
        try {
            int idade = Integer.parseInt(txtIdade.getText().trim());
            if (idade <= 0) {
                DialogUtil.mostrarAlerta("Validação", "A idade deve ser um número inteiro maior que zero.");
                return false;
            }
        } catch (NumberFormatException e) {
            DialogUtil.mostrarAlerta("Validação", "O campo 'Idade' deve conter apenas números.");
            return false;
        }

        return true;
    }
}
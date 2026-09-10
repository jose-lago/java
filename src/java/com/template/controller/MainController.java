package com.template.controller;

import com.template.model.dao.PlayerDAO;
import com.template.model.dto.PlayerDTO;
import com.template.util.DialogUtil;
import com.template.validator.IPlayerValidador;

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

    // DAO continua responsável pelo acesso ao banco
    private final PlayerDAO dao;

    // O Controller depende da INTERFACE
    private final IPlayerValidador playerValidador;

    /*
     * O validador é recebido por injeção de dependência.
     *
     * O Controller não cria um PlayerValidador diretamente.
     */
    public MainController(IPlayerValidador playerValidador) {

        this.playerValidador = playerValidador;
        this.dao = new PlayerDAO();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Configuração das colunas da tabela
        colId.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );

        colNick.setCellValueFactory(
                new PropertyValueFactory<>("nick")
        );

        colIdade.setCellValueFactory(
                new PropertyValueFactory<>("idade")
        );

        colTime.setCellValueFactory(
                new PropertyValueFactory<>("timequejoga")
        );

        // Habilita/Desabilita os botões conforme a seleção
        if (btnEditar != null) {

            btnEditar.disableProperty().bind(
                    tabela.getSelectionModel()
                            .selectedItemProperty()
                            .isNull()
            );
        }

        if (btnExcluir != null) {

            btnExcluir.disableProperty().bind(
                    tabela.getSelectionModel()
                            .selectedItemProperty()
                            .isNull()
            );
        }

        // Detecta quando um player é selecionado na tabela
        tabela.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, antigo, novo) -> {

                    playerSelecionado = novo;

                    if (novo != null) {

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

        // Carrega os dados do banco
        carregarTabela();
    }
    public void carregarTabela() {

        ObservableList<PlayerDTO> lista =
                FXCollections.observableArrayList(
                        dao.listarPlayers()
                );

        tabela.setItems(lista);
    }
    @FXML
    public void cadastrar() {

        PlayerDTO player = criarPlayerDosCampos();

        // Validação feita através da interface
        if (!validarPlayer(player)) {
            return;
        }

        if (dao.cadastrarPlayer(player)) {

            DialogUtil.mostrarInformacao(
                    "Sucesso",
                    "Player cadastrado com sucesso!"
            );

            limparCampos();
            carregarTabela();

        } else {

            DialogUtil.mostrarErro(
                    "Erro",
                    "Não foi possível cadastrar o player."
            );
        }
    }
    @FXML
    public void editar() {

        if (playerSelecionado == null) {

            DialogUtil.mostrarAlerta(
                    "Atenção",
                    "Selecione um player para editar."
            );

            return;
        }

        PlayerDTO playerAtualizado =
                criarPlayerDosCampos();

        // Mantém o ID original
        playerAtualizado.setId(
                playerSelecionado.getId()
        );

        // Validação feita através da interface
        if (!validarPlayer(playerAtualizado)) {
            return;
        }

        // Atualiza os dados do player selecionado
        playerSelecionado.setNome(
                playerAtualizado.getNome()
        );

        playerSelecionado.setNick(
                playerAtualizado.getNick()
        );

        playerSelecionado.setIdade(
                playerAtualizado.getIdade()
        );

        playerSelecionado.setTimequejoga(
                playerAtualizado.getTimequejoga()
        );

        if (dao.editarPlayer(playerSelecionado)) {

            DialogUtil.mostrarInformacao(
                    "Sucesso",
                    "Player atualizado com sucesso!"
            );

            limparCampos();
            carregarTabela();

        } else {

            DialogUtil.mostrarErro(
                    "Erro",
                    "Não foi possível atualizar o player."
            );
        }
    }
    @FXML
    public void excluir() {

        if (playerSelecionado == null) {

            DialogUtil.mostrarAlerta(
                    "Atenção",
                    "Selecione um player para excluir."
            );

            return;
        }

        boolean confirm =
                DialogUtil.confirmarAcao(
                        "Confirmar Exclusão",
                        "Tem certeza que deseja excluir o player "
                                + playerSelecionado.getNick()
                                + "?"
                );

        if (confirm) {

            if (dao.deletarPlayer(
                    playerSelecionado.getId()
            )) {

                DialogUtil.mostrarInformacao(
                        "Sucesso",
                        "Player excluído com sucesso!"
                );

                limparCampos();
                carregarTabela();

            } else {

                DialogUtil.mostrarErro(
                        "Erro",
                        "Não foi possível excluir o player."
                );
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

        tabela.getSelectionModel()
                .clearSelection();
    }

    private PlayerDTO criarPlayerDosCampos() {

        int idade = 0;

        try {

            idade = Integer.parseInt(
                    txtIdade.getText().trim()
            );

        } catch (NumberFormatException e) {
        }

        return new PlayerDTO(
                txtNome.getText().trim(),
                txtNick.getText().trim(),
                idade,
                txtTime.getText().trim()
        );
    }
    private boolean validarPlayer(PlayerDTO player) {

        if (!playerValidador.validar(player)) {

            DialogUtil.mostrarAlerta(
                    "Validação",
                    playerValidador.getMensagemErro()
            );

            return false;
        }


        return true;
    }
}

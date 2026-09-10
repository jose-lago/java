package com.template.validator;

import com.template.model.dto.PlayerDTO;

import java.util.ArrayList;
import java.util.List;
public class PlayerValidador implements IPlayerValidador {

    private final List<Validador<String>> validadores;

    private String mensagemErro;

    public PlayerValidador() {
        validadores = new ArrayList<>();
    }

    @Override
    public boolean validar(PlayerDTO player) {

        validadores.clear();

        // Validações dos campos obrigatórios
        validadores.add(
                new CampoObrigatorioValidador(
                        "Nome",
                        player.getNome()
                )
        );

        validadores.add(
                new CampoObrigatorioValidador(
                        "Nick",
                        player.getNick()
                )
        );

        validadores.add(
                new CampoObrigatorioValidador(
                        "Idade",
                        String.valueOf(player.getIdade())
                )
        );

        validadores.add(
                new CampoObrigatorioValidador(
                        "Time",
                        player.getTimequejoga()
                )
        );

        // Validação específica do nome
        validadores.add(
                new NomeValidador(player.getNome())
        );

        // Validação específica da idade
        validadores.add(
                new IdadeValidador(String.valueOf(player.getIdade()))
        );

        /*
         * Percorre todos os validadores utilizando foreach.
         */
        for (Validador<String> validador : validadores) {

            if (!validador.validar(validador.getValor())) {

                mensagemErro = validador.getMensagemErro();

                return false;
            }
        }

        mensagemErro = "";

        return true;
    }

    @Override
    public String getMensagemErro() {
        return mensagemErro;
    }
}

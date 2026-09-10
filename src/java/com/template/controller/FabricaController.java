package com.template.controller;
import com.template.validator.IPlayerValidador;
import com.template.validator.PlayerValidador;

public class FabricaController {

    public MainController criarMainController() {
        IPlayerValidador playerValidador = new PlayerValidador();
        return new MainController(playerValidador);
    }
}

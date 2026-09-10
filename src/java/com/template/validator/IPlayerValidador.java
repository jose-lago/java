package com.template.validator;

import com.template.model.dto.PlayerDTO;
public interface IPlayerValidador {
    boolean validar(PlayerDTO player);
    String getMensagemErro();
}


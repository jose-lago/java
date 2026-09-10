package com.template.validator;
public class IdadeValidador implements Validador<String> {

    private final String valor;

    public IdadeValidador(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(String valor) {

        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }

        try {
            int idade = Integer.parseInt(valor.trim());

            return idade > 0;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getMensagemErro() {
        return "A idade deve ser um número inteiro maior que zero.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}


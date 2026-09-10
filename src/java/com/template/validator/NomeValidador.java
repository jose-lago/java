package com.template.validator;
public class NomeValidador implements Validador<String> {

    private final String valor;

    public NomeValidador(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(String valor) {

        if (valor == null) {
            return false;
        }

        String nome = valor.trim();

        if (nome.length() < 3) {
            return false;
        }

        return nome.matches("[a-zA-ZÀ-ÿ ]+");
    }

    @Override
    public String getMensagemErro() {
        return "O nome deve possuir pelo menos 3 caracteres e conter apenas letras.";
    }

    @Override
    public String getValor() {
        return valor;
    }
}

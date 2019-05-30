package com.example.android.rodar.models;

public class AvaliacaoCarona {
    private Integer idEventoCarona;
    private Float Avaliacao;
    private String Mensagem;

    public AvaliacaoCarona(Integer idEventoCarona, Float avaliacao, String mensagem) {
        this.idEventoCarona = idEventoCarona;
        Avaliacao = avaliacao;
        Mensagem = mensagem;
    }

    public AvaliacaoCarona() {
    }
}

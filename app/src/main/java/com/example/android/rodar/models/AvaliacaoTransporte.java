package com.example.android.rodar.models;

public class AvaliacaoTransporte {
    private Integer idEventoTransporte;
    private Float Avaliacao;
    private String Mensagem;

    public AvaliacaoTransporte(Integer idEventoTransporte, Float avaliacao, String mensagem) {
        this.idEventoTransporte = idEventoTransporte;
        Avaliacao = avaliacao;
        Mensagem = mensagem;
    }

    public AvaliacaoTransporte() {
    }
}

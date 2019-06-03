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

    public Integer getIdEventoCarona() {
        return idEventoCarona;
    }

    public void setIdEventoCarona(Integer idEventoCarona) {
        this.idEventoCarona = idEventoCarona;
    }

    public Float getAvaliacao() {
        return Avaliacao;
    }

    public void setAvaliacao(Float avaliacao) {
        Avaliacao = avaliacao;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }
}

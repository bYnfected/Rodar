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


    public Integer getIdEventoTransporte() {
        return idEventoTransporte;
    }

    public void setIdEventoTransporte(Integer idEventoTransporte) {
        this.idEventoTransporte = idEventoTransporte;
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

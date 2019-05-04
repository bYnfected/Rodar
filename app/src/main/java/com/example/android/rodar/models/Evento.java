package com.example.android.rodar.models;

import java.io.Serializable;

public class Evento implements Serializable {

    private Integer idEvento;
    private String dataCriacao;
    private Integer idUsuarioCriacao;
    private String nomeEvento;
    private String enderecoRua;
    private String enderecoComplemento;
    private String enderecoBairro;
    private Integer enderecoNumero;
    private String enderecoCEP;
    private String enderecoCidade;
    private String enderecoUF;
    private String urlImagemCapa;
    private String urlImagem1;
    private String urlImagem2;
    private String urlImagem3;
    private String urlImagem4;
    private String urlImagem5;
    private String dataHoraInicio;
    private String dataHoraTermino;
    private String descricaoEvento;
    private boolean Favorito;

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Integer getIdUsuarioCriacao() {
        return idUsuarioCriacao;
    }

    public void setIdUsuarioCriacao(Integer idUsuarioCriacao) {
        this.idUsuarioCriacao = idUsuarioCriacao;
    }

    public String getEnderecoRua() {
        return enderecoRua;
    }

    public void setEnderecoRua(String enderecoRua) {
        this.enderecoRua = enderecoRua;
    }

    public String getEnderecoComplemento() {
        return enderecoComplemento;
    }

    public void setEnderecoComplemento(String enderecoComplemento) {
        this.enderecoComplemento = enderecoComplemento;
    }

    public String getEnderecoBairro() {
        return enderecoBairro;
    }

    public void setEnderecoBairro(String enderecoBairro) {
        this.enderecoBairro = enderecoBairro;
    }

    public Integer getEnderecoNumero() {
        return enderecoNumero;
    }

    public void setEnderecoNumero(Integer enderecoNumero) {
        this.enderecoNumero = enderecoNumero;
    }

    public String getEnderecoCEP() {
        return enderecoCEP;
    }

    public void setEnderecoCEP(String enderecoCEP) {
        this.enderecoCEP = enderecoCEP;
    }

    public String getEnderecoCidade() {
        return enderecoCidade;
    }

    public void setEnderecoCidade(String enderecoCidade) {
        this.enderecoCidade = enderecoCidade;
    }

    public String getEnderecoUF() {
        return enderecoUF;
    }

    public void setEnderecoUF(String enderecoUF) {
        this.enderecoUF = enderecoUF;
    }

    public String getUrlImagemCapa() {
        return urlImagemCapa;
    }

    public void setUrlImagemCapa(String urlImagemCapa) {
        this.urlImagemCapa = urlImagemCapa;
    }

    public String getUrlImagem1() {
        return urlImagem1;
    }

    public void setUrlImagem1(String urlImagem1) {
        this.urlImagem1 = urlImagem1;
    }

    public String getUrlImagem2() {
        return urlImagem2;
    }

    public void setUrlImagem2(String urlImagem2) {
        this.urlImagem2 = urlImagem2;
    }

    public String getUrlImagem3() {
        return urlImagem3;
    }

    public void setUrlImagem3(String urlImagem3) {
        this.urlImagem3 = urlImagem3;
    }

    public String getUrlImagem4() {
        return urlImagem4;
    }

    public void setUrlImagem4(String urlImagem4) {
        this.urlImagem4 = urlImagem4;
    }

    public String getUrlImagem5() {
        return urlImagem5;
    }

    public void setUrlImagem5(String urlImagem5) {
        this.urlImagem5 = urlImagem5;
    }

    public String getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(String dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public String getDataHoraTermino() {
        return dataHoraTermino;
    }

    public void setDataHoraTermino(String dataHoraTermino) {
        this.dataHoraTermino = dataHoraTermino;
    }

    public String getDescricaoEvento() {
        return descricaoEvento;
    }

    public void setDescricaoEvento(String descricaoEvento) {
        this.descricaoEvento = descricaoEvento;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public boolean getFavorito() { return Favorito; }

    public void setFavorito(boolean favorito) { this.Favorito = favorito; }
}

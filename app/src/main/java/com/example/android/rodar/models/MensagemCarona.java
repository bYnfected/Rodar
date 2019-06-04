package com.example.android.rodar.models;

import java.io.Serializable;

public class MensagemCarona implements Serializable {

    public Integer idChatUsuarioEventoCarona;
    public String dataHoraInclusaoMensagem;
    public Integer idEventoCarona;
    public Carona eventoCarona;
    public Integer idUsuarioOrigem;
    public Usuario usuarioOrigem;
    public Integer idUsuarioDestino;
    public Usuario usuarioDestino;
    public String Mensagem;

    public Carona getEventoCarona() {
        return eventoCarona;
    }

    public void setEventoCarona(Carona eventoCarona) {
        this.eventoCarona = eventoCarona;
    }

    public Usuario getUsuarioOrigem() {
        return usuarioOrigem;
    }

    public void setUsuarioOrigem(Usuario usuarioOrigem) {
        this.usuarioOrigem = usuarioOrigem;
    }

    public Usuario getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(Usuario usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }

    public Integer getIdChatUsuarioEventoCarona() {
        return idChatUsuarioEventoCarona;
    }

    public void setIdChatUsuarioEventoCarona(Integer idChatUsuarioEventoCarona) {
        this.idChatUsuarioEventoCarona = idChatUsuarioEventoCarona;
    }

    public Integer getIdEventoCarona() {
        return idEventoCarona;
    }

    public void setIdEventoCarona(Integer idEventoCarona) {
        this.idEventoCarona = idEventoCarona;
    }

    public String getDataHoraInclusaoMensagem() {
        return dataHoraInclusaoMensagem;
    }

    public void setDataHoraInclusaoMensagem(String dataHoraInclusaoMensagem) {
        this.dataHoraInclusaoMensagem = dataHoraInclusaoMensagem;
    }

    public Integer getIdUsuarioOrigem() {
        return idUsuarioOrigem;
    }

    public void setIdUsuarioOrigem(Integer idUsuarioOrigem) {
        this.idUsuarioOrigem = idUsuarioOrigem;
    }

    public Integer getIdUsuarioDestino() {
        return idUsuarioDestino;
    }

    public void setIdUsuarioDestino(Integer idUsuarioDestino) {
        this.idUsuarioDestino = idUsuarioDestino;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        Mensagem = mensagem;
    }
}
package com.example.android.rodar.models;

import java.io.Serializable;

public class MensagemTransporte implements Serializable {

    public Integer idChatUsuarioEventoTransporte;
    public String dataHoraInclusaoMensagem;
    public Integer idEventoTransporte;
    public Transporte eventoTransporte;
    public Integer idUsuarioOrigem;
    public Usuario usuarioOrigem;
    public Integer idUsuarioDestino;
    public Usuario usuarioDestino;
    public String Mensagem;

    public Integer getIdChatUsuarioEventoTransporte() {
        return idChatUsuarioEventoTransporte;
    }

    public void setIdChatUsuarioEventoTransporte(Integer idChatUsuarioEventoTransporte) {
        this.idChatUsuarioEventoTransporte = idChatUsuarioEventoTransporte;
    }

    public Integer getIdEventoTransporte() {
        return idEventoTransporte;
    }

    public void setIdEventoTransporte(Integer idEventoTransporte) {
        this.idEventoTransporte = idEventoTransporte;
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

    public Transporte getEventoTransporte() {
        return eventoTransporte;
    }

    public void setEventoTransporte(Transporte eventoTransporte) {
        this.eventoTransporte = eventoTransporte;
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
}
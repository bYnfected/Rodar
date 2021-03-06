package com.example.android.rodar.models;

import java.io.Serializable;
import java.util.List;

public class Carona implements Serializable {

    public Integer idEventoCarona;
    public Integer idEvento;
    public Evento Evento;
    public Integer idUsuarioMotorista;
    public Usuario usuarioMotorista;
    public String enderecoPartidaRua;
    public String enderecoPartidaComplemento;
    public String enderecoPartidaBairro;
    public Integer enderecoPartidaNumero;
    public String enderecoPartidaCEP;
    public String enderecoPartidaCidade;
    public String enderecoPartidaUF;
    public Double valorParticipacao;
    public String Mensagem;
    private Integer quantidadeVagas;
    private Integer quantidadeVagasDisponiveis;
    private Integer quantidadeVagasOcupadas;
    private List<Usuario> Passageiros;
    private AvaliacaoCarona avaliacaoCarona;
    private String dataHoraPartida;
    private String dataHoraPrevisaoChegada;

    public String getDataHoraPrevisaoChegada() {
        return dataHoraPrevisaoChegada;
    }

    public void setDataHoraPrevisaoChegada(String dataHoraPrevisaoChegada) {
        this.dataHoraPrevisaoChegada = dataHoraPrevisaoChegada;
    }

    public String getDataHoraPartida() {
        return dataHoraPartida;
    }

    public void setDataHoraPartida(String dataHoraPartida) {
        this.dataHoraPartida = dataHoraPartida;
    }

    public AvaliacaoCarona getAvaliacaoCarona() {
        return avaliacaoCarona;
    }

    public void setAvaliacaoCarona(AvaliacaoCarona avaliacaoCarona) {
        this.avaliacaoCarona = avaliacaoCarona;
    }

    public Usuario getUsuarioMotorista() {
        return usuarioMotorista;
    }

    public void setUsuarioMotorista(Usuario usuarioMotorista) {
        this.usuarioMotorista = usuarioMotorista;
    }

    public List<Usuario> getPassageiros() {
        return Passageiros;
    }

    public void setPassageiros(List<Usuario> passageiros) {
        Passageiros = passageiros;
    }

    public Integer getQuantidadeVagasDisponiveis() {
        return quantidadeVagasDisponiveis;
    }

    public void setQuantidadeVagasDisponiveis(Integer quantidadeVagasDisponiveis) {
        this.quantidadeVagasDisponiveis = quantidadeVagasDisponiveis;
    }

    public Integer getQuantidadeVagasOcupadas() {
        return quantidadeVagasOcupadas;
    }

    public void setQuantidadeVagasOcupadas(Integer quantidadeVagasOcupadas) {
        this.quantidadeVagasOcupadas = quantidadeVagasOcupadas;
    }

    public Integer getIdEventoCarona() {
        return idEventoCarona;
    }

    public void setIdEventoCarona(Integer idEventoCarona) {
        this.idEventoCarona = idEventoCarona;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Integer getIdUsuarioMotorista() {
        return idUsuarioMotorista;
    }

    public void setIdUsuarioMotorista(Integer idUsuarioMotorista) {
        this.idUsuarioMotorista = idUsuarioMotorista;
    }

    public Integer getQuantidadeVagas() {
        return quantidadeVagas;
    }

    public void setQuantidadeVagas(Integer quantidadeVagas) {
        this.quantidadeVagas = quantidadeVagas;
    }

    public String getEnderecoPartidaRua() {
        return enderecoPartidaRua;
    }

    public void setEnderecoPartidaRua(String enderecoPartidaRua) {
        this.enderecoPartidaRua = enderecoPartidaRua;
    }

    public String getEnderecoPartidaComplemento() {
        return enderecoPartidaComplemento;
    }

    public void setEnderecoPartidaComplemento(String enderecoPartidaComplemento) {
        this.enderecoPartidaComplemento = enderecoPartidaComplemento;
    }

    public String getEnderecoPartidaBairro() {
        return enderecoPartidaBairro;
    }

    public void setEnderecoPartidaBairro(String enderecoPartidaBairro) {
        this.enderecoPartidaBairro = enderecoPartidaBairro;
    }

    public Integer getEnderecoPartidaNumero() {
        return enderecoPartidaNumero;
    }

    public void setEnderecoPartidaNumero(Integer enderecoPartidaNumero) {
        this.enderecoPartidaNumero = enderecoPartidaNumero;
    }

    public String getEnderecoPartidaCEP() {
        return enderecoPartidaCEP;
    }

    public void setEnderecoPartidaCEP(String enderecoPartidaCEP) {
        this.enderecoPartidaCEP = enderecoPartidaCEP;
    }

    public String getEnderecoPartidaCidade() {
        return enderecoPartidaCidade;
    }

    public void setEnderecoPartidaCidade(String enderecoPartidaCidade) {
        this.enderecoPartidaCidade = enderecoPartidaCidade;
    }

    public String getEnderecoPartidaUF() {
        return enderecoPartidaUF;
    }

    public void setEnderecoPartidaUF(String enderecoPartidaUF) {
        this.enderecoPartidaUF = enderecoPartidaUF;
    }

    public Double getValorParticipacao() {
        return valorParticipacao;
    }

    public void setValorParticipacao(Double valorParticipacao) {
        this.valorParticipacao = valorParticipacao;
    }

    public String getMensagem() {
        return Mensagem;
    }

    public void setMensagem(String mensagem) {
        this.Mensagem = mensagem;
    }

    public com.example.android.rodar.models.Evento getEvento() {
        return Evento;
    }

    public void setEvento(com.example.android.rodar.models.Evento evento) {
        Evento = evento;
    }
}

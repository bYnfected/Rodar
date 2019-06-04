package com.example.android.rodar.models;

import java.io.Serializable;
import java.util.List;

public class Transporte implements Serializable {

    public Integer idEventoTransporte;
    public Integer idEvento;
    public Evento Evento;
    public String nomeTransporte;
    public Integer idUsuarioTransportador;
    public Usuario usuarioTransportador;
    public String enderecoPartidaRua;
    public String enderecoPartidaComplemento;
    public String enderecoPartidaBairro;
    public Integer enderecoPartidaNumero;
    public String enderecoPartidaCEP;
    public String enderecoPartidaCidade;
    public String enderecoPartidaUF;
    public Double valorParticipacao;
    public String Mensagem;
    public Integer quantidadeVagas;
    public Integer quantidadeVagasDisponiveis;
    public Integer quantidadeVagasOcupadas;
    public List<Usuario> Passageiros;
    private AvaliacaoTransporte avaliacaoTransporte;
    private String dataHoraPartida;
    private String dataHoraPrevisaoChegada;

    public String getDataHoraPartida() {
        return dataHoraPartida;
    }

    public void setDataHoraPartida(String dataHoraPartida) {
        this.dataHoraPartida = dataHoraPartida;
    }

    public String getDataHoraPrevisaoChegada() {
        return dataHoraPrevisaoChegada;
    }

    public void setDataHoraPrevisaoChegada(String dataHoraPrevisaoChegada) {
        this.dataHoraPrevisaoChegada = dataHoraPrevisaoChegada;
    }

    public AvaliacaoTransporte getAvaliacaoTransporte() {
        return avaliacaoTransporte;
    }

    public void setAvaliacaoTransporte(AvaliacaoTransporte avaliacaoTransporte) {
        this.avaliacaoTransporte = avaliacaoTransporte;
    }

    public Integer getIdEventoTransporte() {
        return idEventoTransporte;
    }

    public void setIdEventoTransporte(Integer idEventoTransporte) {
        this.idEventoTransporte = idEventoTransporte;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getNomeTransporte() {
        return nomeTransporte;
    }

    public void setNomeTransporte(String nomeTransporte) {
        this.nomeTransporte = nomeTransporte;
    }

    public Integer getIdUsuarioTransportador() {
        return idUsuarioTransportador;
    }

    public void setIdUsuarioTransportador(Integer idUsuarioTransportador) {
        this.idUsuarioTransportador = idUsuarioTransportador;
    }

    public Usuario getUsuarioTransportador() {
        return usuarioTransportador;
    }

    public void setUsuarioTransportador(Usuario usuarioTransportador) {
        this.usuarioTransportador = usuarioTransportador;
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
        Mensagem = mensagem;
    }

    public Integer getQuantidadeVagas() {
        return quantidadeVagas;
    }

    public void setQuantidadeVagas(Integer quantidadeVagas) {
        this.quantidadeVagas = quantidadeVagas;
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

    public List<Usuario> getPassageiros() {
        return Passageiros;
    }

    public void setPassageiros(List<Usuario> passageiros) {
        Passageiros = passageiros;
    }

    public com.example.android.rodar.models.Evento getEvento() {
        return Evento;
    }

    public void setEvento(com.example.android.rodar.models.Evento evento) {
        Evento = evento;
    }
}

package com.example.android.rodar.models;


public class Usuario {

    private int idUsuario;
    private String Nome;
    private String Sobrenome;
    private String RG;
    private String CPF;
    private String urlImagemSelfie;
    private String Genero;
    private String Descricao;
    private String dataNascimento;
    private String Email;
    private String numeroTelefone;
    private String Senha;
    private String facebookId;
    private String googleId;
    private String urlImagemRGFrente;
    private String urlImagemRGTras;
    private String urlImagemCPF;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }


    public String getSobrenome() {
        return Sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.Sobrenome = sobrenome;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getUrlImagemSelfie() {
        return urlImagemSelfie;
    }

    public void setUrlImagemSelfie(String urlImagemSelfie) {
        this.urlImagemSelfie = urlImagemSelfie;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNumeroTelefone() {
        return numeroTelefone;
    }

    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getUrlImagemRGFrente() {
        return urlImagemRGFrente;
    }

    public void setUrlImagemRGFrente(String urlImagemRGFrente) {
        this.urlImagemRGFrente = urlImagemRGFrente;
    }

    public String getUrlImagemRGTras() {
        return urlImagemRGTras;
    }

    public void setUrlImagemRGTras(String urlImagemRGTras) {
        this.urlImagemRGTras = urlImagemRGTras;
    }

    public String getUrlImagemCPF() {
        return urlImagemCPF;
    }

    public void setUrlImagemCPF(String urlImagemCPF) {
        this.urlImagemCPF = urlImagemCPF;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}

package com.estudando.controle.unicesumar.estudando_controle.model;

import java.time.LocalDateTime;

public class TarefaModel {

    private Long id;
    private UsuarioModel usuario;
    private String nome;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConclusao;
    private Boolean concluida;

    public TarefaModel() {
    }

    public TarefaModel(String nome, UsuarioModel usuario, Boolean concluida) {
        this.nome = nome;
        this.usuario = usuario;
        if (concluida) {
            setDataConclusao(LocalDateTime.now());
        } else {
            setDataCriacao(LocalDateTime.now());
            setDataConclusao(null);
        }
        this.concluida = concluida;
    }

    public TarefaModel(Long id, String nome, UsuarioModel usuario, Boolean concluida) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        if (concluida) {
            setDataConclusao(LocalDateTime.now());
        } else {
            setDataCriacao(LocalDateTime.now());
            setDataConclusao(null);
        }
        this.concluida = concluida;
    }

    public TarefaModel(Long id, String nome, UsuarioModel usuario, LocalDateTime dataCriacao,
            LocalDateTime dataConclusao, Boolean concluida) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
        this.concluida = concluida;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataConclusao() {
        return this.dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public Boolean isConcluida() {
        return this.concluida;
    }

    public Boolean getConcluida() {
        return this.concluida;
    }

    public void setConcluida(Boolean concluida) {
        this.concluida = concluida;
    }
}

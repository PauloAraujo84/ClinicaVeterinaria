package br.com.veterinaria.model;

public class Diagnostico {
    private Consulta consulta;
    private String descricao;

    public Diagnostico(Consulta consulta, String descricao) {
        this.consulta = consulta;
        this.descricao = descricao;
    }

    // Getters
    public Consulta getConsulta() {
        return consulta;
    }

    public String getDescricao() {
        return descricao;
    }

    // Setters
    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Diagnostico{" +
                "consulta=" + consulta.getId() + // Referência ao ID da consulta
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
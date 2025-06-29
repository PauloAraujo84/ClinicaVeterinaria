package br.com.veterinaria.model;

public class Medicamento {
    private static int nextId = 1;
    private int id;
    private String nome;
    private String descricao; // Ex: "anti-inflamatório"
    private String dosagem;   // Ex: "1 comprimido a cada 12h"

    public Medicamento(String nome, String descricao, String dosagem) {
        this.id = nextId++;
        this.nome = nome;
        this.descricao = descricao;
        this.dosagem = dosagem;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getDosagem() { return dosagem; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setDosagem(String dosagem) { this.dosagem = dosagem; }

    @Override
    public String toString() {
        return nome + " (" + dosagem + ")";
    }
}
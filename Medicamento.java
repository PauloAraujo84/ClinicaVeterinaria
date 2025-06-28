package br.com.veterinaria.model;

public class Medicamento {
    private String nome;
    private String dosagem;

    public Medicamento(String nome, String dosagem) {
        this.nome = nome;
        this.dosagem = dosagem;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getDosagem() {
        return dosagem;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                "nome='" + nome + '\'' +
                ", dosagem='" + dosagem + '\'' +
                '}';
    }
}
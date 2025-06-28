package br.com.veterinaria.model;

public class Veterinario {
    private String nome;
    private String especialidade;

    public Veterinario(String nome, String especialidade) {
        this.nome = nome;
        this.especialidade = especialidade;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return nome; // Para que o JComboBox mostre apenas o nome do veterinário
    }
}
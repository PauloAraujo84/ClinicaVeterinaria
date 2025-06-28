package br.com.veterinaria.model;

import java.util.Date;

public class Consulta {
    private int id; // Adicionado para ser gerenciado pelo DAO
    private Pet pet;
    private Cliente cliente;
    private Veterinario veterinario;
    private Date data;

    // Construtor, getters e setters
    public Consulta(Pet pet, Cliente cliente, Veterinario veterinario, Date data) {
        this.pet = pet;
        this.cliente = cliente;
        this.veterinario = veterinario;
        this.data = data;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Pet getPet() { return pet; }
    public Cliente getCliente() { return cliente; }
    public Veterinario getVeterinario() { return veterinario; }
    public Date getData() { return data; }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", pet=" + pet.getNome() +
                ", cliente=" + cliente.getNome() +
                ", veterinario=" + veterinario.getNome() +
                ", data=" + data +
                '}';
    }
}
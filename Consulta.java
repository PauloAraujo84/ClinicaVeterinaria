package br.com.veterinaria.model;

import java.util.Date; // Certifique-se de importar java.util.Date

public class Consulta {
    private static int nextId = 1; // Contador para gerar IDs únicos
    private int id; // ID único da consulta
    private Pet pet;
    private Cliente cliente;
    private Veterinario veterinario;
    private Date dataHora; // Nome da variável para a data e hora da consulta

    public Consulta(Pet pet, Cliente cliente, Veterinario veterinario, Date dataHora) {
        this.id = nextId++; // Atribui um ID único
        this.pet = pet;
        this.cliente = cliente;
        this.veterinario = veterinario;
        this.dataHora = dataHora;
    }

    // --- Getters ---

    /**
     * Retorna o ID único da consulta.
     * @return O ID da consulta.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o pet envolvido na consulta.
     * @return O objeto Pet da consulta.
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * Retorna o cliente (proprietário) envolvido na consulta.
     * @return O objeto Cliente da consulta.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Retorna o veterinário responsável pela consulta.
     * @return O objeto Veterinario da consulta.
     */
    public Veterinario getVeterinario() {
        return veterinario;
    }

    /**
     * Retorna a data e hora da consulta.
     * Este é o método que estava faltando ou com nome incorreto.
     * @return O objeto Date representando a data e hora da consulta.
     */
    public Date getData() { // <-- MÉTODO CORRIGIDO (ou altere para getDataHora() se preferir consistência)
        return dataHora;
    }

    // --- Setters (se necessário, especialmente para o ID pelo DAO) ---

    /**
     * Define o ID da consulta. Geralmente usado apenas pelo DAO para persistência.
     * @param id O novo ID para a consulta.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Define o pet envolvido na consulta.
     * @param pet O novo objeto Pet para a consulta.
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Define o cliente (proprietário) envolvido na consulta.
     * @param cliente O novo objeto Cliente para a consulta.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Define o veterinário responsável pela consulta.
     * @param veterinario O novo objeto Veterinario para a consulta.
     */
    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    /**
     * Define a data e hora da consulta.
     * @param dataHora O novo objeto Date representando a data e hora da consulta.
     */
    public void setData(Date dataHora) { // <-- SETTER CORRIGIDO (ou altere para setDataHora())
        this.dataHora = dataHora;
    }

    /**
     * Sobrescreve o método toString para uma representação legível do objeto Consulta.
     * Útil para exibir em ComboBoxes ou para depuração.
     * @return Uma String que representa a Consulta.
     */
    @Override
    public String toString() {
        return "Consulta ID: " + id + " | Pet: " + pet.getNome() + " | Vet: " + veterinario.getNome() + " | Data: " + dataHora;
    }
}
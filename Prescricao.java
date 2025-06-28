package br.com.veterinaria.model;

import java.util.List;

public class Prescricao {
    private static int nextId = 1; // Para gerar IDs únicos
    private int id;
    private Consulta consulta;
    private List<Medicamento> medicamentos;

    public Prescricao(Consulta consulta, List<Medicamento> medicamentos) {
        this.id = nextId++;
        this.consulta = consulta;
        this.medicamentos = medicamentos;
    }

    // Getters
    public int getId() {
        return id;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    // Setters (se necessário)
    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    @Override
    public String toString() {
        return "Prescrição ID " + id + " para " + consulta.getPet().getNome() + " - " + consulta.getData();
    }
}
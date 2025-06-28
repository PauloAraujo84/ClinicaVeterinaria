package br.com.veterinaria.model;

import java.util.List;

public class Prescricao {
    private Consulta consulta;
    private List<Medicamento> medicamentos;

    public Prescricao(Consulta consulta, List<Medicamento> medicamentos) {
        this.consulta = consulta;
        this.medicamentos = medicamentos;
    }

    // Getters
    public Consulta getConsulta() {
        return consulta;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    // Setters
    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Prescricao para Consulta ID: ").append(consulta.getId()).append("\n");
        sb.append("Medicamentos:\n");
        if (medicamentos != null && !medicamentos.isEmpty()) {
            for (Medicamento med : medicamentos) {
                sb.append("  - ").append(med.getNome()).append(" (").append(med.getDosagem()).append(")\n");
            }
        } else {
            sb.append("  Nenhum medicamento prescrito.\n");
        }
        return sb.toString();
    }
}
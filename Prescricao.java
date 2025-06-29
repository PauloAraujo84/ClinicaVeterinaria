package br.com.veterinaria.model;

import java.util.ArrayList;
import java.util.List;

public class Prescricao {
    private static int nextId = 1;
    private int id;
    private Consulta consulta;
    private Diagnostico diagnostico; // Associar o diagnóstico
    private List<Medicamento> medicamentos;

    public Prescricao(Consulta consulta, List<Medicamento> medicamentos) {
        this.id = nextId++;
        this.consulta = consulta;
        this.medicamentos = (medicamentos != null) ? new ArrayList<>(medicamentos) : new ArrayList<>();
        // O diagnóstico será setado após a criação no método realizarAtendimento
    }

    public int getId() { return id; }
    public Consulta getConsulta() { return consulta; }
    public Diagnostico getDiagnostico() { return diagnostico; }
    public List<Medicamento> getMedicamentos() { return medicamentos; }

    public void setId(int id) { this.id = id; }
    public void setConsulta(Consulta consulta) { this.consulta = consulta; }
    public void setDiagnostico(Diagnostico diagnostico) { this.diagnostico = diagnostico; }
    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = (medicamentos != null) ? new ArrayList<>(medicamentos) : new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Prescrição [ID=").append(id)
                .append(", Consulta ID=").append(consulta.getId());
        if (diagnostico != null) {
            sb.append(", Diagnóstico=").append(diagnostico.getDescricao());
        }
        sb.append(", Medicamentos: ");
        if (medicamentos.isEmpty()) {
            sb.append("Nenhum");
        } else {
            for (Medicamento med : medicamentos) {
                sb.append(med.getNome()).append(" (").append(med.getDosagem()).append("); ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
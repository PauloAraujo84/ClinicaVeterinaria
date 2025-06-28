package br.com.veterinaria.facade;

import br.com.veterinaria.adapter.DataAdapter;
import br.com.veterinaria.dao.ConsultaDAO;
import br.com.veterinaria.dao.InMemoryConsultaDAO;
import br.com.veterinaria.model.*;
import java.util.Date;
import java.util.List;

public class ClinicaFacade {
    // A fachada conhece e utiliza o DAO para acessar os dados
    private final ConsultaDAO consultaDAO;

    public ClinicaFacade() {
        // Instancia a implementação do DAO. Poderia ser injetado.
        this.consultaDAO = new InMemoryConsultaDAO();
    }

    // Método que simplifica o processo de agendar uma consulta
    public void agendarConsulta(Pet pet, Cliente cliente, Veterinario veterinario, Date data) {
        // Cria o objeto de Consulta
        Consulta novaConsulta = new Consulta(pet, cliente, veterinario, data);

        // Salva a consulta usando o DAO, que encapsula a lógica de persistência
        consultaDAO.salvar(novaConsulta);

        // Utiliza o Adapter para formatar a data para exibição
        String dataFormatada = DataAdapter.formatarData(data);
        System.out.println("\n--- Agendamento Realizado ---");
        System.out.println("Consulta agendada para o pet " + pet.getNome() + " com o veterinário " + veterinario.getNome());
        System.out.println("Data e Hora: " + dataFormatada);
    }

    // Método que simplifica a realização de uma consulta e gera diagnóstico/prescrição
    public void realizarAtendimento(Consulta consulta, String diagnosticoDescricao, List<Medicamento> medicamentos) {
        System.out.println("\n--- Atendimento Realizado ---");
        System.out.println("Realizando consulta para " + consulta.getPet().getNome() + "...");

        // Lógica de negócio da consulta...
        // No caso real, a consulta teria um status 'em andamento' e 'concluída'

        // Criação de Diagnóstico e Prescrição
        Diagnostico diagnostico = new Diagnostico(consulta, diagnosticoDescricao);
        Prescricao prescricao = new Prescricao(consulta, medicamentos);

        System.out.println("\n--- Resumo do Atendimento ---");
        System.out.println("Diagnóstico: " + diagnostico.getDescricao());
        System.out.println("Prescrição: ");
        for (Medicamento m : prescricao.getMedicamentos()) {
            System.out.println("  - " + m.getNome() + " (" + m.getDosagem() + ")");
        }

        // Aqui você poderia usar DAOs para salvar o diagnóstico e a prescrição
        // diagnosticoDAO.salvar(diagnostico);
        // prescricaoDAO.salvar(prescricao);
    }

    // Métodos utilitários que a fachada pode expor
    public List<Consulta> buscarTodasAsConsultas() {
        return consultaDAO.buscarTodos();
    }
}
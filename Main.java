package br.com.veterinaria;

import br.com.veterinaria.facade.ClinicaFacade;
import br.com.veterinaria.model.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClinicaFacade clinica = new ClinicaFacade();

        Pet pet = new Pet("Rex", "Cachorro", "Labrador", 3);
        Cliente cliente = new Cliente("João", "Rua A, 123", "1234-5678");
        Veterinario veterinario = new Veterinario("Dra. Ana", "Clínica Geral");
        Date dataAtual = new Date();

        clinica.agendarConsulta(pet, cliente, veterinario, dataAtual);

        Consulta consultaAgendada = clinica.buscarTodasAsConsultas().get(0);

        String diagnosticoDescricao = "Gastroenterite leve";
        List<Medicamento> medicamentos = new ArrayList<>();
        medicamentos.add(new Medicamento("Remédio A", "10mg"));
        medicamentos.add(new Medicamento("Remédio B", "5ml"));

        clinica.realizarAtendimento(consultaAgendada, diagnosticoDescricao, medicamentos);

        System.out.println("\nProcesso finalizado.");
    }
}
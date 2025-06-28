package br.com.veterinaria.facade;

import br.com.veterinaria.dao.InMemoryClienteDAO;
import br.com.veterinaria.dao.InMemoryConsultaDAO;
import br.com.veterinaria.dao.InMemoryPetDAO;
import br.com.veterinaria.dao.InMemoryVeterinarioDAO;
import br.com.veterinaria.model.Cliente;
import br.com.veterinaria.model.Consulta;
import br.com.veterinaria.model.Diagnostico;
import br.com.veterinaria.model.Medicamento;
import br.com.veterinaria.model.Pet;
import br.com.veterinaria.model.Prescricao;
import br.com.veterinaria.model.Veterinario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A ClinicaFacade atua como uma interface simplificada para o subsistema da clínica veterinária.
 * Ela centraliza as operações de negócio, escondendo a complexidade da interação direta
 * com os objetos DAO (Data Access Object) e modelos.
 */
public class ClinicaFacade {
    // Instâncias dos DAOs (Data Access Objects) em memória.
    // Em uma aplicação real, estes seriam DAOs que interagem com um banco de dados.
    private InMemoryPetDAO petDAO;
    private InMemoryClienteDAO clienteDAO;
    private InMemoryVeterinarioDAO veterinarioDAO;
    private InMemoryConsultaDAO consultaDAO;

    /**
     * Construtor da ClinicaFacade.
     * Inicializa os DAOs e, opcionalmente, popula o sistema com alguns dados de exemplo
     * para facilitar os testes da interface gráfica.
     */
    public ClinicaFacade() {
        this.petDAO = new InMemoryPetDAO();
        this.clienteDAO = new InMemoryClienteDAO();
        this.veterinarioDAO = new InMemoryVeterinarioDAO();
        this.consultaDAO = new InMemoryConsultaDAO();

        // --- Dados Iniciais de Exemplo (Úteis para testar a GUI rapidamente) ---
        // Você pode remover este bloco se quiser que o sistema inicie completamente vazio.
        // Se remover, lembre-se de cadastrar clientes, pets e veterinários pela GUI primeiro.

        System.out.println("--- Iniciando ClinicaFacade: Carregando dados de exemplo ---");

        // Clientes de exemplo (proprietários)
        Cliente cliente1 = new Cliente("João Silva", "11987654321", "joao.silva@email.com");
        Cliente cliente2 = new Cliente("Maria Souza", "11991234567", "maria.souza@email.com");
        clienteDAO.adicionar(cliente1);
        clienteDAO.adicionar(cliente2);
        System.out.println("   Clientes de exemplo adicionados.");

        // Veterinários de exemplo
        Veterinario vet1 = new Veterinario("Dr. Carlos Almeida", "CRM-SP 12345");
        Veterinario vet2 = new Veterinario("Dra. Ana Paula Costa", "CRM-SP 67890");
        veterinarioDAO.adicionar(vet1);
        veterinarioDAO.adicionar(vet2);
        System.out.println("   Veterinários de exemplo adicionados.");

        // Pets de exemplo (associados aos clientes criados acima)
        Pet pet1 = new Pet("Fred", "Cachorro", "Poodle", 5, cliente1);
        Pet pet2 = new Pet("Miau", "Gato", "Siamês", 2, cliente2);
        Pet pet3 = new Pet("Bob", "Cachorro", "Labrador", 8, cliente1);
        petDAO.adicionar(pet1);
        petDAO.adicionar(pet2);
        petDAO.adicionar(pet3);
        System.out.println("   Pets de exemplo adicionados.");

        // Consulta de exemplo (para testes iniciais)
        agendarConsulta(pet1, cliente1, vet1, new Date());
        System.out.println("   Consulta de exemplo agendada.");

        System.out.println("--- Dados de exemplo carregados com sucesso! ---");
    }

    // --- Métodos para Gerenciar Pets ---

    /**
     * Cadastra um novo pet no sistema.
     * @param pet O objeto Pet a ser cadastrado.
     */
    public void cadastrarPet(Pet pet) {
        petDAO.adicionar(pet);
        System.out.println("[Facade] Pet '" + pet.getNome() + "' cadastrado.");
    }

    /**
     * Retorna uma lista de todos os pets cadastrados.
     * @return Uma lista de objetos Pet.
     */
    public List<Pet> getPetsCadastrados() {
        return petDAO.listarTodos();
    }

    /**
     * Remove um pet do sistema.
     * @param pet O objeto Pet a ser removido.
     */
    public void removerPet(Pet pet) {
        petDAO.remover(pet);
        System.out.println("[Facade] Pet '" + pet.getNome() + "' removido.");
    }

    // --- Métodos para Gerenciar Clientes (Proprietários) ---

    /**
     * Cadastra um novo cliente (proprietário) no sistema.
     * @param cliente O objeto Cliente a ser cadastrado.
     */
    public void cadastrarCliente(Cliente cliente) {
        clienteDAO.adicionar(cliente);
        System.out.println("[Facade] Cliente '" + cliente.getNome() + "' cadastrado.");
    }

    /**
     * Retorna uma lista de todos os clientes (proprietários) cadastrados.
     * @return Uma lista de objetos Cliente.
     */
    public List<Cliente> getClientesCadastrados() {
        return clienteDAO.listarTodos();
    }

    /**
     * Remove um cliente (proprietário) do sistema.
     * @param cliente O objeto Cliente a ser removido.
     */
    public void removerCliente(Cliente cliente) {
        clienteDAO.remover(cliente);
        System.out.println("[Facade] Cliente '" + cliente.getNome() + "' removido.");
    }

    // --- Métodos para Gerenciar Veterinários ---
    // ESTES SÃO OS MÉTODOS QUE DEVEM ESTAR PRESENTES UMA ÚNICA VEZ

    /**
     * Cadastra um novo veterinário no sistema.
     * @param veterinario O objeto Veterinario a ser cadastrado.
     */
    public void cadastrarVeterinario(Veterinario veterinario) {
        veterinarioDAO.adicionar(veterinario);
        System.out.println("[Facade] Veterinário '" + veterinario.getNome() + "' cadastrado.");
    }

    /**
     * Retorna uma lista de todos os veterinários cadastrados.
     * @return Uma lista de objetos Veterinario.
     */
    public List<Veterinario> getVeterinariosCadastrados() {
        return veterinarioDAO.listarTodos();
    }

    /**
     * Remove um veterinário do sistema.
     * @param veterinario O objeto Veterinario a ser removido.
     */
    public void removerVeterinario(Veterinario veterinario) {
        veterinarioDAO.remover(veterinario);
        System.out.println("[Facade] Veterinário '" + veterinario.getNome() + "' removido.");
    }

    // --- Métodos para Gerenciar Consultas ---

    /**
     * Agenda uma nova consulta no sistema.
     * @param pet O pet da consulta.
     * @param cliente O cliente (proprietário) do pet.
     * @param veterinario O veterinário responsável pela consulta.
     * @param data A data e hora da consulta.
     */
    public void agendarConsulta(Pet pet, Cliente cliente, Veterinario veterinario, Date data) {
        Consulta novaConsulta = new Consulta(pet, cliente, veterinario, data);
        consultaDAO.adicionar(novaConsulta);
        System.out.println("[Facade] Consulta agendada para '" + pet.getNome() + "' em " + data);
    }

    /**
     * Busca e retorna uma lista de todas as consultas agendadas.
     * @return Uma lista de objetos Consulta.
     */
    public List<Consulta> buscarTodasAsConsultas() {
        return consultaDAO.listarTodos();
    }

    // --- Métodos para Realizar Atendimento e Gerar Prescrições ---

    /**
     * Realiza um atendimento para uma consulta, registrando o diagnóstico e gerando uma prescrição.
     * @param consulta A consulta que está sendo atendida.
     * @param diagnosticoDescricao A descrição do diagnóstico.
     * @param medicamentos Uma lista de objetos Medicamento a serem incluídos na prescrição.
     * @return O objeto Prescricao gerado para este atendimento.
     */
    public Prescricao realizarAtendimento(Consulta consulta, String diagnosticoDescricao, List<Medicamento> medicamentos) {
        System.out.println("\n[Facade] --- Atendimento Iniciado ---");
        System.out.println("[Facade] Atendendo consulta para Pet: " + consulta.getPet().getNome());

        // Cria o diagnóstico e a prescrição
        Diagnostico diagnostico = new Diagnostico(consulta, diagnosticoDescricao);
        Prescricao prescricao = new Prescricao(consulta, medicamentos);


        System.out.println("[Facade] Diagnóstico: " + diagnostico.getDescricao());
        System.out.println("[Facade] Prescrição gerada com " + medicamentos.size() + " medicamentos.");
        System.out.println("[Facade] --- Atendimento Finalizado ---");

        return prescricao; // Retorna a prescrição criada
    }
}
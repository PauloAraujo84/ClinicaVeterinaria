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
    private InMemoryPetDAO petDAO;
    private InMemoryClienteDAO clienteDAO;
    private InMemoryVeterinarioDAO veterinarioDAO;
    private InMemoryConsultaDAO consultaDAO;

    // NOVO: Adicionar um DAO para Diagnosticos e Prescricoes se quiser persistir
    // Por enquanto, vamos apenas criar os objetos
    // private InMemoryDiagnosticoDAO diagnosticoDAO;
    // private InMemoryPrescricaoDAO prescricaoDAO;


    public ClinicaFacade() {
        this.petDAO = new InMemoryPetDAO();
        this.clienteDAO = new InMemoryClienteDAO();
        this.veterinarioDAO = new InMemoryVeterinarioDAO();
        this.consultaDAO = new InMemoryConsultaDAO();

        System.out.println("--- Iniciando ClinicaFacade: Carregando dados de exemplo ---");

        Cliente cliente1 = new Cliente("João Silva", "11987654321", "joao.silva@email.com");
        Cliente cliente2 = new Cliente("Maria Souza", "11991234567", "maria.souza@email.com");
        clienteDAO.adicionar(cliente1);
        clienteDAO.adicionar(cliente2);
        System.out.println("   Clientes de exemplo adicionados.");

        Veterinario vet1 = new Veterinario("Dr. Carlos Almeida", "CRM-SP 12345");
        Veterinario vet2 = new Veterinario("Dra. Ana Paula Costa", "CRM-SP 67890");
        veterinarioDAO.adicionar(vet1);
        veterinarioDAO.adicionar(vet2);
        System.out.println("   Veterinários de exemplo adicionados.");

        Pet pet1 = new Pet("Fred", "Cachorro", "Poodle", 5, cliente1);
        Pet pet2 = new Pet("Miau", "Gato", "Siamês", 2, cliente2);
        Pet pet3 = new Pet("Bob", "Cachorro", "Labrador", 8, cliente1);
        petDAO.adicionar(pet1);
        petDAO.adicionar(pet2);
        petDAO.adicionar(pet3);
        System.out.println("   Pets de exemplo adicionados.");

        // Consulta de exemplo (para testes iniciais)
        agendarConsulta(pet1, cliente1, vet1, new Date());
        // Agendar uma segunda consulta para testar a remoção
        agendarConsulta(pet2, cliente2, vet2, new Date(System.currentTimeMillis() + 3600 * 1000 * 24)); // Amanhã
        System.out.println("   Consultas de exemplo agendadas.");

        System.out.println("--- Dados de exemplo carregados com sucesso! ---");
    }

    // --- Métodos para Gerenciar Pets ---
    public void cadastrarPet(Pet pet) {
        petDAO.adicionar(pet);
        System.out.println("[Facade] Pet '" + pet.getNome() + "' cadastrado.");
    }

    public List<Pet> getPetsCadastrados() {
        return petDAO.listarTodos();
    }

    public void removerPet(Pet pet) {
        petDAO.remover(pet);
        System.out.println("[Facade] Pet '" + pet.getNome() + "' removido.");
    }

    // --- Métodos para Gerenciar Clientes (Proprietários) ---
    public void cadastrarCliente(Cliente cliente) {
        clienteDAO.adicionar(cliente);
        System.out.println("[Facade] Cliente '" + cliente.getNome() + "' cadastrado.");
    }

    public List<Cliente> getClientesCadastrados() {
        return clienteDAO.listarTodos();
    }

    public void removerCliente(Cliente cliente) {
        for (Pet pet : petDAO.listarTodos()) {
            if (pet.getProprietario() != null && pet.getProprietario().getId() == cliente.getId()) {
                System.out.println("[Facade] Não foi possível remover o cliente '" + cliente.getNome() + "': possui pets cadastrados.");
                return;
            }
        }
        clienteDAO.remover(cliente);
        System.out.println("[Facade] Cliente '" + cliente.getNome() + "' removido.");
    }

    // --- Métodos para Gerenciar Veterinários ---
    public void cadastrarVeterinario(Veterinario veterinario) {
        veterinarioDAO.adicionar(veterinario);
        System.out.println("[Facade] Veterinário '" + veterinario.getNome() + "' cadastrado.");
    }

    public List<Veterinario> getVeterinariosCadastrados() {
        return veterinarioDAO.listarTodos();
    }

    public void removerVeterinario(Veterinario veterinario) {
        veterinarioDAO.remover(veterinario);
        System.out.println("[Facade] Veterinário '" + veterinario.getNome() + "' removido.");
    }

    // --- Métodos para Gerenciar Consultas ---
    public void agendarConsulta(Pet pet, Cliente cliente, Veterinario veterinario, Date data) {
        Consulta novaConsulta = new Consulta(pet, cliente, veterinario, data);
        consultaDAO.adicionar(novaConsulta);
        System.out.println("[Facade] Consulta agendada para '" + pet.getNome() + "' em " + data);
    }

    public List<Consulta> buscarTodasAsConsultas() {
        return consultaDAO.listarTodos();
    }

    public void removerConsulta(Consulta consulta) {
        consultaDAO.remover(consulta);
        System.out.println("[Facade] Consulta para Pet '" + consulta.getPet().getNome() + "' em " + consulta.getData() + " removida.");
    }

    // --- Métodos para Realizar Atendimento e Gerar Prescrições ---

    /**
     * Realiza um atendimento para uma consulta, registrando o diagnóstico e gerando uma prescrição.
     * Após a realização, a consulta é removida da lista de agendadas.
     * @param consulta A consulta que está sendo atendida.
     * @param diagnosticoDescricao A descrição do diagnóstico.
     * @param medicamentos Uma lista de objetos Medicamento a serem incluídos na prescrição.
     * @return O objeto Prescricao gerado para este atendimento.
     */
    public Prescricao realizarAtendimento(Consulta consulta, String diagnosticoDescricao, List<Medicamento> medicamentos) {
        System.out.println("\n[Facade] --- Atendimento Iniciado ---");
        System.out.println("[Facade] Atendendo consulta para Pet: " + consulta.getPet().getNome());

        Diagnostico diagnostico = new Diagnostico(consulta, diagnosticoDescricao);
        Prescricao prescricao = new Prescricao(consulta, medicamentos);
        prescricao.setDiagnostico(diagnostico); // Garantir que o diagnóstico esteja associado à prescrição

        // --- AÇÃO PRINCIPAL: REMOVER A CONSULTA AGENDADA ---
        removerConsulta(consulta); // Chama o método de remoção de consulta

        // Em um sistema real, você salvaria Diagnostico e Prescricao em seus DAOs
        // diagnosticoDAO.adicionar(diagnostico);
        // prescricaoDAO.adicionar(prescricao);

        System.out.println("[Facade] Diagnóstico: " + diagnostico.getDescricao());
        System.out.println("[Facade] Prescrição gerada com " + medicamentos.size() + " medicamentos.");
        System.out.println("[Facade] --- Atendimento Finalizado ---");

        return prescricao;
    }
}
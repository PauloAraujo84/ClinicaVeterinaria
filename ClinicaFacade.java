package br.com.veterinaria.facade;

import br.com.veterinaria.adapter.DataAdapter;
import br.com.veterinaria.dao.ConsultaDAO;
import br.com.veterinaria.dao.InMemoryConsultaDAO;
import br.com.veterinaria.model.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClinicaFacade {
    private final ConsultaDAO consultaDAO;

    // --- Listas de exemplo para Pets, Clientes e Veterinários (para a GUI) ---
    private final List<Pet> petsCadastrados;
    private final List<Cliente> clientesCadastrados;
    private final List<Veterinario> veterinariosCadastrados;
    // --- Fim das listas de exemplo ---

    public ClinicaFacade() {
        this.consultaDAO = new InMemoryConsultaDAO();

        // --- Inicializa as listas de exemplo com alguns dados ---
        petsCadastrados = new ArrayList<>();
        petsCadastrados.add(new Pet("Rex", "Cachorro", "Labrador", 3));
        petsCadastrados.add(new Pet("Miau", "Gato", "Siamês", 2));
        petsCadastrados.add(new Pet("Thor", "Cachorro", "Golden Retriever", 5));

        clientesCadastrados = new ArrayList<>();
        clientesCadastrados.add(new Cliente("João Silva", "Rua A, 123", "11987654321"));
        clientesCadastrados.add(new Cliente("Maria Souza", "Av. B, 456", "11998765432"));
        clientesCadastrados.add(new Cliente("Carlos Pereira", "Rua C, 789", "11976543210"));

        veterinariosCadastrados = new ArrayList<>();
        veterinariosCadastrados.add(new Veterinario("Dra. Ana Costa", "Clínica Geral"));
        veterinariosCadastrados.add(new Veterinario("Dr. Pedro Almeida", "Cardiologia"));
        // --- Fim da inicialização ---
    }

    // Método que simplifica o processo de agendar uma consulta
    public void agendarConsulta(Pet pet, Cliente cliente, Veterinario veterinario, Date data) {
        Consulta novaConsulta = new Consulta(pet, cliente, veterinario, data);
        consultaDAO.salvar(novaConsulta);
        String dataFormatada = DataAdapter.formatarData(data);
        System.out.println("\n--- Agendamento Realizado ---");
        System.out.println("Consulta agendada para o pet " + pet.getNome() + " com o veterinário " + veterinario.getNome());
        System.out.println("Data e Hora: " + dataFormatada);
    }

    // Método que simplifica a realização de uma consulta e gera diagnóstico/prescrição
    public void realizarAtendimento(Consulta consulta, String diagnosticoDescricao, List<Medicamento> medicamentos) {
        System.out.println("\n--- Atendimento Realizado ---");
        System.out.println("Realizando consulta para " + consulta.getPet().getNome() + "...");

        Diagnostico diagnostico = new Diagnostico(consulta, diagnosticoDescricao);
        Prescricao prescricao = new Prescricao(consulta, medicamentos);

        System.out.println("\n--- Resumo do Atendimento ---");
        System.out.println("Diagnóstico: " + diagnostico.getDescricao());
        System.out.println("Prescrição: ");
        if (prescricao.getMedicamentos() != null) {
            for (Medicamento m : prescricao.getMedicamentos()) {
                System.out.println("  - " + m.getNome() + " (" + m.getDosagem() + ")");
            }
        }
    }

    // Métodos utilitários que a fachada pode expor
    public List<Consulta> buscarTodasAsConsultas() {
        return consultaDAO.buscarTodos();
    }

    // --- Novos métodos para a GUI de Agendamento ---
    public List<Pet> getPetsCadastrados() {
        return new ArrayList<>(petsCadastrados); // Retorna uma cópia
    }

    public List<Cliente> getClientesCadastrados() {
        return new ArrayList<>(clientesCadastrados); // Retorna uma cópia
    }

    public List<Veterinario> getVeterinariosCadastrados() {
        return new ArrayList<>(veterinariosCadastrados); // Retorna uma cópia
    }

    // Método para adicionar um pet cadastrado (chamado da CadastroPetGUI)
    public void cadastrarPet(Pet pet) {
        petsCadastrados.add(pet);
        System.out.println("LOG: Pet " + pet.getNome() + " cadastrado via fachada.");
        // Em um sistema real, aqui você chamaria petDAO.salvar(pet);
    }
    // --- Fim dos novos métodos ---
}
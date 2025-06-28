package br.com.veterinaria.dao;

import br.com.veterinaria.model.Veterinario;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator; // Importa Iterator para remoção segura

/**
 * Implementação do Data Access Object (DAO) para a entidade Veterinario.
 * Gerencia o armazenamento e a recuperação de objetos Veterinario em memória.
 * Esta é uma versão simplificada para demonstração; em um sistema real,
 * ela interagira com um banco de dados.
 */
public class InMemoryVeterinarioDAO {
    // Lista para armazenar os objetos Veterinario em memória
    private List<Veterinario> veterinarios;
    // Contador para gerar IDs únicos para novos veterinários
    private static int nextId = 1;

    /**
     * Construtor da InMemoryVeterinarioDAO.
     * Inicializa a lista de veterinários.
     */
    public InMemoryVeterinarioDAO() {
        this.veterinarios = new ArrayList<>();
    }

    /**
     * Adiciona um novo veterinário à lista.
     * Atribui um ID único ao veterinário antes de adicioná-lo.
     * @param veterinario O objeto Veterinario a ser adicionado.
     */
    public void adicionar(Veterinario veterinario) {
        if (veterinario == null) {
            System.err.println("Erro: Não é possível adicionar um veterinário nulo.");
            return;
        }
        // Atribui um ID se o veterinário ainda não tiver um (assumindo 0 ou valor padrão para "novo")
        // Ou você pode garantir que o ID é atribuído apenas uma vez no construtor do Veterinario
        if (veterinario.getId() == 0) { // Supondo que 0 é o ID padrão para um objeto novo
            veterinario.setId(nextId++);
        }
        this.veterinarios.add(veterinario);
        System.out.println("[DAO] Veterinário '" + veterinario.getNome() + "' adicionado. ID: " + veterinario.getId());
    }

    /**
     * Lista todos os veterinários atualmente armazenados.
     * @return Uma lista contendo todos os objetos Veterinario.
     */
    public List<Veterinario> listarTodos() {
        // Retorna uma nova ArrayList para evitar modificações externas diretas na lista interna
        return new ArrayList<>(this.veterinarios);
    }

    /**
     * Busca um veterinário pelo seu ID.
     * @param id O ID do veterinário a ser buscado.
     * @return O objeto Veterinario se encontrado, ou null caso contrário.
     */
    public Veterinario buscarPorId(int id) {
        for (Veterinario vet : this.veterinarios) {
            if (vet.getId() == id) {
                return vet;
            }
        }
        System.out.println("[DAO] Veterinário com ID " + id + " não encontrado.");
        return null;
    }

    /**
     * Remove um veterinário da lista.
     * @param veterinario O objeto Veterinario a ser removido.
     */
    public void remover(Veterinario veterinario) {
        if (veterinario == null) {
            System.err.println("Erro: Não é possível remover um veterinário nulo.");
            return;
        }
        // Usa Iterator para remoção segura durante a iteração
        Iterator<Veterinario> iterator = this.veterinarios.iterator();
        while (iterator.hasNext()) {
            Veterinario v = iterator.next();
            if (v.getId() == veterinario.getId()) { // Compara pelo ID para garantir que é o mesmo objeto
                iterator.remove();
                System.out.println("[DAO] Veterinário '" + veterinario.getNome() + "' removido.");
                return;
            }
        }
        System.out.println("[DAO] Veterinário '" + veterinario.getNome() + "' não encontrado para remoção.");
    }

    /**
     * Atualiza as informações de um veterinário existente.
     * @param veterinario O objeto Veterinario com as informações atualizadas.
     */
    public void atualizar(Veterinario veterinario) {
        if (veterinario == null) {
            System.err.println("Erro: Não é possível atualizar um veterinário nulo.");
            return;
        }
        for (int i = 0; i < this.veterinarios.size(); i++) {
            if (this.veterinarios.get(i).getId() == veterinario.getId()) {
                this.veterinarios.set(i, veterinario); // Substitui o objeto existente pelo atualizado
                System.out.println("[DAO] Veterinário '" + veterinario.getNome() + "' atualizado.");
                return;
            }
        }
        System.out.println("[DAO] Veterinário '" + veterinario.getNome() + "' não encontrado para atualização.");
    }
}
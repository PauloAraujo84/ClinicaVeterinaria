package br.com.veterinaria.dao;

import br.com.veterinaria.model.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator; // Importa Iterator para remoção segura

/**
 * Implementação do Data Access Object (DAO) para a entidade Cliente.
 * Gerencia o armazenamento e a recuperação de objetos Cliente em memória.
 * Esta é uma versão simplificada para demonstração; em um sistema real,
 * ela interagira com um banco de dados persistente.
 */
public class InMemoryClienteDAO {
    // Lista para armazenar os objetos Cliente em memória
    private List<Cliente> clientes;
    // Contador para gerar IDs únicos para novos clientes
    private static int nextId = 1;

    /**
     * Construtor da InMemoryClienteDAO.
     * Inicializa a lista de clientes.
     */
    public InMemoryClienteDAO() {
        this.clientes = new ArrayList<>();
    }

    /**
     * Adiciona um novo cliente à lista.
     * Atribui um ID único ao cliente antes de adicioná-lo, se ele ainda não tiver um.
     * @param cliente O objeto Cliente a ser adicionado.
     */
    public void adicionar(Cliente cliente) {
        if (cliente == null) {
            System.err.println("Erro: Não é possível adicionar um cliente nulo.");
            return;
        }
        // Atribui um ID se o cliente ainda não tiver um (assumindo que 0 é o ID padrão para um novo objeto)
        if (cliente.getId() == 0) {
            cliente.setId(nextId++);
        }
        this.clientes.add(cliente);
        System.out.println("[DAO] Cliente '" + cliente.getNome() + "' adicionado. ID: " + cliente.getId());
    }

    /**
     * Lista todos os clientes atualmente armazenados.
     * @return Uma lista contendo todos os objetos Cliente.
     */
    public List<Cliente> listarTodos() {
        // Retorna uma nova ArrayList para evitar modificações externas diretas na lista interna
        return new ArrayList<>(this.clientes);
    }

    /**
     * Busca um cliente pelo seu ID.
     * @param id O ID do cliente a ser buscado.
     * @return O objeto Cliente se encontrado, ou null caso contrário.
     */
    public Cliente buscarPorId(int id) {
        for (Cliente cliente : this.clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        System.out.println("[DAO] Cliente com ID " + id + " não encontrado.");
        return null;
    }

    /**
     * Remove um cliente da lista.
     * @param cliente O objeto Cliente a ser removido.
     */
    public void remover(Cliente cliente) {
        if (cliente == null) {
            System.err.println("Erro: Não é possível remover um cliente nulo.");
            return;
        }
        // Usa Iterator para remoção segura durante a iteração
        Iterator<Cliente> iterator = this.clientes.iterator();
        while (iterator.hasNext()) {
            Cliente c = iterator.next();
            if (c.getId() == cliente.getId()) { // Compara pelo ID para garantir que é o mesmo objeto
                iterator.remove();
                System.out.println("[DAO] Cliente '" + cliente.getNome() + "' removido.");
                return;
            }
        }
        System.out.println("[DAO] Cliente '" + cliente.getNome() + "' não encontrado para remoção.");
    }

    /**
     * Atualiza as informações de um cliente existente.
     * @param cliente O objeto Cliente com as informações atualizadas.
     */
    public void atualizar(Cliente cliente) {
        if (cliente == null) {
            System.err.println("Erro: Não é possível atualizar um cliente nulo.");
            return;
        }
        for (int i = 0; i < this.clientes.size(); i++) {
            if (this.clientes.get(i).getId() == cliente.getId()) {
                this.clientes.set(i, cliente); // Substitui o objeto existente pelo atualizado
                System.out.println("[DAO] Cliente '" + cliente.getNome() + "' atualizado.");
                return;
            }
        }
        System.out.println("[DAO] Cliente '" + cliente.getNome() + "' não encontrado para atualização.");
    }
}
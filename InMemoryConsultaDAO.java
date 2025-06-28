package br.com.veterinaria.dao;

import br.com.veterinaria.model.Consulta;

import java.util.ArrayList;
import java.util.Iterator; // Importa Iterator para remoção segura
import java.util.List;

/**
 * Implementação do Data Access Object (DAO) para a entidade Consulta.
 * Gerencia o armazenamento e a recuperação de objetos Consulta em memória.
 * Esta é uma versão simplificada para demonstração; em um sistema real,
 * ela interagira com um banco de dados persistente.
 */
public class InMemoryConsultaDAO {
    // Lista para armazenar os objetos Consulta em memória
    private List<Consulta> consultas;
    // Contador para gerar IDs únicos para novas consultas
    private static int nextId = 1;

    /**
     * Construtor da InMemoryConsultaDAO.
     * Inicializa a lista de consultas.
     */
    public InMemoryConsultaDAO() {
        this.consultas = new ArrayList<>();
    }

    /**
     * Adiciona uma nova consulta à lista.
     * Atribui um ID único à consulta antes de adicioná-la, se ela ainda não tiver um.
     * @param consulta O objeto Consulta a ser adicionado.
     */
    public void adicionar(Consulta consulta) {
        if (consulta == null) {
            System.err.println("Erro: Não é possível adicionar uma consulta nula.");
            return;
        }
        // Atribui um ID se a consulta ainda não tiver um (assumindo que 0 é o ID padrão para um novo objeto)
        if (consulta.getId() == 0) { // Você precisará ter um getId() e setId() na sua classe Consulta
            consulta.setId(nextId++);
        }
        this.consultas.add(consulta);
        System.out.println("[DAO] Consulta para Pet '" + consulta.getPet().getNome() + "' adicionada. ID: " + consulta.getId());
    }

    /**
     * Lista todas as consultas atualmente armazenadas.
     * @return Uma lista contendo todos os objetos Consulta.
     */
    public List<Consulta> listarTodos() {
        // Retorna uma nova ArrayList para evitar modificações externas diretas na lista interna
        return new ArrayList<>(this.consultas);
    }

    /**
     * Busca uma consulta pelo seu ID.
     * @param id O ID da consulta a ser buscada.
     * @return O objeto Consulta se encontrado, ou null caso contrário.
     */
    public Consulta buscarPorId(int id) {
        for (Consulta consulta : this.consultas) {
            if (consulta.getId() == id) {
                return consulta;
            }
        }
        System.out.println("[DAO] Consulta com ID " + id + " não encontrada.");
        return null;
    }

    /**
     * Remove uma consulta da lista.
     * @param consulta O objeto Consulta a ser removido.
     */
    public void remover(Consulta consulta) {
        if (consulta == null) {
            System.err.println("Erro: Não é possível remover uma consulta nula.");
            return;
        }
        // Usa Iterator para remoção segura durante a iteração
        Iterator<Consulta> iterator = this.consultas.iterator();
        while (iterator.hasNext()) {
            Consulta c = iterator.next();
            if (c.getId() == consulta.getId()) { // Compara pelo ID para garantir que é o mesmo objeto
                iterator.remove();
                System.out.println("[DAO] Consulta para Pet '" + consulta.getPet().getNome() + "' removida.");
                return;
            }
        }
        System.out.println("[DAO] Consulta para Pet '" + consulta.getPet().getNome() + "' não encontrada para remoção.");
    }

    /**
     * Atualiza as informações de uma consulta existente.
     * @param consulta O objeto Consulta com as informações atualizadas.
     */
    public void atualizar(Consulta consulta) {
        if (consulta == null) {
            System.err.println("Erro: Não é possível atualizar uma consulta nula.");
            return;
        }
        for (int i = 0; i < this.consultas.size(); i++) {
            if (this.consultas.get(i).getId() == consulta.getId()) {
                this.consultas.set(i, consulta); // Substitui o objeto existente pelo atualizado
                System.out.println("[DAO] Consulta para Pet '" + consulta.getPet().getNome() + "' atualizada.");
                return;
            }
        }
        System.out.println("[DAO] Consulta para Pet '" + consulta.getPet().getNome() + "' não encontrada para atualização.");
    }
}
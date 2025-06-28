package br.com.veterinaria.dao;

import br.com.veterinaria.model.Pet;

import java.util.ArrayList;
import java.util.Iterator; // Importa Iterator para remoção segura
import java.util.List;

/**
 * Implementação do Data Access Object (DAO) para a entidade Pet.
 * Gerencia o armazenamento e a recuperação de objetos Pet em memória.
 * Esta é uma versão simplificada para demonstração; em um sistema real,
 * ela interagira com um banco de dados persistente.
 */
public class InMemoryPetDAO {
    // Lista para armazenar os objetos Pet em memória
    private List<Pet> pets;
    // Contador para gerar IDs únicos para novos pets
    private static int nextId = 1;

    /**
     * Construtor da InMemoryPetDAO.
     * Inicializa a lista de pets.
     */
    public InMemoryPetDAO() {
        this.pets = new ArrayList<>();
    }

    /**
     * Adiciona um novo pet à lista.
     * Atribui um ID único ao pet antes de adicioná-lo, se ele ainda não tiver um.
     * @param pet O objeto Pet a ser adicionado.
     */
    public void adicionar(Pet pet) {
        if (pet == null) {
            System.err.println("Erro: Não é possível adicionar um pet nulo.");
            return;
        }
        // Atribui um ID se o pet ainda não tiver um (assumindo que 0 é o ID padrão para um novo objeto)
        if (pet.getId() == 0) {
            pet.setId(nextId++);
        }
        this.pets.add(pet);
        System.out.println("[DAO] Pet '" + pet.getNome() + "' adicionado. ID: " + pet.getId());
    }

    /**
     * Lista todos os pets atualmente armazenados.
     * @return Uma lista contendo todos os objetos Pet.
     */
    public List<Pet> listarTodos() {
        // Retorna uma nova ArrayList para evitar modificações externas diretas na lista interna
        return new ArrayList<>(this.pets);
    }

    /**
     * Busca um pet pelo seu ID.
     * @param id O ID do pet a ser buscado.
     * @return O objeto Pet se encontrado, ou null caso contrário.
     */
    public Pet buscarPorId(int id) {
        for (Pet pet : this.pets) {
            if (pet.getId() == id) {
                return pet;
            }
        }
        System.out.println("[DAO] Pet com ID " + id + " não encontrado.");
        return null;
    }

    /**
     * Remove um pet da lista.
     * @param pet O objeto Pet a ser removido.
     */
    public void remover(Pet pet) {
        if (pet == null) {
            System.err.println("Erro: Não é possível remover um pet nulo.");
            return;
        }
        // Usa Iterator para remoção segura durante a iteração
        Iterator<Pet> iterator = this.pets.iterator();
        while (iterator.hasNext()) {
            Pet p = iterator.next();
            if (p.getId() == pet.getId()) { // Compara pelo ID para garantir que é o mesmo objeto
                iterator.remove();
                System.out.println("[DAO] Pet '" + pet.getNome() + "' removido.");
                return;
            }
        }
        System.out.println("[DAO] Pet '" + pet.getNome() + "' não encontrado para remoção.");
    }

    /**
     * Atualiza as informações de um pet existente.
     * @param pet O objeto Pet com as informações atualizadas.
     */
    public void atualizar(Pet pet) {
        if (pet == null) {
            System.err.println("Erro: Não é possível atualizar um pet nulo.");
            return;
        }
        for (int i = 0; i < this.pets.size(); i++) {
            if (this.pets.get(i).getId() == pet.getId()) {
                this.pets.set(i, pet); // Substitui o objeto existente pelo atualizado
                System.out.println("[DAO] Pet '" + pet.getNome() + "' atualizado.");
                return;
            }
        }
        System.out.println("[DAO] Pet '" + pet.getNome() + "' não encontrado para atualização.");
    }
}
package br.com.veterinaria.model;

public class Cliente {
    // Contador estático para gerar IDs únicos para cada novo cliente
    private static int nextId = 1;

    // Atributos do cliente
    private int id; // ID único do cliente
    private String nome;
    private String telefone;
    private String email;

    /**
     * Construtor para criar um novo objeto Cliente.
     * Um ID único é automaticamente atribuído.
     * @param nome O nome completo do cliente.
     * @param telefone O número de telefone do cliente.
     * @param email O endereço de e-mail do cliente (opcional).
     */
    public Cliente(String nome, String telefone, String email) {
        this.id = nextId++; // Atribui um ID único e incrementa o contador
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    // --- Getters ---

    /**
     * Retorna o ID único do cliente.
     * @return O ID do cliente.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o nome do cliente.
     * @return O nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o telefone do cliente.
     * @return O telefone do cliente.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Retorna o email do cliente.
     * @return O email do cliente.
     */
    public String getEmail() {
        return email;
    }

    // --- Setters (se necessário para atualizações) ---

    /**
     * Define o ID do cliente. Geralmente usado apenas pelo DAO para persistência.
     * @param id O novo ID para o cliente.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Define o nome do cliente.
     * @param nome O novo nome para o cliente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define o telefone do cliente.
     * @param telefone O novo telefone para o cliente.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Define o email do cliente.
     * @param email O novo email para o cliente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sobrescreve o método toString para uma representação legível do objeto Cliente.
     * Útil para exibir em ComboBoxes ou para depuração.
     * @return Uma String que representa o Cliente.
     */
    @Override
    public String toString() {
        return nome + " (ID: " + id + ")";
    }
}
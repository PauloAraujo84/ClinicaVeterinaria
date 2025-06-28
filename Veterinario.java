package br.com.veterinaria.model;

public class Veterinario {
    // Contador estático para gerar IDs únicos para cada novo veterinário
    private static int nextId = 1;

    // Atributos do veterinário
    private int id; // ID único do veterinário
    private String nome;
    private String crmv; // Conselho Regional de Medicina Veterinária

    /**
     * Construtor para criar um novo objeto Veterinario.
     * Um ID único é automaticamente atribuído.
     * @param nome O nome completo do veterinário.
     * @param crmv O número de registro no Conselho Regional de Medicina Veterinária.
     */
    public Veterinario(String nome, String crmv) {
        this.id = nextId++; // Atribui um ID único e incrementa o contador
        this.nome = nome;
        this.crmv = crmv;
    }

    // --- Getters ---

    /**
     * Retorna o ID único do veterinário.
     * @return O ID do veterinário.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o nome do veterinário.
     * @return O nome do veterinário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o número de registro no CRMV do veterinário.
     * @return O CRMV do veterinário.
     */
    public String getCrmv() {
        return crmv;
    }

    // --- Setters (se necessário para atualizações, especialmente o ID pelo DAO) ---

    /**
     * Define o ID do veterinário. Geralmente usado apenas pelo DAO para persistência.
     * @param id O novo ID para o veterinário.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Define o nome do veterinário.
     * @param nome O novo nome para o veterinário.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define o número de registro no CRMV do veterinário.
     * @param crmv O novo CRMV para o veterinário.
     */
    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    /**
     * Sobrescreve o método toString para uma representação legível do objeto Veterinario.
     * Útil para exibir em ComboBoxes ou para depuração.
     * @return Uma String que representa o Veterinario.
     */
    @Override
    public String toString() {
        return nome + " (" + crmv + ", ID: " + id + ")";
    }
}
package br.com.veterinaria.model;

public class Pet {
    // Contador estático para gerar IDs únicos para cada novo pet
    private static int nextId = 1;

    // Atributos do pet
    private int id; // ID único do pet
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private Cliente proprietario; // O proprietário (cliente) do pet

    /**
     * Construtor para criar um novo objeto Pet.
     * Um ID único é automaticamente atribuído.
     * @param nome O nome do pet.
     * @param especie A espécie do pet (ex: "Cachorro", "Gato").
     * @param raca A raça do pet.
     * @param idade A idade do pet em anos.
     * @param proprietario O objeto Cliente que é o proprietário do pet.
     */
    public Pet(String nome, String especie, String raca, int idade, Cliente proprietario) {
        this.id = nextId++; // Atribui um ID único e incrementa o contador
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.proprietario = proprietario;
    }

    // --- Getters ---

    /**
     * Retorna o ID único do pet.
     * @return O ID do pet.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o nome do pet.
     * @return O nome do pet.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a espécie do pet.
     * @return A espécie do pet.
     */
    public String getEspecie() {
        return especie;
    }

    /**
     * Retorna a raça do pet.
     * @return A raça do pet.
     */
    public String getRaca() {
        return raca;
    }

    /**
     * Retorna a idade do pet.
     * @return A idade do pet.
     */
    public int getIdade() {
        return idade;
    }

    /**
     * Retorna o objeto Cliente que é o proprietário do pet.
     * @return O proprietário do pet.
     */
    public Cliente getProprietario() {
        return proprietario;
    }

    // --- Setters (se necessário para atualizações) ---

    /**
     * Define o ID do pet. Geralmente usado apenas pelo DAO para persistência.
     * @param id O novo ID para o pet.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Define o nome do pet.
     * @param nome O novo nome para o pet.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define a espécie do pet.
     * @param especie A nova espécie para o pet.
     */
    public void setEspecie(String especie) {
        this.especie = especie;
    }

    /**
     * Define a raça do pet.
     * @param raca A nova raça para o pet.
     */
    public void setRaca(String raca) {
        this.raca = raca;
    }

    /**
     * Define a idade do pet.
     * @param idade A nova idade para o pet.
     */
    public void setIdade(int idade) {
        this.idade = idade;
    }

    /**
     * Define o proprietário do pet.
     * @param proprietario O novo objeto Cliente proprietário do pet.
     */
    public void setProprietario(Cliente proprietario) {
        this.proprietario = proprietario;
    }

    /**
     * Sobrescreve o método toString para uma representação legível do objeto Pet.
     * Útil para exibir em ComboBoxes ou para depuração.
     * @return Uma String que representa o Pet.
     */
    @Override
    public String toString() {
        return nome + " (" + especie + ", ID: " + id + ")";
    }
}
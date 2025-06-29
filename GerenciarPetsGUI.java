package br.com.veterinaria.gui;

import br.com.veterinaria.facade.ClinicaFacade;
import br.com.veterinaria.model.Cliente;
import br.com.veterinaria.model.Pet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GerenciarPetsGUI extends JFrame {
    private ClinicaFacade facade;
    private JTable petsTable;
    private DefaultTableModel tableModel;

    // Componentes para Adicionar Pet
    private JTextField txtNome;
    private JTextField txtEspecie;
    private JTextField txtRaca;
    private JTextField txtIdade;
    private JComboBox<Cliente> cmbProprietario; // ComboBox para selecionar o cliente
    private JButton btnAdicionar;

    // Componente para Remover Pet
    private JButton btnRemover;

    public GerenciarPetsGUI(ClinicaFacade facade) {
        this.facade = facade;
        setTitle("Gerenciar Pets");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadInitialData(); // Carrega clientes para o ComboBox e pets para a tabela
        addListeners();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Painel de Adição de Pet (Superior) ---
        JPanel adicionarPanel = new JPanel(new GridLayout(6, 2, 10, 5)); // Ajustado para 6 linhas
        adicionarPanel.setBorder(BorderFactory.createTitledBorder("Adicionar Novo Pet"));

        adicionarPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        adicionarPanel.add(txtNome);

        adicionarPanel.add(new JLabel("Espécie:"));
        txtEspecie = new JTextField();
        adicionarPanel.add(txtEspecie);

        adicionarPanel.add(new JLabel("Raça:"));
        txtRaca = new JTextField();
        adicionarPanel.add(txtRaca);

        adicionarPanel.add(new JLabel("Idade:"));
        txtIdade = new JTextField();
        adicionarPanel.add(txtIdade);

        adicionarPanel.add(new JLabel("Proprietário:"));
        cmbProprietario = new JComboBox<>(); // Inicialização do JComboBox
        adicionarPanel.add(cmbProprietario);

        btnAdicionar = new JButton("Adicionar Pet"); // Botão para adicionar
        adicionarPanel.add(new JLabel("")); // Espaçador
        adicionarPanel.add(btnAdicionar);

        mainPanel.add(adicionarPanel, BorderLayout.NORTH);

        // --- Painel de Listagem e Remoção de Pets (Central/Inferior) ---
        JPanel listagemPanel = new JPanel(new BorderLayout(10, 10));
        listagemPanel.setBorder(BorderFactory.createTitledBorder("Pets Cadastrados"));

        String[] columnNames = {"ID", "Nome", "Espécie", "Raça", "Idade", "Proprietário"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna as células da tabela não editáveis
            }
        };
        petsTable = new JTable(tableModel);
        petsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(petsTable);
        listagemPanel.add(scrollPane, BorderLayout.CENTER);

        btnRemover = new JButton("Remover Pet Selecionado");
        JPanel removerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        removerPanel.add(btnRemover);
        listagemPanel.add(removerPanel, BorderLayout.SOUTH);

        mainPanel.add(listagemPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void loadInitialData() {
        loadProprietarios(); // **Carrega clientes no ComboBox**
        loadPets();          // Carrega pets na tabela
    }

    /**
     * Carrega a lista de clientes cadastrados no ComboBox de proprietários.
     * Essencial para que o usuário possa selecionar o proprietário do novo pet.
     */
    private void loadProprietarios() {
        cmbProprietario.removeAllItems();
        List<Cliente> clientes = facade.getClientesCadastrados();
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há clientes cadastrados. Cadastre um cliente para adicionar um pet.", "Aviso", JOptionPane.WARNING_MESSAGE);
            btnAdicionar.setEnabled(false); // Desabilita o botão de adicionar se não houver clientes
            return;
        }
        for (Cliente cliente : clientes) {
            cmbProprietario.addItem(cliente);
        }
        btnAdicionar.setEnabled(true); // Habilita o botão se houver clientes
    }

    private void addListeners() {
        btnAdicionar.addActionListener(e -> adicionarPet()); // Listener para o botão Adicionar
        btnRemover.addActionListener(e -> removerPet());
    }

    /**
     * **Método responsável por adicionar um novo pet.**
     * Ele pega os dados dos campos, valida e chama a facade para cadastrar.
     */
    private void adicionarPet() {
        String nome = txtNome.getText().trim();
        String especie = txtEspecie.getText().trim();
        String raca = txtRaca.getText().trim();
        String idadeStr = txtIdade.getText().trim();
        Cliente proprietario = (Cliente) cmbProprietario.getSelectedItem(); // Obtém o cliente selecionado

        if (nome.isEmpty() || especie.isEmpty() || raca.isEmpty() || idadeStr.isEmpty() || proprietario == null) {
            JOptionPane.showMessageDialog(this, "Todos os campos e o proprietário são obrigatórios.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idade;
        try {
            idade = Integer.parseInt(idadeStr);
            if (idade <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade deve ser um número inteiro positivo.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Pet novoPet = new Pet(nome, especie, raca, idade, proprietario);
        facade.cadastrarPet(novoPet); // Chama o método da facade para cadastrar o pet
        JOptionPane.showMessageDialog(this, "Pet '" + nome + "' cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        // Limpa os campos após o cadastro
        txtNome.setText("");
        txtEspecie.setText("");
        txtRaca.setText("");
        txtIdade.setText("");
        // cmbProprietario.setSelectedIndex(0); // Opcional: resetar seleção do proprietário para o primeiro item
        loadPets(); // Recarrega a tabela para mostrar o novo pet
    }

    /**
     * Carrega todos os pets da ClinicaFacade e os exibe na tabela.
     */
    private void loadPets() {
        tableModel.setRowCount(0); // Limpa a tabela
        List<Pet> pets = facade.getPetsCadastrados();

        for (Pet pet : pets) {
            Object[] rowData = {
                    pet.getId(),
                    pet.getNome(),
                    pet.getEspecie(),
                    pet.getRaca(),
                    pet.getIdade(),
                    (pet.getProprietario() != null) ? pet.getProprietario().getNome() : "N/A"
            };
            tableModel.addRow(rowData);
        }
    }

    /**
     * Remove o pet selecionado na tabela.
     */
    private void removerPet() {
        int selectedRow = petsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pet para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int petId = (int) tableModel.getValueAt(selectedRow, 0); // Pega o ID do pet da linha selecionada

        Pet petParaRemover = null;
        for (Pet pet : facade.getPetsCadastrados()) {
            if (pet.getId() == petId) {
                petParaRemover = pet;
                break;
            }
        }

        if (petParaRemover != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja remover o pet '" + petParaRemover.getNome() + "'?",
                    "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                facade.removerPet(petParaRemover); // Chama a facade para remover
                loadPets(); // Recarrega a tabela
                JOptionPane.showMessageDialog(this, "Pet removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erro: Pet não encontrado para remoção.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
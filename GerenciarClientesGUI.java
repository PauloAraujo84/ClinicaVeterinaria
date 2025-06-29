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

public class GerenciarClientesGUI extends JFrame {
    private ClinicaFacade facade;
    private JTable clientesTable;
    private DefaultTableModel tableModel;

    // Componentes para Adicionar Cliente
    private JTextField txtNome;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JButton btnAdicionar;

    // Componente para Remover Cliente
    private JButton btnRemover;

    public GerenciarClientesGUI(ClinicaFacade facade) {
        this.facade = facade;
        setTitle("Gerenciar Clientes");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadClientes(); // Carrega clientes na tabela
        addListeners();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Painel de Adição de Cliente (Superior) ---
        JPanel adicionarPanel = new JPanel(new GridLayout(4, 2, 10, 5));
        adicionarPanel.setBorder(BorderFactory.createTitledBorder("Adicionar Novo Cliente"));

        adicionarPanel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        adicionarPanel.add(txtNome);

        adicionarPanel.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        adicionarPanel.add(txtTelefone);

        adicionarPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        adicionarPanel.add(txtEmail);

        btnAdicionar = new JButton("Adicionar Cliente");
        adicionarPanel.add(new JLabel("")); // Espaçador
        adicionarPanel.add(btnAdicionar);

        mainPanel.add(adicionarPanel, BorderLayout.NORTH);

        // --- Painel de Listagem e Remoção de Clientes (Central/Inferior) ---
        JPanel listagemPanel = new JPanel(new BorderLayout(10, 10));
        listagemPanel.setBorder(BorderFactory.createTitledBorder("Clientes Cadastrados"));

        String[] columnNames = {"ID", "Nome", "Telefone", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Células da tabela não editáveis
            }
        };
        clientesTable = new JTable(tableModel);
        clientesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(clientesTable);
        listagemPanel.add(scrollPane, BorderLayout.CENTER);

        btnRemover = new JButton("Remover Cliente Selecionado");
        JPanel removerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        removerPanel.add(btnRemover);
        listagemPanel.add(removerPanel, BorderLayout.SOUTH);

        mainPanel.add(listagemPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void addListeners() {
        btnAdicionar.addActionListener(e -> adicionarCliente());
        btnRemover.addActionListener(e -> removerCliente());
    }

    /**
     * Adiciona um novo cliente com base nos dados dos campos de entrada.
     */
    private void adicionarCliente() {
        String nome = txtNome.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String email = txtEmail.getText().trim();

        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente novoCliente = new Cliente(nome, telefone, email);
        facade.cadastrarCliente(novoCliente);
        JOptionPane.showMessageDialog(this, "Cliente '" + nome + "' cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        // Limpa os campos após o cadastro
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        loadClientes(); // Recarrega a tabela para mostrar o novo cliente
    }

    /**
     * Carrega todos os clientes da ClinicaFacade e os exibe na tabela.
     */
    private void loadClientes() {
        tableModel.setRowCount(0); // Limpa a tabela
        List<Cliente> clientes = facade.getClientesCadastrados();

        for (Cliente cliente : clientes) {
            Object[] rowData = {
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getTelefone(),
                    cliente.getEmail()
            };
            tableModel.addRow(rowData);
        }
    }

    /**
     * Remove o cliente selecionado na tabela.
     */
    private void removerCliente() {
        int selectedRow = clientesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int clienteId = (int) tableModel.getValueAt(selectedRow, 0); // Pega o ID do cliente da linha selecionada

        Cliente clienteParaRemover = null;
        for (Cliente cliente : facade.getClientesCadastrados()) {
            if (cliente.getId() == clienteId) {
                clienteParaRemover = cliente;
                break;
            }
        }

        if (clienteParaRemover != null) {
            // Verifica se o cliente possui pets antes de remover
            boolean temPets = false;
            for(Pet pet : facade.getPetsCadastrados()) {
                if(pet.getProprietario() != null && pet.getProprietario().getId() == clienteParaRemover.getId()) {
                    temPets = true;
                    break;
                }
            }

            if (temPets) {
                JOptionPane.showMessageDialog(this, "Não é possível remover este cliente. Ele possui pets cadastrados.", "Erro de Remoção", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja remover o cliente '" + clienteParaRemover.getNome() + "'?",
                    "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                facade.removerCliente(clienteParaRemover); // Chama a facade para remover
                loadClientes(); // Recarrega a tabela
                JOptionPane.showMessageDialog(this, "Cliente removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erro: Cliente não encontrado para remoção.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
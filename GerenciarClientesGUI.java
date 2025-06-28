package br.com.veterinaria.gui;

import br.com.veterinaria.facade.ClinicaFacade;
import br.com.veterinaria.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GerenciarClientesGUI extends JFrame {
    private ClinicaFacade facade;

    // Componentes para Cadastro
    private JTextField txtNomeCliente;
    private JTextField txtTelefoneCliente;
    private JTextField txtEmailCliente;
    private JButton btnCadastrarCliente;

    // Componentes para Listagem e Remoção
    private JTable tblClientes;
    private DefaultTableModel tableModel;
    private JButton btnRemoverCliente;

    public GerenciarClientesGUI(ClinicaFacade facade) {
        this.facade = facade;
        setTitle("Gerenciar Clientes (Proprietários)");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadTableData(); // Carrega os clientes existentes na tabela
        addListeners();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // Painel principal com borda
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Painel de Cadastro ---
        JPanel cadastroPanel = new JPanel(new GridBagLayout());
        cadastroPanel.setBorder(BorderFactory.createTitledBorder("Cadastrar Novo Cliente"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        cadastroPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtNomeCliente = new JTextField(20);
        cadastroPanel.add(txtNomeCliente, gbc);

        // Telefone
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        cadastroPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtTelefoneCliente = new JTextField(20);
        cadastroPanel.add(txtTelefoneCliente, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        cadastroPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtEmailCliente = new JTextField(20);
        cadastroPanel.add(txtEmailCliente, gbc);

        // Botão Cadastrar
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        btnCadastrarCliente = new JButton("Cadastrar Cliente");
        cadastroPanel.add(btnCadastrarCliente, gbc);

        mainPanel.add(cadastroPanel, BorderLayout.NORTH);

        // --- Painel de Listagem e Remoção ---
        JPanel listagemPanel = new JPanel(new BorderLayout());
        listagemPanel.setBorder(BorderFactory.createTitledBorder("Clientes Cadastrados"));

        String[] columnNames = {"ID", "Nome", "Telefone", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna as células não editáveis
            }
        };
        tblClientes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblClientes);
        listagemPanel.add(scrollPane, BorderLayout.CENTER);

        btnRemoverCliente = new JButton("Remover Cliente Selecionado");
        JPanel btnRemoverPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRemoverPanel.add(btnRemoverCliente);
        listagemPanel.add(btnRemoverPanel, BorderLayout.SOUTH);

        mainPanel.add(listagemPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void loadTableData() {
        tableModel.setRowCount(0); // Limpa a tabela antes de recarregar
        List<Cliente> clientes = facade.getClientesCadastrados();
        for (Cliente cliente : clientes) {
            Object[] rowData = {cliente.getId(), cliente.getNome(), cliente.getTelefone(), cliente.getEmail()};
            tableModel.addRow(rowData);
        }
    }

    private void addListeners() {
        btnCadastrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });

        btnRemoverCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerCliente();
            }
        });
    }

    private void cadastrarCliente() {
        String nome = txtNomeCliente.getText().trim();
        String telefone = txtTelefoneCliente.getText().trim();
        String email = txtEmailCliente.getText().trim();

        if (nome.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e Telefone são obrigatórios.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente novoCliente = new Cliente(nome, telefone, email);
        facade.cadastrarCliente(novoCliente);
        JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        // Limpa os campos e recarrega a tabela
        txtNomeCliente.setText("");
        txtTelefoneCliente.setText("");
        txtEmailCliente.setText("");
        loadTableData();
    }

    private void removerCliente() {
        int selectedRow = tblClientes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um cliente para remover.", "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtém o ID do cliente da linha selecionada (assumindo que o ID está na primeira coluna)
        int clienteId = (int) tableModel.getValueAt(selectedRow, 0);

        // Encontra o objeto Cliente correspondente na lista da fachada
        Cliente clienteParaRemover = null;
        for (Cliente c : facade.getClientesCadastrados()) {
            if (c.getId() == clienteId) {
                clienteParaRemover = c;
                break;
            }
        }

        if (clienteParaRemover != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja remover o cliente " + clienteParaRemover.getNome() + "?",
                    "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                facade.removerCliente(clienteParaRemover);
                JOptionPane.showMessageDialog(this, "Cliente removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                loadTableData(); // Recarrega a tabela após a remoção
            }
        } else {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
package br.com.veterinaria.gui;

import br.com.veterinaria.facade.ClinicaFacade;
import br.com.veterinaria.model.Cliente;
import br.com.veterinaria.model.Consulta;
import br.com.veterinaria.model.Pet;
import br.com.veterinaria.model.Veterinario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AgendarConsultaGUI extends JFrame {
    private ClinicaFacade facade;

    // Componentes para agendar nova consulta
    private JComboBox<Cliente> cmbCliente;
    private JComboBox<Pet> cmbPet;
    private JComboBox<Veterinario> cmbVeterinario;
    private JTextField txtDataHora; // Formato esperado: dd/MM/yyyy HH:mm
    private JButton btnAgendar;

    // Componentes para listar e remover consultas
    private JTable consultasTable;
    private DefaultTableModel tableModel;
    private JButton btnRemoverConsulta;

    // Formatador de data para exibir e interpretar as datas
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public AgendarConsultaGUI(ClinicaFacade facade) {
        this.facade = facade;
        setTitle("Agendar e Gerenciar Consultas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadInitialData(); // Carrega clientes, pets, veterinários e consultas
        addListeners();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Painel de Agendamento (Parte Superior) ---
        JPanel agendamentoPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        agendamentoPanel.setBorder(BorderFactory.createTitledBorder("Agendar Nova Consulta"));

        agendamentoPanel.add(new JLabel("Cliente:"));
        cmbCliente = new JComboBox<>();
        agendamentoPanel.add(cmbCliente);

        agendamentoPanel.add(new JLabel("Pet:"));
        cmbPet = new JComboBox<>();
        agendamentoPanel.add(cmbPet);

        agendamentoPanel.add(new JLabel("Veterinário:"));
        cmbVeterinario = new JComboBox<>();
        agendamentoPanel.add(cmbVeterinario);

        agendamentoPanel.add(new JLabel("Data e Hora (dd/MM/yyyy HH:mm):"));
        txtDataHora = new JTextField();
        agendamentoPanel.add(txtDataHora);

        btnAgendar = new JButton("Agendar Consulta");
        agendamentoPanel.add(new JLabel("")); // Espaçador
        agendamentoPanel.add(btnAgendar);

        mainPanel.add(agendamentoPanel, BorderLayout.NORTH);

        // --- Painel de Listagem e Remoção (Parte Central e Inferior) ---
        JPanel listagemPanel = new JPanel(new BorderLayout(10, 10));
        listagemPanel.setBorder(BorderFactory.createTitledBorder("Consultas Agendadas"));

        String[] columnNames = {"ID", "Pet", "Proprietário", "Veterinário", "Data e Hora"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        consultasTable = new JTable(tableModel);
        consultasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(consultasTable);
        listagemPanel.add(scrollPane, BorderLayout.CENTER);

        btnRemoverConsulta = new JButton("Remover Consulta Selecionada");
        JPanel removerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        removerPanel.add(btnRemoverConsulta);
        listagemPanel.add(removerPanel, BorderLayout.SOUTH);

        mainPanel.add(listagemPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void loadInitialData() {
        // Carrega clientes no ComboBox de clientes
        List<Cliente> clientes = facade.getClientesCadastrados();
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há clientes cadastrados. Cadastre um cliente primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            btnAgendar.setEnabled(false); // Desabilita o agendamento
            return;
        }
        for (Cliente cliente : clientes) {
            cmbCliente.addItem(cliente);
        }

        // Adiciona um listener para atualizar o cmbPet quando o cliente muda
        cmbCliente.addActionListener(e -> loadPetsForSelectedCliente());
        // Carrega os pets inicialmente para o primeiro cliente (se houver)
        loadPetsForSelectedCliente();

        // Carrega veterinários no ComboBox de veterinários
        List<Veterinario> veterinarios = facade.getVeterinariosCadastrados();
        if (veterinarios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há veterinários cadastrados. Cadastre um veterinário primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            btnAgendar.setEnabled(false); // Desabilita o agendamento
            return;
        }
        for (Veterinario vet : veterinarios) {
            cmbVeterinario.addItem(vet);
        }

        loadConsultas(); // Carrega as consultas existentes na tabela
    }

    /**
     * Carrega os pets associados ao cliente selecionado no cmbCliente.
     */
    private void loadPetsForSelectedCliente() {
        cmbPet.removeAllItems(); // Limpa os pets anteriores
        Cliente selectedCliente = (Cliente) cmbCliente.getSelectedItem();
        if (selectedCliente != null) {
            List<Pet> allPets = facade.getPetsCadastrados();
            for (Pet pet : allPets) {
                if (pet.getProprietario() != null && pet.getProprietario().getId() == selectedCliente.getId()) {
                    cmbPet.addItem(pet);
                }
            }
            if (cmbPet.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "O cliente selecionado não possui pets cadastrados.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void addListeners() {
        btnAgendar.addActionListener(e -> agendarNovaConsulta());
        btnRemoverConsulta.addActionListener(e -> removerConsultaSelecionada());
    }

    private void agendarNovaConsulta() {
        Cliente cliente = (Cliente) cmbCliente.getSelectedItem();
        Pet pet = (Pet) cmbPet.getSelectedItem();
        Veterinario veterinario = (Veterinario) cmbVeterinario.getSelectedItem();
        String dataHoraStr = txtDataHora.getText().trim();

        if (cliente == null || pet == null || veterinario == null || dataHoraStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos de agendamento são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date dataHora;
        try {
            dataHora = dateFormat.parse(dataHoraStr);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de data e hora inválido. Use dd/MM/yyyy HH:mm (ex: 25/06/2024 14:30).", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dataHora.before(new Date())) {
            JOptionPane.showMessageDialog(this, "Não é possível agendar consultas para datas passadas.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
            return;
        }

        facade.agendarConsulta(pet, cliente, veterinario, dataHora);
        JOptionPane.showMessageDialog(this, "Consulta agendada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        // Limpa o campo de data/hora após o agendamento
        txtDataHora.setText("");
        loadConsultas(); // Recarrega a tabela de consultas
    }

    private void loadConsultas() {
        tableModel.setRowCount(0); // Limpa a tabela
        List<Consulta> consultas = facade.buscarTodasAsConsultas();

        if (consultas.isEmpty()) {
            return; // Nenhuma consulta para exibir
        }

        for (Consulta consulta : consultas) {
            Object[] rowData = {
                    consulta.getId(),
                    consulta.getPet().getNome(),
                    consulta.getCliente().getNome(),
                    consulta.getVeterinario().getNome(),
                    dateFormat.format(consulta.getData()) // Formata a data para exibição
            };
            tableModel.addRow(rowData);
        }
    }

    private void removerConsultaSelecionada() {
        int selectedRow = consultasTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int consultaId = (int) tableModel.getValueAt(selectedRow, 0); // Pega o ID da consulta na tabela

        // Você precisa de um método na Facade para buscar a consulta pelo ID
        // Como o `buscarTodasAsConsultas()` retorna a lista, vamos buscar na lista
        Consulta consultaParaRemover = null;
        for (Consulta c : facade.buscarTodasAsConsultas()) {
            if (c.getId() == consultaId) {
                consultaParaRemover = c;
                break;
            }
        }

        if (consultaParaRemover != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja remover a consulta do Pet '" + consultaParaRemover.getPet().getNome() + "' em " + dateFormat.format(consultaParaRemover.getData()) + "?",
                    "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                facade.removerConsulta(consultaParaRemover); // Chama a facade para remover
                loadConsultas(); // Recarrega a tabela
                JOptionPane.showMessageDialog(this, "Consulta removida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erro: Consulta não encontrada no sistema.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
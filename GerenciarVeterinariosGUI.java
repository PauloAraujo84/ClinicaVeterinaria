package br.com.veterinaria.gui;

import br.com.veterinaria.facade.ClinicaFacade;
import br.com.veterinaria.model.Veterinario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GerenciarVeterinariosGUI extends JFrame {
    private ClinicaFacade facade;
    private JTable veterinariosTable;
    private DefaultTableModel tableModel;

    // Botões para as novas funcionalidades
    private JButton btnAdicionar; // Botão para adicionar (opcional, pode levar a outra GUI)
    private JButton btnRemover;   // Botão para remover

    public GerenciarVeterinariosGUI(ClinicaFacade facade) {
        this.facade = facade;
        setTitle("Gerenciar Veterinários");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadVeterinarios(); // Carrega os veterinários ao iniciar a tela
        addListeners();     // Adiciona os listeners para os novos botões
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"ID", "Nome", "CRMV"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Células da tabela não editáveis
            }
        };
        veterinariosTable = new JTable(tableModel);
        veterinariosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permite selecionar apenas uma linha
        JScrollPane scrollPane = new JScrollPane(veterinariosTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnAdicionar = new JButton("Adicionar Veterinário");
        btnRemover = new JButton("Remover Veterinário");

        buttonPanel.add(btnAdicionar);
        buttonPanel.add(btnRemover);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void addListeners() {
        // Listener para o botão Adicionar
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ao clicar em Adicionar, você pode abrir uma nova GUI para cadastro
                // Por simplicidade, faremos um JOptionPane para exemplo.
                String nome = JOptionPane.showInputDialog(GerenciarVeterinariosGUI.this, "Digite o nome do novo veterinário:");
                if (nome == null || nome.trim().isEmpty()) {
                    return; // Usuário cancelou ou digitou vazio
                }
                String crmv = JOptionPane.showInputDialog(GerenciarVeterinariosGUI.this, "Digite o CRMV do novo veterinário:");
                if (crmv == null || crmv.trim().isEmpty()) {
                    return; // Usuário cancelou ou digitou vazio
                }

                // Cria e cadastra o novo veterinário usando a facade
                facade.cadastrarVeterinario(new Veterinario(nome, crmv));
                loadVeterinarios(); // Recarrega a tabela para mostrar o novo veterinário
                JOptionPane.showMessageDialog(GerenciarVeterinariosGUI.this, "Veterinário " + nome + " cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Listener para o botão Remover
        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerVeterinario();
            }
        });
    }

    /**
     * Carrega todos os veterinários da ClinicaFacade e os exibe na tabela.
     */
    private void loadVeterinarios() {
        tableModel.setRowCount(0); // Limpa a tabela

        List<Veterinario> veterinarios = facade.getVeterinariosCadastrados();
        if (veterinarios.isEmpty()) {
            // Não exibe um JOptionPane se a lista estiver vazia ao iniciar, apenas ao tentar remover.
            // JOptionPane.showMessageDialog(this, "Nenhum veterinário cadastrado no momento.", "Informação", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Veterinario vet : veterinarios) {
            Object[] rowData = {
                    vet.getId(),
                    vet.getNome(),
                    vet.getCrmv()
            };
            tableModel.addRow(rowData);
        }
    }

    /**
     * Remove o veterinário selecionado na tabela.
     */
    private void removerVeterinario() {
        int selectedRow = veterinariosTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veterinário para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtém o ID do veterinário da linha selecionada (coluna ID)
        int veterinarioId = (int) tableModel.getValueAt(selectedRow, 0);

        // Busca o objeto Veterinario real pela facade
        Veterinario veterinarioParaRemover = null;
        for (Veterinario vet : facade.getVeterinariosCadastrados()) {
            if (vet.getId() == veterinarioId) {
                veterinarioParaRemover = vet;
                break;
            }
        }

        if (veterinarioParaRemover != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja remover o veterinário " + veterinarioParaRemover.getNome() + "?",
                    "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                facade.removerVeterinario(veterinarioParaRemover); // Chama a facade para remover
                loadVeterinarios(); // Recarrega a tabela para atualizar a visualização
                JOptionPane.showMessageDialog(this, "Veterinário removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veterinário não encontrado para remoção.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
package br.com.veterinaria.gui;

import br.com.veterinaria.facade.ClinicaFacade;
import br.com.veterinaria.model.Pet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GerenciarPetsGUI extends JFrame {
    private ClinicaFacade facade;

    private JTable tblPets;
    private DefaultTableModel tableModel;
    private JButton btnRemoverPet;
    private JButton btnFechar;

    public GerenciarPetsGUI(ClinicaFacade facade) {
        this.facade = facade;
        setTitle("Gerenciar Pets");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadTableData(); // Carrega os pets existentes na tabela
        addListeners();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Painel de Listagem ---
        JPanel listagemPanel = new JPanel(new BorderLayout());
        listagemPanel.setBorder(BorderFactory.createTitledBorder("Pets Cadastrados"));

        String[] columnNames = {"ID", "Nome", "Espécie", "Raça", "Idade", "Proprietário"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int intColumn) {
                return false; // Torna as células não editáveis
            }
        };
        tblPets = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblPets);
        listagemPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(listagemPanel, BorderLayout.CENTER);

        // --- Painel de Botões ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRemoverPet = new JButton("Remover Pet Selecionado");
        btnFechar = new JButton("Fechar");
        buttonPanel.add(btnRemoverPet);
        buttonPanel.add(btnFechar);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadTableData() {
        tableModel.setRowCount(0); // Limpa a tabela antes de recarregar
        List<Pet> pets = facade.getPetsCadastrados();
        for (Pet pet : pets) {
            Object[] rowData = {
                    pet.getId(),
                    pet.getNome(),
                    pet.getEspecie(),
                    pet.getRaca(),
                    pet.getIdade(),
                    pet.getProprietario() != null ? pet.getProprietario().getNome() : "N/A"
            };
            tableModel.addRow(rowData);
        }
    }

    private void addListeners() {
        btnRemoverPet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerPet();
            }
        });

        btnFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela
            }
        });
    }

    private void removerPet() {
        int selectedRow = tblPets.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um pet para remover.", "Nenhuma Seleção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtém o ID do pet da linha selecionada
        int petId = (int) tableModel.getValueAt(selectedRow, 0);

        // Encontra o objeto Pet correspondente na lista da fachada
        Pet petParaRemover = null;
        for (Pet p : facade.getPetsCadastrados()) {
            if (p.getId() == petId) {
                petParaRemover = p;
                break;
            }
        }

        if (petParaRemover != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja remover o pet " + petParaRemover.getNome() + "?",
                    "Confirmar Remoção", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                facade.removerPet(petParaRemover);
                JOptionPane.showMessageDialog(this, "Pet removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                loadTableData(); // Recarrega a tabela após a remoção
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pet não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
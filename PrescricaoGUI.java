package br.com.veterinaria.gui;

import br.com.veterinaria.model.Consulta;
import br.com.veterinaria.model.Medicamento;
import br.com.veterinaria.model.Prescricao;
import br.com.veterinaria.adapter.DataAdapter; // Para formatar a data da consulta

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PrescricaoGUI extends JFrame {
    private Prescricao prescricao; // A prescrição a ser exibida

    // Componentes da interface
    private JLabel lblPetNome;
    private JLabel lblClienteNome;
    private JLabel lblVeterinarioNome;
    private JLabel lblDataConsulta;
    private JTextArea taMedicamentos; // Para exibir a lista de medicamentos
    private JButton btnFechar;

    /**
     * Construtor da tela de Prescrição.
     * @param prescricao O objeto Prescricao cujos detalhes serão exibidos.
     */
    public PrescricaoGUI(Prescricao prescricao) {
        this.prescricao = prescricao;
        setTitle("Detalhes da Prescrição");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        displayPrescriptionDetails(); // Preenche os componentes com os dados da prescrição
        addListeners();
    }

    /**
     * Inicializa e configura todos os componentes visuais da tela.
     */
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Rótulos de Informações da Consulta
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Pet:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Cliente:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Veterinário:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Data da Consulta:"), gbc);

        // Campos de Exibição das Informações da Consulta
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Faz o componente expandir horizontalmente
        lblPetNome = new JLabel();
        panel.add(lblPetNome, gbc);

        gbc.gridy++;
        lblClienteNome = new JLabel();
        panel.add(lblClienteNome, gbc);

        gbc.gridy++;
        lblVeterinarioNome = new JLabel();
        panel.add(lblVeterinarioNome, gbc);

        gbc.gridy++;
        lblDataConsulta = new JLabel();
        panel.add(lblDataConsulta, gbc);

        // Rótulo para Medicamentos
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Ocupa duas colunas
        panel.add(new JLabel("Medicamentos Prescritos:"), gbc);

        // Área de Texto para Medicamentos
        gbc.gridy++;
        taMedicamentos = new JTextArea(8, 30); // 8 linhas de altura, 30 colunas
        taMedicamentos.setEditable(false); // Não permite edição
        taMedicamentos.setLineWrap(true);
        taMedicamentos.setWrapStyleWord(true);
        JScrollPane scrollMedicamentos = new JScrollPane(taMedicamentos); // Adiciona barra de rolagem
        panel.add(scrollMedicamentos, gbc);

        // Botão Fechar
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        btnFechar = new JButton("Fechar");
        panel.add(btnFechar, gbc);

        add(panel, BorderLayout.CENTER);
    }

    /**
     * Preenche os componentes da GUI com os detalhes da prescrição.
     */
    private void displayPrescriptionDetails() {
        if (prescricao == null) {
            JOptionPane.showMessageDialog(this, "Nenhuma prescrição para exibir.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Consulta consulta = prescricao.getConsulta();

        // Exibe informações da consulta
        if (consulta != null) {
            lblPetNome.setText(consulta.getPet() != null ? consulta.getPet().getNome() : "N/A");
            lblClienteNome.setText(consulta.getCliente() != null ? consulta.getCliente().getNome() : "N/A");
            lblVeterinarioNome.setText(consulta.getVeterinario() != null ? consulta.getVeterinario().getNome() : "N/A");
            lblDataConsulta.setText(consulta.getData() != null ? DataAdapter.formatarData(consulta.getData()) : "N/A");
        } else {
            lblPetNome.setText("N/A");
            lblClienteNome.setText("N/A");
            lblVeterinarioNome.setText("N/A");
            lblDataConsulta.setText("N/A");
        }

        // Exibe a lista de medicamentos
        List<Medicamento> medicamentos = prescricao.getMedicamentos();
        if (medicamentos != null && !medicamentos.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Medicamento med : medicamentos) {
                sb.append("- ").append(med.getNome());
                if (med.getDosagem() != null && !med.getDosagem().isEmpty()) {
                    sb.append(" (").append(med.getDosagem()).append(")");
                }
                sb.append("\n");
            }
            taMedicamentos.setText(sb.toString());
        } else {
            taMedicamentos.setText("Nenhum medicamento prescrito.");
        }
    }

    /**
     * Adiciona os ouvintes de eventos aos botões.
     */
    private void addListeners() {
        btnFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela
            }
        });
    }
}
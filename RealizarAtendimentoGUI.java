package br.com.veterinaria.gui;

import br.com.veterinaria.facade.ClinicaFacade;
import br.com.veterinaria.model.Consulta;
import br.com.veterinaria.model.Medicamento;
import br.com.veterinaria.model.Prescricao; // Importar Prescricao

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RealizarAtendimentoGUI extends JFrame {
    private ClinicaFacade facade;

    private JComboBox<Consulta> cbConsulta;
    private JTextArea taDiagnostico;
    private JTextArea taMedicamentos;
    private JButton btnRealizarAtendimento;
    private JButton btnCancelar;

    public RealizarAtendimentoGUI(ClinicaFacade facade) {
        this.facade = facade;
        setTitle("Realizar Atendimento");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadConsultas();
        addListeners();
    }

    private void initComponents() {
        // ... (código initComponents existente, sem alterações aqui) ...
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Seleção de Consulta
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Selecionar Consulta:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cbConsulta = new JComboBox<>();
        panel.add(cbConsulta, gbc);

        // Diagnóstico
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Diagnóstico:"), gbc);
        gbc.gridy++;
        taDiagnostico = new JTextArea(5, 30);
        taDiagnostico.setLineWrap(true);
        taDiagnostico.setWrapStyleWord(true);
        JScrollPane scrollDiagnostico = new JScrollPane(taDiagnostico);
        panel.add(scrollDiagnostico, gbc);

        // Medicamentos
        gbc.gridy++;
        panel.add(new JLabel("Medicamentos (separados por vírgula, ex: 'Remédio A 10mg, Remédio B 5ml'):"), gbc);
        gbc.gridy++;
        taMedicamentos = new JTextArea(4, 30);
        taMedicamentos.setLineWrap(true);
        taMedicamentos.setWrapStyleWord(true);
        JScrollPane scrollMedicamentos = new JScrollPane(taMedicamentos);
        panel.add(scrollMedicamentos, gbc);

        // Painel de Botões
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnRealizarAtendimento = new JButton("Realizar Atendimento");
        btnCancelar = new JButton("Cancelar");
        buttonPanel.add(btnRealizarAtendimento);
        buttonPanel.add(btnCancelar);
        panel.add(buttonPanel, gbc);

        add(panel, BorderLayout.CENTER);
    }

    private void loadConsultas() {
        // ... (código loadConsultas existente, sem alterações aqui) ...
        List<Consulta> consultas = facade.buscarTodasAsConsultas();
        if (consultas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há consultas agendadas para atendimento.", "Sem Consultas", JOptionPane.INFORMATION_MESSAGE);
            btnRealizarAtendimento.setEnabled(false);
            return;
        }
        for (Consulta c : consultas) {
            cbConsulta.addItem(c);
        }
        cbConsulta.setSelectedIndex(0);
    }

    private void addListeners() {
        // ... (código addListeners existente, sem alterações aqui) ...
        btnRealizarAtendimento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarAtendimento();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void realizarAtendimento() {
        Consulta consultaSelecionada = (Consulta) cbConsulta.getSelectedItem();
        String diagnosticoDescricao = taDiagnostico.getText().trim();
        String medicamentosTexto = taMedicamentos.getText().trim();

        if (consultaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma consulta.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (diagnosticoDescricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira o diagnóstico.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Medicamento> medicamentos = new ArrayList<>();
        if (!medicamentosTexto.isEmpty()) {
            String[] medsArray = medicamentosTexto.split(",");
            for (String medEntry : medsArray) {
                String trimmedMedEntry = medEntry.trim();
                if (!trimmedMedEntry.isEmpty()) {
                    String nome = trimmedMedEntry;
                    String dosagem = "";

                    int lastSpace = trimmedMedEntry.lastIndexOf(" ");
                    if (lastSpace != -1 && trimmedMedEntry.substring(lastSpace + 1).matches(".*\\d+.*")) {
                        nome = trimmedMedEntry.substring(0, lastSpace).trim();
                        dosagem = trimmedMedEntry.substring(lastSpace + 1).trim();
                    }

                    medicamentos.add(new Medicamento(nome, dosagem));
                }
            }
        }

        // CHAMA O MÉTODO DA FACHADA E CAPTURA A PRESCRICAO RETORNADA
        Prescricao prescricaoGerada = facade.realizarAtendimento(consultaSelecionada, diagnosticoDescricao, medicamentos);

        JOptionPane.showMessageDialog(this, "Atendimento para " + consultaSelecionada.getPet().getNome() + " registrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        // PERGUNTA AO USUÁRIO SE DESEJA VISUALIZAR A PRESCRIÇÃO
        int dialogResult = JOptionPane.showConfirmDialog(this,
                "Atendimento registrado. Deseja visualizar a prescrição?",
                "Visualizar Prescrição", JOptionPane.YES_NO_OPTION);

        if (dialogResult == JOptionPane.YES_OPTION) {
            // Abre a PrescricaoGUI passando a prescrição gerada
            SwingUtilities.invokeLater(() -> {
                new PrescricaoGUI(prescricaoGerada).setVisible(true);
            });
        }

        dispose(); // Fecha a janela RealizarAtendimentoGUI
    }
}
package br.com.veterinaria.gui;

import br.com.veterinaria.facade.ClinicaFacade;
import br.com.veterinaria.model.Consulta;
import br.com.veterinaria.model.Medicamento;
import br.com.veterinaria.model.Prescricao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RealizarAtendimentoGUI extends JFrame {
    private ClinicaFacade facade;

    private JComboBox<ConsultaComboBoxItem> cmbConsulta; // Changed to use ConsultaComboBoxItem directly
    private JTextArea txtDiagnostico;
    private JTextArea txtMedicamentos;
    private JButton btnFinalizarAtendimento;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public RealizarAtendimentoGUI(ClinicaFacade facade) {
        this.facade = facade;
        setTitle("Realizar Atendimento Veterinário");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        loadConsultas();
        addListeners();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Detalhes do Atendimento"));

        formPanel.add(new JLabel("Selecionar Consulta:"));
        cmbConsulta = new JComboBox<>(); // This JComboBox now holds ConsultaComboBoxItem objects
        formPanel.add(cmbConsulta);

        formPanel.add(new JLabel("Diagnóstico:"));
        txtDiagnostico = new JTextArea(5, 20);
        JScrollPane scrollDiagnostico = new JScrollPane(txtDiagnostico);
        formPanel.add(scrollDiagnostico);

        formPanel.add(new JLabel("Medicamentos (um por linha/separados por vírgula):"));
        txtMedicamentos = new JTextArea(5, 20);
        JScrollPane scrollMedicamentos = new JScrollPane(txtMedicamentos);
        formPanel.add(scrollMedicamentos);

        btnFinalizarAtendimento = new JButton("Finalizar Atendimento e Gerar Prescrição");
        formPanel.add(new JLabel(""));
        formPanel.add(btnFinalizarAtendimento);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void loadConsultas() {
        cmbConsulta.removeAllItems();
        List<Consulta> consultas = facade.buscarTodasAsConsultas();

        if (consultas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há consultas agendadas para atendimento.", "Aviso", JOptionPane.WARNING_MESSAGE);
            btnFinalizarAtendimento.setEnabled(false);
            return;
        }

        for (Consulta consulta : consultas) {
            cmbConsulta.addItem(new ConsultaComboBoxItem(consulta));
        }
    }

    private void addListeners() {
        btnFinalizarAtendimento.addActionListener(e -> finalizarAtendimento());
    }

    private void finalizarAtendimento() {
        ConsultaComboBoxItem selectedItem = (ConsultaComboBoxItem) cmbConsulta.getSelectedItem();

        if (selectedItem == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma consulta para finalizar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- THE CRITICAL FIX IS HERE ---
        // Get the actual Consulta object from the wrapper
        Consulta consultaSelecionada = selectedItem.getConsulta();

        String diagnosticoDescricao = txtDiagnostico.getText().trim();
        String medicamentosTexto = txtMedicamentos.getText().trim();

        if (diagnosticoDescricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O campo Diagnóstico é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Medicamento> medicamentos = new ArrayList<>();
        if (!medicamentosTexto.isEmpty()) {
            String[] medsArray = medicamentosTexto.split("[,\\n]");
            for (String medName : medsArray) {
                String trimmedMedName = medName.trim();
                if (!trimmedMedName.isEmpty()) {
                    medicamentos.add(new Medicamento(trimmedMedName, "Descrição Padrão", "1x ao dia"));
                }
            }
        }

        Prescricao prescricaoGerada = facade.realizarAtendimento(consultaSelecionada, diagnosticoDescricao, medicamentos);

        JOptionPane.showMessageDialog(this,
                "Atendimento finalizado com sucesso!\nDiagnóstico: " + prescricaoGerada.getDiagnostico().getDescricao() +
                        "\nMedicamentos Prescritos: " + prescricaoGerada.getMedicamentos().size(),
                "Atendimento Concluído", JOptionPane.INFORMATION_MESSAGE);

        txtDiagnostico.setText("");
        txtMedicamentos.setText("");
        // Consider refreshing the list of consultations if the attended one should disappear
        // loadConsultas();
    }

    private class ConsultaComboBoxItem {
        private Consulta consulta;

        public ConsultaComboBoxItem(Consulta consulta) {
            this.consulta = consulta;
        }

        public Consulta getConsulta() {
            return consulta; // This method is crucial for extracting the actual Consulta
        }

        @Override
        public String toString() {
            return "ID: " + consulta.getId() +
                    " | Pet: " + consulta.getPet().getNome() +
                    " (" + consulta.getCliente().getNome() + ")" +
                    " | Vet: " + consulta.getVeterinario().getNome() +
                    " | Data: " + dateFormat.format(consulta.getData());
        }
    }
}
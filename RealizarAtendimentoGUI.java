package br.com.veterinaria.gui;

import br.com.veterinaria.facade.ClinicaFacade;
import br.com.veterinaria.model.Consulta;
import br.com.veterinaria.model.Medicamento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RealizarAtendimentoGUI extends JFrame {
    private ClinicaFacade facade; // Referência à fachada para operações de negócio

    // Componentes da interface
    private JComboBox<Consulta> cbConsulta; // ComboBox para selecionar a consulta
    private JTextArea taDiagnostico;       // Área de texto para o diagnóstico
    private JTextArea taMedicamentos;      // Área de texto para os medicamentos (separados por vírgula)
    private JButton btnRealizarAtendimento; // Botão para finalizar o atendimento
    private JButton btnCancelar;            // Botão para cancelar e fechar a janela

    /**
     * Construtor da tela de Realizar Atendimento.
     * @param facade Instância da ClinicaFacade para interagir com a lógica de negócio.
     */
    public RealizarAtendimentoGUI(ClinicaFacade facade) {
        this.facade = facade; // Atribui a instância da fachada
        setTitle("Realizar Atendimento"); // Título da janela
        setSize(500, 450); // Define o tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Apenas fecha esta janela ao clicar em 'X'
        setLocationRelativeTo(null); // Centraliza a janela na tela

        initComponents();       // Inicializa e posiciona os componentes da GUI
        loadConsultas();        // Carrega as consultas disponíveis para o ComboBox
        addListeners();         // Adiciona os ouvintes de eventos aos botões
    }

    /**
     * Inicializa e configura todos os componentes visuais da tela.
     */
    private void initComponents() {
        // Painel principal usando GridBagLayout para flexibilidade no layout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Adiciona um padding interno
        GridBagConstraints gbc = new GridBagConstraints(); // Objeto para controlar as restrições do layout
        gbc.insets = new Insets(8, 8, 8, 8); // Define o espaçamento (margem) entre os componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz com que os componentes preencham o espaço horizontalmente

        // --- Seleção de Consulta ---
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
        panel.add(new JLabel("Selecionar Consulta:"), gbc);
        gbc.gridx = 1; // Coluna 1
        gbc.weightx = 1.0; // Dá peso a esta coluna para expandir
        cbConsulta = new JComboBox<>();
        panel.add(cbConsulta, gbc);

        // --- Campo de Diagnóstico ---
        gbc.gridx = 0;
        gbc.gridy++; // Próxima linha
        gbc.gridwidth = 2; // Ocupa duas colunas para o rótulo e a área de texto
        panel.add(new JLabel("Diagnóstico:"), gbc);
        gbc.gridy++; // Próxima linha para a área de texto
        taDiagnostico = new JTextArea(5, 30); // 5 linhas de altura, 30 colunas de largura preferencial
        taDiagnostico.setLineWrap(true);       // Ativa a quebra de linha automática
        taDiagnostico.setWrapStyleWord(true); // Quebra a linha por palavra
        JScrollPane scrollDiagnostico = new JScrollPane(taDiagnostico); // Adiciona barra de rolagem se o texto for grande
        panel.add(scrollDiagnostico, gbc);

        // --- Campo de Medicamentos ---
        gbc.gridy++; // Próxima linha
        panel.add(new JLabel("Medicamentos (separados por vírgula, ex: 'Remédio A 10mg, Remédio B 5ml'):"), gbc);
        gbc.gridy++; // Próxima linha para a área de texto
        taMedicamentos = new JTextArea(4, 30); // 4 linhas de altura
        taMedicamentos.setLineWrap(true);
        taMedicamentos.setWrapStyleWord(true);
        JScrollPane scrollMedicamentos = new JScrollPane(taMedicamentos); // Adiciona barra de rolagem
        panel.add(scrollMedicamentos, gbc);

        // --- Painel de Botões ---
        gbc.gridy++; // Próxima linha para os botões
        gbc.fill = GridBagConstraints.NONE; // Não preenche o espaço horizontalmente
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza os botões

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10)); // Painel interno para organizar botões
        btnRealizarAtendimento = new JButton("Realizar Atendimento");
        btnCancelar = new JButton("Cancelar");
        buttonPanel.add(btnRealizarAtendimento);
        buttonPanel.add(btnCancelar);
        panel.add(buttonPanel, gbc); // Adiciona o painel de botões ao painel principal

        add(panel, BorderLayout.CENTER); // Adiciona o painel principal à janela (JFrame)
    }

    /**
     * Carrega as consultas disponíveis do sistema para o JComboBox.
     * Atualmente, busca todas as consultas agendadas na fachada.
     */
    private void loadConsultas() {
        // Busca todas as consultas agendadas através da fachada
        List<Consulta> consultas = facade.buscarTodasAsConsultas();

        // Se não houver consultas, exibe uma mensagem e desabilita o botão de atendimento
        if (consultas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há consultas agendadas para atendimento.", "Sem Consultas", JOptionPane.INFORMATION_MESSAGE);
            btnRealizarAtendimento.setEnabled(false); // Desabilita o botão se não houver consultas
            return;
        }

        // Adiciona cada consulta ao ComboBox
        for (Consulta c : consultas) {
            cbConsulta.addItem(c);
        }
        cbConsulta.setSelectedIndex(0); // Seleciona a primeira consulta por padrão
    }

    /**
     * Adiciona os ouvintes de eventos (ActionListeners) aos botões.
     */
    private void addListeners() {
        // Listener para o botão "Realizar Atendimento"
        btnRealizarAtendimento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarAtendimento(); // Chama o método para processar o atendimento
            }
        });

        // Listener para o botão "Cancelar"
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela atual
            }
        });
    }

    /**
     * Processa a realização do atendimento, obtendo os dados da interface,
     * validando-os e chamando o método correspondente na ClinicaFacade.
     */
    private void realizarAtendimento() {
        // Obtém a consulta selecionada no ComboBox
        Consulta consultaSelecionada = (Consulta) cbConsulta.getSelectedItem();
        // Obtém o texto do diagnóstico e remove espaços em branco extras
        String diagnosticoDescricao = taDiagnostico.getText().trim();
        // Obtém o texto dos medicamentos e remove espaços em branco extras
        String medicamentosTexto = taMedicamentos.getText().trim();

        // --- Validação dos dados de entrada ---
        // Verifica se uma consulta foi selecionada
        if (consultaSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma consulta para realizar o atendimento.", "Erro de Seleção", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Verifica se o campo de diagnóstico não está vazio
        if (diagnosticoDescricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira o diagnóstico.", "Campos Vazios", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // --- Processamento dos medicamentos ---
        List<Medicamento> medicamentos = new ArrayList<>();
        if (!medicamentosTexto.isEmpty()) {
            // Divide o texto dos medicamentos por vírgula
            String[] medsArray = medicamentosTexto.split(",");
            for (String medEntry : medsArray) {
                String trimmedMedEntry = medEntry.trim(); // Remove espaços em branco da entrada de cada medicamento
                if (!trimmedMedEntry.isEmpty()) {
                    // Lógica simples para tentar separar nome e dosagem (pode ser aprimorada)
                    String nome = trimmedMedEntry;
                    String dosagem = "";

                    // Procura por uma possível dosagem no final da string (ex: "10mg", "5ml")
                    int lastSpace = trimmedMedEntry.lastIndexOf(" ");
                    if (lastSpace != -1 && trimmedMedEntry.substring(lastSpace + 1).matches(".*\\d+.*")) {
                        nome = trimmedMedEntry.substring(0, lastSpace).trim();
                        dosagem = trimmedMedEntry.substring(lastSpace + 1).trim();
                    }

                    medicamentos.add(new Medicamento(nome, dosagem));
                }
            }
        }

        // --- Chama a lógica de negócio através da fachada ---
        facade.realizarAtendimento(consultaSelecionada, diagnosticoDescricao, medicamentos);

        // Exibe mensagem de sucesso
        JOptionPane.showMessageDialog(this,
                "Atendimento para " + consultaSelecionada.getPet().getNome() + " registrado com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        dispose(); // Fecha a janela após o registro do atendimento
    }
}
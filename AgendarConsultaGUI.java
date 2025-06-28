package br.com.veterinaria.gui;

import br.com.veterinaria.facade.ClinicaFacade;
import br.com.veterinaria.adapter.DataAdapter; // Importar DataAdapter para formatar a data na mensagem de sucesso
import br.com.veterinaria.model.Cliente;
import br.com.veterinaria.model.Pet;
import br.com.veterinaria.model.Veterinario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class AgendarConsultaGUI extends JFrame {
    private ClinicaFacade facade; // Referência à fachada para operações de negócio

    // Componentes da interface
    private JComboBox<Pet> cbPet;
    private JComboBox<Cliente> cbCliente;
    private JComboBox<Veterinario> cbVeterinario;
    private JSpinner spDataHora; // Componente para seleção de data e hora
    private JButton btnAgendar;
    private JButton btnCancelar;

    /**
     * Construtor da tela de Agendamento de Consulta.
     * @param facade Instância da ClinicaFacade para interagir com a lógica de negócio.
     */
    public AgendarConsultaGUI(ClinicaFacade facade) {
        this.facade = facade; // Atribui a instância da fachada
        setTitle("Agendar Nova Consulta"); // Título da janela
        setSize(450, 350); // Define o tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Apenas fecha esta janela ao clicar em 'X'
        setLocationRelativeTo(null); // Centraliza a janela na tela

        initComponents();      // Inicializa e posiciona os componentes da GUI
        loadComboBoxData();    // Carrega os dados (Pets, Clientes, Veterinários) para os ComboBoxes
        addListeners();        // Adiciona os ouvintes de eventos aos botões
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

        // --- Rótulos e Campos de Entrada (JComboBoxes e JSpinner) ---
        // Campo para seleção do Pet
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
        panel.add(new JLabel("Pet:"), gbc);
        gbc.gridx = 1; // Coluna 1
        gbc.weightx = 1.0; // Dá peso a esta coluna para expandir
        cbPet = new JComboBox<>();
        panel.add(cbPet, gbc);

        // Campo para seleção do Cliente
        gbc.gridx = 0;
        gbc.gridy++; // Próxima linha
        panel.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        cbCliente = new JComboBox<>();
        panel.add(cbCliente, gbc);

        // Campo para seleção do Veterinário
        gbc.gridx = 0;
        gbc.gridy++; // Próxima linha
        panel.add(new JLabel("Veterinário:"), gbc);
        gbc.gridx = 1;
        cbVeterinario = new JComboBox<>();
        panel.add(cbVeterinario, gbc);

        // Campo para seleção de Data e Hora
        gbc.gridx = 0;
        gbc.gridy++; // Próxima linha
        panel.add(new JLabel("Data e Hora:"), gbc);
        gbc.gridx = 1;
        // Configura o JSpinner para data e hora, com formato específico
        SpinnerDateModel dateModel = new SpinnerDateModel();
        spDataHora = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spDataHora, "dd/MM/yyyy HH:mm");
        spDataHora.setEditor(dateEditor);
        spDataHora.setValue(new Date()); // Define a data e hora inicial como a atual
        panel.add(spDataHora, gbc);

        // --- Painel de Botões ---
        gbc.gridx = 0;
        gbc.gridy++; // Próxima linha para os botões
        gbc.gridwidth = 2; // Faz com que o painel de botões ocupe as duas colunas
        gbc.fill = GridBagConstraints.NONE; // Não preenche o espaço horizontalmente
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza os botões

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10)); // Painel interno para organizar botões
        btnAgendar = new JButton("Agendar Consulta");
        btnCancelar = new JButton("Cancelar");
        buttonPanel.add(btnAgendar);
        buttonPanel.add(btnCancelar);
        panel.add(buttonPanel, gbc); // Adiciona o painel de botões ao painel principal

        add(panel, BorderLayout.CENTER); // Adiciona o painel principal à janela (JFrame)
    }

    /**
     * Carrega os dados de Pets, Clientes e Veterinários para os JComboBoxes.
     * Os dados são obtidos através da ClinicaFacade.
     */
    private void loadComboBoxData() {
        // Carrega Pets
        List<Pet> pets = facade.getPetsCadastrados();
        for (Pet pet : pets) {
            cbPet.addItem(pet);
        }
        // Seleciona o primeiro item se a lista não estiver vazia
        if (!pets.isEmpty()) {
            cbPet.setSelectedIndex(0);
        }

        // Carrega Clientes
        List<Cliente> clientes = facade.getClientesCadastrados();
        for (Cliente cliente : clientes) {
            cbCliente.addItem(cliente);
        }
        if (!clientes.isEmpty()) {
            cbCliente.setSelectedIndex(0);
        }

        // Carrega Veterinários
        List<Veterinario> veterinarios = facade.getVeterinariosCadastrados();
        for (Veterinario vet : veterinarios) {
            cbVeterinario.addItem(vet);
        }
        if (!veterinarios.isEmpty()) {
            cbVeterinario.setSelectedIndex(0);
        }

        // Verifica se há dados suficientes para agendar uma consulta
        // Se alguma das listas estiver vazia, o agendamento não será possível.
        if (pets.isEmpty() || clientes.isEmpty() || veterinarios.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "É necessário cadastrar pets, clientes e veterinários antes de agendar uma consulta.",
                    "Dados Ausentes", JOptionPane.WARNING_MESSAGE);
            btnAgendar.setEnabled(false); // Desabilita o botão de agendar se faltarem dados
        }
    }

    /**
     * Adiciona os ouvintes de eventos (ActionListeners) aos botões.
     */
    private void addListeners() {
        // Listener para o botão "Agendar Consulta"
        btnAgendar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agendarConsulta(); // Chama o método para processar o agendamento
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
     * Realiza a lógica de agendamento da consulta, obtendo os dados da interface
     * e chamando o método correspondente na ClinicaFacade.
     */
    private void agendarConsulta() {
        // Obtém os objetos selecionados nos ComboBoxes
        Pet petSelecionado = (Pet) cbPet.getSelectedItem();
        Cliente clienteSelecionado = (Cliente) cbCliente.getSelectedItem();
        Veterinario veterinarioSelecionado = (Veterinario) cbVeterinario.getSelectedItem();
        Date dataSelecionada = (Date) spDataHora.getValue(); // Obtém a data e hora do JSpinner

        // --- Validação dos dados de entrada ---
        // Verifica se algum dos itens selecionados (ou a data) é nulo.
        // O método getSelectedItem() de um JComboBox pode retornar null se a lista estiver vazia.
        if (petSelecionado == null || clienteSelecionado == null || veterinarioSelecionado == null || dataSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos e selecione os itens.", "Erro de Agendamento", JOptionPane.ERROR_MESSAGE);
            return; // Sai do método se a validação falhar, impedindo o agendamento
        }

        // --- Chama a lógica de negócio através da fachada ---
        facade.agendarConsulta(petSelecionado, clienteSelecionado, veterinarioSelecionado, dataSelecionada);

        // Exibe mensagem de sucesso
        JOptionPane.showMessageDialog(this,
                "Consulta agendada com sucesso para " + petSelecionado.getNome() + " em " + DataAdapter.formatarData(dataSelecionada) + "!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        dispose(); // Fecha a janela após o agendamento bem-sucedido
    }
}
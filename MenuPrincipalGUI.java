package br.com.veterinaria.gui;

import br.com.veterinaria.facade.ClinicaFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalGUI extends JFrame {
    private ClinicaFacade facade;

    // Declaração dos botões como atributos da classe
    private JButton btnCadastrarCliente;
    private JButton btnGerenciarClientes;
    private JButton btnCadastrarPet;
    private JButton btnGerenciarPets;
    private JButton btnGerenciarVeterinarios; // Variável para o botão "Gerenciar Veterinários"
    private JButton btnAgendarConsulta;
    private JButton btnRealizarAtendimento;

    /**
     * Construtor da MenuPrincipalGUI.
     * @param facade A instância da ClinicaFacade para interagir com a lógica de negócio.
     */
    public MenuPrincipalGUI(ClinicaFacade facade) {
        this.facade = facade;
        setTitle("Sistema de Gestão de Clínica Veterinária");
        setSize(400, 450); // Ajustado o tamanho para acomodar mais botões
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao fechar esta janela
        setLocationRelativeTo(null); // Centraliza a janela na tela

        initComponents(); // Inicializa os componentes da interface
        addListeners();   // Adiciona os ouvintes de eventos aos botões
    }

    /**
     * Inicializa e organiza os componentes visuais da janela principal.
     */
    private void initComponents() {
        JPanel panel = new JPanel();
        // Layout de grade para organizar os botões verticalmente
        panel.setLayout(new GridLayout(7, 1, 10, 10)); // 7 linhas para 7 botões, com espaçamento
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margem interna

        // Inicialização de cada botão
        btnCadastrarCliente = new JButton("Cadastrar Cliente");
        btnGerenciarClientes = new JButton("Gerenciar Clientes");
        btnCadastrarPet = new JButton("Cadastrar Pet");
        btnGerenciarPets = new JButton("Gerenciar Pets");
        btnGerenciarVeterinarios = new JButton("Gerenciar Veterinários"); // Inicialização do botão
        btnAgendarConsulta = new JButton("Agendar Consulta");
        btnRealizarAtendimento = new JButton("Realizar Atendimento");

        // Adição dos botões ao painel
        panel.add(btnCadastrarCliente);
        panel.add(btnGerenciarClientes);
        panel.add(btnCadastrarPet);
        panel.add(btnGerenciarPets);
        panel.add(btnGerenciarVeterinarios); // Adição do botão ao painel
        panel.add(btnAgendarConsulta);
        panel.add(btnRealizarAtendimento);

        // Adiciona o painel principal à janela
        add(panel, BorderLayout.CENTER);
    }

    /**
     * Adiciona os ActionListeners a cada botão para definir suas ações.
     */
    private void addListeners() {
        btnCadastrarCliente.addActionListener(e -> {
            // Exemplo: new CadastroClienteGUI(facade).setVisible(true);
            JOptionPane.showMessageDialog(this, "Funcionalidade 'Cadastrar Cliente' em desenvolvimento.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        btnGerenciarClientes.addActionListener(e -> {
            // Exemplo: new GerenciarClientesGUI(facade).setVisible(true);
            JOptionPane.showMessageDialog(this, "Funcionalidade 'Gerenciar Clientes' em desenvolvimento.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        btnCadastrarPet.addActionListener(e -> {
            new CadastroPetGUI(facade).setVisible(true);
        });

        btnGerenciarPets.addActionListener(e -> {
            // Exemplo: new GerenciarPetsGUI(facade).setVisible(true);
            JOptionPane.showMessageDialog(this, "Funcionalidade 'Gerenciar Pets' em desenvolvimento.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        // Listener para o botão "Gerenciar Veterinários"
        btnGerenciarVeterinarios.addActionListener(e -> {
            GerenciarVeterinariosGUI gerenciarVeterinariosGUI = new GerenciarVeterinariosGUI(facade);
            gerenciarVeterinariosGUI.setVisible(true);
        });

        btnAgendarConsulta.addActionListener(e -> {
            // Exemplo: new AgendarConsultaGUI(facade).setVisible(true);
            JOptionPane.showMessageDialog(this, "Funcionalidade 'Agendar Consulta' em desenvolvimento.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        btnRealizarAtendimento.addActionListener(e -> {
            // Exemplo: new RealizarAtendimentoGUI(facade).setVisible(true);
            JOptionPane.showMessageDialog(this, "Funcionalidade 'Realizar Atendimento' em desenvolvimento.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    /**
     * Método principal para iniciar a aplicação.
     * @param args Argumentos da linha de comando (não utilizados neste caso).
     */
    public static void main(String[] args) {
        // Inicializa a fachada (que carrega os dados de exemplo)
        ClinicaFacade facade = new ClinicaFacade();

        // Garante que a GUI seja criada e atualizada na Thread de Despacho de Eventos (EDT)
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipalGUI(facade).setVisible(true);
        });
    }
}
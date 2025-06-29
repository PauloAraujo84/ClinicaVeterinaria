package br.com.veterinaria.gui;

import br.com.veterinaria.facade.ClinicaFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalGUI extends JFrame {
    private ClinicaFacade facade;

    // Declaração dos botões como atributos da classe
    // Os botões btnCadastrarCliente e btnCadastrarPet foram removidos daqui
    private JButton btnGerenciarClientes;
    private JButton btnGerenciarPets;
    private JButton btnGerenciarVeterinarios;
    private JButton btnAgendarConsulta;
    private JButton btnRealizarAtendimento;

    /**
     * Construtor da MenuPrincipalGUI.
     * @param facade A instância da ClinicaFacade para interagir com a lógica de negócio.
     */
    public MenuPrincipalGUI(ClinicaFacade facade) {
        this.facade = facade;
        setTitle("Sistema de Gestão de Clínica Veterinária");
        setSize(400, 400); // Tamanho ajustado, pois há menos botões
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        addListeners();
    }

    /**
     * Inicializa e organiza os componentes visuais da janela principal.
     */
    private void initComponents() {
        JPanel panel = new JPanel();
        // O GridLayout agora terá menos linhas, pois alguns botões foram removidos
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 linhas para os 5 botões restantes
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Inicialização dos botões restantes
        btnGerenciarClientes = new JButton("Gerenciar Clientes (Adicionar/Remover)"); // Nome do botão atualizado
        btnGerenciarPets = new JButton("Gerenciar Pets (Adicionar/Remover)");       // Nome do botão atualizado
        btnGerenciarVeterinarios = new JButton("Gerenciar Veterinários (Adicionar/Remover)");
        btnAgendarConsulta = new JButton("Agendar/Gerenciar Consultas");
        btnRealizarAtendimento = new JButton("Realizar Atendimento");

        // Adição dos botões ao painel
        panel.add(btnGerenciarClientes);
        panel.add(btnGerenciarPets);
        panel.add(btnGerenciarVeterinarios);
        panel.add(btnAgendarConsulta);
        panel.add(btnRealizarAtendimento);

        add(panel, BorderLayout.CENTER);
    }

    /**
     * Adiciona os ActionListeners a cada botão para definir suas ações.
     */
    private void addListeners() {
        // O listener para btnCadastrarCliente foi removido
        // O listener para btnCadastrarPet foi removido

        btnGerenciarClientes.addActionListener(e -> {
            new GerenciarClientesGUI(facade).setVisible(true);
        });

        btnGerenciarPets.addActionListener(e -> {
            new GerenciarPetsGUI(facade).setVisible(true);
        });

        btnGerenciarVeterinarios.addActionListener(e -> {
            new GerenciarVeterinariosGUI(facade).setVisible(true);
        });

        btnAgendarConsulta.addActionListener(e -> {
            new AgendarConsultaGUI(facade).setVisible(true);
        });

        btnRealizarAtendimento.addActionListener(e -> {
            new RealizarAtendimentoGUI(facade).setVisible(true);
        });
    }

    /**
     * Método principal para iniciar a aplicação.
     * @param args Argumentos da linha de comando (não utilizados neste caso).
     */
    public static void main(String[] args) {
        ClinicaFacade facade = new ClinicaFacade();
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipalGUI(facade).setVisible(true);
        });
    }
}
package br.com.veterinaria.gui;

import br.com.veterinaria.facade.ClinicaFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalGUI extends JFrame {
    private ClinicaFacade facade; // Referência à fachada para passar para outras telas

    private JButton btnCadastrarPet;
    private JButton btnAgendarConsulta;
    private JButton btnRealizarAtendimento; // Novo botão
    private JButton btnSair;

    /**
     * Construtor da tela de Menu Principal.
     * @param facade Instância da ClinicaFacade que será usada por todas as telas.
     */
    public MenuPrincipalGUI(ClinicaFacade facade) {
        this.facade = facade; // Atribui a instância da fachada
        setTitle("Sistema de Gerenciamento de Clínica Veterinária");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha a aplicação ao fechar esta janela
        setLocationRelativeTo(null); // Centraliza a janela

        initComponents();
        addListeners();
    }

    /**
     * Inicializa e configura os componentes visuais do menu.
     */
    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10)); // 4 linhas, 1 coluna, espaçamento de 10px
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding

        btnCadastrarPet = new JButton("Cadastrar Pet");
        btnAgendarConsulta = new JButton("Agendar Consulta");
        btnRealizarAtendimento = new JButton("Realizar Atendimento"); // Instancia o novo botão
        btnSair = new JButton("Sair");

        panel.add(btnCadastrarPet);
        panel.add(btnAgendarConsulta);
        panel.add(btnRealizarAtendimento); // Adiciona o novo botão ao painel
        panel.add(btnSair);

        add(panel, BorderLayout.CENTER);
    }

    /**
     * Adiciona os ouvintes de eventos aos botões do menu.
     */
    private void addListeners() {
        btnCadastrarPet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ao clicar, abre a tela de Cadastro de Pet
                CadastroPetGUI cadastroPetGUI = new CadastroPetGUI(facade); // Passa a fachada
                cadastroPetGUI.setVisible(true);
            }
        });

        btnAgendarConsulta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ao clicar, abre a tela de Agendamento de Consulta
                AgendarConsultaGUI agendarConsultaGUI = new AgendarConsultaGUI(facade); // Passa a fachada
                agendarConsultaGUI.setVisible(true);
            }
        });

        // Listener para o novo botão "Realizar Atendimento"
        btnRealizarAtendimento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ao clicar, abre a tela de Realizar Atendimento
                RealizarAtendimentoGUI realizarAtendimentoGUI = new RealizarAtendimentoGUI(facade); // Passa a fachada
                realizarAtendimentoGUI.setVisible(true);
            }
        });

        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ao clicar, fecha a aplicação
                System.exit(0);
            }
        });
    }
}
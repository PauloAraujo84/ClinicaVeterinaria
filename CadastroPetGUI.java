package br.com.veterinaria.gui;

import br.com.veterinaria.facade.ClinicaFacade;
import br.com.veterinaria.model.Pet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroPetGUI extends JFrame {
    private JTextField txtNome;
    private JTextField txtEspecie;
    private JTextField txtRaca;
    private JTextField txtIdade;
    private JButton btnSalvar;
    private JButton btnCancelar;

    private ClinicaFacade facade;

    public CadastroPetGUI(ClinicaFacade facade) {
        this.facade = facade;
        setTitle("Cadastro de Pet");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        addListeners();
    }

    private void initComponents() {
        // ... (código initComponents existente) ...
        JPanel panel = new JPanel(new GridBagLayout()); // Usando GridBagLayout para um layout flexível
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Espaçamento interno
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margem entre componentes

        // Rótulos e campos de texto
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Espécie:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Raça:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Idade:"), gbc);

        gbc.gridx = 1; // Coluna 1
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Preenche horizontalmente
        gbc.weightx = 1.0; // Pesa mais na distribuição horizontal de espaço
        txtNome = new JTextField(20);
        panel.add(txtNome, gbc);
        gbc.gridy++;
        txtEspecie = new JTextField(20);
        panel.add(txtEspecie, gbc);
        gbc.gridy++;
        txtRaca = new JTextField(20);
        panel.add(txtRaca, gbc);
        gbc.gridy++;
        txtIdade = new JTextField(20);
        panel.add(txtIdade, gbc);

        // Botões
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.fill = GridBagConstraints.NONE; // Não preenche
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Painel para os botões
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");
        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);
        panel.add(buttonPanel, gbc);

        add(panel, BorderLayout.CENTER);
    }

    private void addListeners() {
        // ... (código addListeners existente) ...
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarPet();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void salvarPet() {
        String nome = txtNome.getText();
        String especie = txtEspecie.getText();
        String raca = txtRaca.getText();
        int idade;

        try {
            idade = Integer.parseInt(txtIdade.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade deve ser um número válido.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nome.isEmpty() || especie.isEmpty() || raca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Campos Vazios", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Pet novoPet = new Pet(nome, especie, raca, idade);

        // --- CHAMA O NOVO MÉTODO CADASTRARPET DA FACHADA ---
        facade.cadastrarPet(novoPet);
        // ----------------------------------------------------

        JOptionPane.showMessageDialog(this, "Pet " + novoPet.getNome() + " salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        limparCampos();
    }

    private void limparCampos() {
        txtNome.setText("");
        txtEspecie.setText("");
        txtRaca.setText("");
        txtIdade.setText("");
    }

    // O método main de teste pode ser removido ou mantido, mas não é usado pela aplicação principal.
    /*
    public static void main(String[] args) {
        ClinicaFacade mockFacade = new ClinicaFacade();
        SwingUtilities.invokeLater(() -> {
            new CadastroPetGUI(mockFacade).setVisible(true);
        });
    }
    */
}
package br.com.veterinaria.gui;

import br.com.veterinaria.facade.ClinicaFacade;
import br.com.veterinaria.model.Cliente;
import br.com.veterinaria.model.Pet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CadastroPetGUI extends JFrame {
    private ClinicaFacade facade;

    private JTextField txtNome;
    private JTextField txtEspecie;
    private JTextField txtRaca;
    private JTextField txtIdade;
    private JComboBox<Cliente> cmbProprietario; // NOVO: ComboBox para selecionar o proprietário
    private JButton btnCadastrar;

    public CadastroPetGUI(ClinicaFacade facade) {
        this.facade = facade;
        setTitle("Cadastrar Novo Pet");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        initComponents();
        loadProprietarios(); // Carrega os clientes no ComboBox
        addListeners();
    }

    private void initComponents() {
        // Ajustamos o layout para acomodar o novo campo "Proprietário"
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10)); // Agora 6 linhas para 5 campos + 1 botão
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panel.add(txtNome);

        panel.add(new JLabel("Espécie:"));
        txtEspecie = new JTextField();
        panel.add(txtEspecie);

        panel.add(new JLabel("Raça:"));
        txtRaca = new JTextField();
        panel.add(txtRaca);

        panel.add(new JLabel("Idade:"));
        txtIdade = new JTextField();
        panel.add(txtIdade);

        // NOVO: Campo e ComboBox para Proprietário
        panel.add(new JLabel("Proprietário:"));
        cmbProprietario = new JComboBox<>();
        panel.add(cmbProprietario);

        btnCadastrar = new JButton("Cadastrar Pet");
        // O botão ocupa as duas colunas na última linha
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnCadastrar);
        panel.add(new JLabel("")); // Espaçador para o GridLayout
        panel.add(btnCadastrar); // Adiciona o botão

        add(panel, BorderLayout.CENTER);
    }

    /**
     * Carrega a lista de clientes cadastrados no ComboBox de proprietários.
     */
    private void loadProprietarios() {
        List<Cliente> clientes = facade.getClientesCadastrados();
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há clientes cadastrados. Por favor, cadastre um cliente primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            btnCadastrar.setEnabled(false); // Desabilita o botão de cadastro se não houver clientes
            return;
        }
        for (Cliente cliente : clientes) {
            cmbProprietario.addItem(cliente);
        }
    }

    private void addListeners() {
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarPet();
            }
        });
    }

    private void cadastrarPet() {
        String nome = txtNome.getText().trim();
        String especie = txtEspecie.getText().trim();
        String raca = txtRaca.getText().trim();
        String idadeStr = txtIdade.getText().trim();
        // OBTÉM O CLIENTE SELECIONADO NO COMBOBOX
        Cliente proprietario = (Cliente) cmbProprietario.getSelectedItem();

        // Validação de campos obrigatórios
        if (nome.isEmpty() || especie.isEmpty() || raca.isEmpty() || idadeStr.isEmpty() || proprietario == null) {
            JOptionPane.showMessageDialog(this, "Todos os campos e o proprietário são obrigatórios.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idade;
        try {
            idade = Integer.parseInt(idadeStr);
            if (idade <= 0) {
                throw new NumberFormatException(); // Idade deve ser positiva
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade deve ser um número inteiro positivo.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cria o objeto Pet, AGORA PASSANDO O PROPRIETÁRIO CORRETAMENTE
        Pet novoPet = new Pet(nome, especie, raca, idade, proprietario);

        facade.cadastrarPet(novoPet);
        JOptionPane.showMessageDialog(this, "Pet cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        // Limpa os campos após o cadastro bem-sucedido
        txtNome.setText("");
        txtEspecie.setText("");
        txtRaca.setText("");
        txtIdade.setText("");
        // Não é necessário resetar o cmbProprietario a menos que você queira que ele volte a "nenhum selecionado"
        // cmbProprietario.setSelectedIndex(0); // Pode ser usado se houver um item "Selecione"
    }
}
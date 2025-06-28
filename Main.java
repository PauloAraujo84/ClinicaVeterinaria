package br.com.veterinaria;

import br.com.veterinaria.facade.ClinicaFacade;
import br.com.veterinaria.gui.MenuPrincipalGUI;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // 1. Instancia a fachada da clínica.
        // É crucial que a fachada seja criada aqui e passada para as GUIs,
        // garantindo que todas as telas usem a mesma lógica de negócio centralizada.
        ClinicaFacade clinica = new ClinicaFacade();

        // 2. Lançamento da Interface Gráfica (GUI)
        // ESSENCIAL: A criação e exibição de componentes Swing DEVE ser feita
        // dentro da Event Dispatch Thread (EDT) para garantir estabilidade e responsividade.
        SwingUtilities.invokeLater(() -> {
            System.out.println("Iniciando interface gráfica do Sistema de Gestão Veterinária...");
            // Cria e exibe a janela principal do seu sistema (o menu)
            MenuPrincipalGUI menuPrincipal = new MenuPrincipalGUI(clinica); // Passa a fachada para o menu
            menuPrincipal.setVisible(true); // Torna a janela visível
        });

        // A partir deste ponto, o controle da aplicação é da interface gráfica.
        // O programa só será encerrado quando a janela principal for fechada.
    }
}
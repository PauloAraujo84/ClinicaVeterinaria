package br.com.veterinaria;

import br.com.veterinaria.facade.ClinicaFacade;
import br.com.veterinaria.gui.MenuPrincipalGUI; // Importa sua tela de menu principal
import br.com.veterinaria.model.*; // Mantém se quiser usar para dados de teste iniciais
import javax.swing.SwingUtilities; // Essencial para rodar a GUI na thread correta
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Instancia a fachada da clínica.
        // É crucial que a fachada seja criada aqui e passada para as GUIs,
        // garantindo que todas as telas usem a mesma lógica de negócio centralizada.
        ClinicaFacade clinica = new ClinicaFacade();

        // 2. Bloco de código para inicialização ou testes de dados (Opcional)
        // Este bloco serve para pré-carregar alguns dados ou testar a lógica de negócio
        // sem precisar interagir com a GUI primeiro. Em uma aplicação final,
        // a criação de dados seria feita pelas telas de cadastro.
        System.out.println("Iniciando lógica de console/inicialização de dados...");
        try {
            // Cria e cadastra um Pet, Cliente e Veterinário para que eles apareçam nos comboboxes
            // da GUI sem precisar cadastrar manualmente a cada execução.
            Pet petExemplo = new Pet("Fred", "Cachorro", "Poodle", 5);
            clinica.cadastrarPet(petExemplo); // Usa o método da fachada que adiciona à lista interna

            // Os clientes e veterinários são adicionados diretamente na ClinicaFacade no construtor dela
            // para este exemplo, então não precisamos cadastrá-los aqui novamente.

            // Exemplo de agendamento de uma consulta inicial
            // Pega o primeiro cliente e veterinário dos exemplos da fachada
            Cliente clienteExemplo = clinica.getClientesCadastrados().get(0);
            Veterinario veterinarioExemplo = clinica.getVeterinariosCadastrados().get(0);
            Date dataHoraExemplo = new Date(); // Data e hora atual para a consulta
            clinica.agendarConsulta(petExemplo, clienteExemplo, veterinarioExemplo, dataHoraExemplo);

            // Exemplo de realização de atendimento (opcional para testes)
            // Busca a consulta que acabou de ser agendada (assumindo que é a primeira na lista da fachada)
            Consulta consultaAgendadaExemplo = null;
            if (!clinica.buscarTodasAsConsultas().isEmpty()) {
                consultaAgendadaExemplo = clinica.buscarTodasAsConsultas().get(0);
            }

            if (consultaAgendadaExemplo != null) {
                List<Medicamento> medicamentosExemplo = new ArrayList<>();
                medicamentosExemplo.add(new Medicamento("Dipirona", "500mg"));
                medicamentosExemplo.add(new Medicamento("Anti-inflamatório", "1 comprimido"));
                clinica.realizarAtendimento(consultaAgendadaExemplo, "Infecção de ouvido leve", medicamentosExemplo);
            }

            System.out.println("Dados de inicialização processados com sucesso.");

        } catch (Exception e) {
            System.err.println("Erro durante a inicialização de dados: " + e.getMessage());
            e.printStackTrace();
        }
        // Fim do bloco de código de inicialização/testes
        // ----------------------------------------------------------------------

        // 3. Lançamento da Interface Gráfica (GUI)
        // ESSENCIAL: A criação e exibição de componentes Swing DEVE ser feita
        // dentro da Event Dispatch Thread (EDT) para garantir estabilidade e responsividade.
        SwingUtilities.invokeLater(() -> {
            System.out.println("Iniciando interface gráfica...");
            // Cria e exibe a janela principal do seu sistema (o menu)
            MenuPrincipalGUI menuPrincipal = new MenuPrincipalGUI(clinica); // Passa a fachada
            menuPrincipal.setVisible(true); // Torna a janela visível
        });

        // O `System.out.println("\nProcesso finalizado.");` original não é mais adequado aqui,
        // pois a aplicação continuará rodando na GUI até que o usuário feche a janela principal.
    }
}
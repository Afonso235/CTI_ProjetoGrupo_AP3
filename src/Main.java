import java.util.List;
import java.util.Scanner;

public class Main {
    private static GereUtilizadores gereUtilizadores;
    private static Scanner scanner;
    private static Utilizador utilizadorAutenticado;

    public static void main(String[] args) {
        gereUtilizadores = new GereUtilizadores();
        scanner = new Scanner(System.in);

        realizarOperacoes();
    }

    private static void mostrarDespedida() {
        if (utilizadorAutenticado != null) {
            System.out.println("Adeus " + utilizadorAutenticado.getLogin());
        } else {
            System.out.println("Adeus!");
        }
    }

    private static void exibirMenuUtilizador() {
        System.out.println("===== Menu Cenas =====");
        System.out.println("1. Alterar Dados");
        System.out.println("0. xau");
        System.out.print("Opção: ");
    }

    static int lerOpcao() {
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    private static void exibirMenuPrincipal() {
        System.out.println("===== Menu Principal =====");
        System.out.println("1. Criar conta");
        System.out.println("2. Fazer login");
        System.out.println("0. Sair");
        System.out.print("Opção: ");
    }

    public static void criarConta() {
        boolean sair = false;
        while (!sair) {
            System.out.println("===== Tipo Conta =====");
            System.out.println("1. Cliente");
            System.out.println("2. Mecanico");
            System.out.println("3. Gestor");
            System.out.print("Opção: ");
            int tipo = lerOpcao();

            System.out.println("\n===== Criar Conta =====");
            System.out.print("Login: ");
            String login = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();

            boolean ativo = true; // Default value for 'ativo' is true

            switch (tipo) {
                case 1, 2 -> ativo = false;
                case 3 -> {
                    System.out.println("Conta de gestor criada com sucesso!");
                    Utilizador gestor = new Utilizador(login, password, nome, true, email, TipoUtilizador.fromInt(tipo));
                    gereUtilizadores.getUtilizadores().add(gestor);
                    gereUtilizadores.salvarCredenciais();
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
            if (tipo != 3) {
                gereUtilizadores.criarConta(login, password, nome ,email, TipoUtilizador.fromInt(tipo));
                utilizadorAutenticado.setAtivo(false);
                System.out.println("Precisa de ter a sua conta ativada para poder usufruir da aplicação na totalidade!");
                mostrarDespedida();
                sair = true;
            } else {
                exibirMenuGestor();
            }
        }
    }


    public static void exibirMenuGestor() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n===== Menu do Gestor =====");
            System.out.println("1. Aprovar pedidos");
            System.out.println("2. Gerir utilizadores");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            int opcao = lerOpcao();

        switch (opcao) {
            case 1:
                List<Utilizador> utilizadoresPorAprovar = gereUtilizadores.getUtilizadoresPorAprovarLogin();
                aprovarUtilizador(utilizadoresPorAprovar);
                gereUtilizadores.definirAtivo("", true);
                gereUtilizadores.processarPedidos();
                break;
            case 2:
                // Lógica para gerir utilizadores
                break;
            case 0:
                sair = true;
                realizarOperacoes();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                exibirMenuGestor();
                break;
            }
        }
    }
    private static void aprovarUtilizador(List<Utilizador> utilizadoresPorAprovar) {
        if (utilizadoresPorAprovar.isEmpty()) {
            System.out.println("Não há utilizadores por aprovar login.");
        } else {
            System.out.println("Utilizadores por aprovar login:");
            for (int i = 0; i < utilizadoresPorAprovar.size(); i++) {
                Utilizador utilizador = utilizadoresPorAprovar.get(i);
                System.out.println((i+1) + ". " + utilizador.getLogin());
            }
            System.out.print("Selecione o número do utilizador que deseja aprovar: ");
            int numeroSelecionado = lerOpcao();

            if (numeroSelecionado >= 1 && numeroSelecionado <= utilizadoresPorAprovar.size()) {
                Utilizador utilizadorSelecionado = utilizadoresPorAprovar.get(numeroSelecionado - 1);
                utilizadorSelecionado.setAtivo(true);
                System.out.println("Utilizador " + utilizadorSelecionado.getLogin() + " aprovado com sucesso.");
            } else {
                System.out.println("Número inválido. Tente novamente.");
            }
        }
    }

    private static void fazerLogin() {
        System.out.println("===== Fazer Login =====");
        System.out.print("Login: ");
        String loginLogin = scanner.nextLine();
        System.out.print("Password: ");
        String passwordLogin = scanner.nextLine();

        Utilizador utilizador = gereUtilizadores.login(loginLogin, passwordLogin);
        if (utilizador != null) {
            System.out.println("Login efetuado com sucesso!");
            System.out.println("Bem-vindo " + utilizador.getLogin());
            System.out.println("Tipo: " + utilizador.getTipo());

            switch (utilizador.getTipo()) {
                case GESTOR -> exibirMenuGestor();
                case CLIENTE, MECANICO -> processarConta(utilizador);
                default -> System.out.println("Tipo de utilizador desconhecido.");
            }
        } else {
            System.out.println("Login falhou. Verifique o login e a password.");
        }
    }
    private static void processarConta(Utilizador utilizador) {
        if (utilizador.isAtivo()) {
            System.out.println("A sua conta já está ativada!");
            exibirMenuUtilizador();
        } else {
            System.out.println("Precisa de ter a sua conta ativada para poder usufruir da aplicação na totalidade!");
        }
    }

    private static void realizarOperacoes() {
        boolean sair = false;
        boolean xau = false;

        while (gereUtilizadores.getUtilizadores() == null || gereUtilizadores.getUtilizadores().isEmpty()) {
            System.out.println("Nenhum utilizador encontrado, por favor criar conta!");
            criarConta();
        }
        while (!sair) {
            if(utilizadorAutenticado != null){
                while(!xau){
                    exibirMenuUtilizador();
                    int opcao = lerOpcao();
                    switch (opcao) {
                        case 1 -> {
                            System.out.println("Insira a nova Password: ");
                            String newPassword = scanner.nextLine();
                            System.out.println("Insira o novo Nome: ");
                            String newNome = scanner.nextLine();
                            System.out.println("Insira o novo Email");
                            String newEmail = scanner.nextLine();
                            gereUtilizadores.alterarInfos(utilizadorAutenticado.getLogin(), newPassword, newNome, newEmail);
                        }
                        case 0 -> {
                            mostrarDespedida();
                            xau = true;
                        }
                        default -> System.out.println("Opção inválida. Tente novamente.");
                    }
                }
            }
            exibirMenuPrincipal();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1 -> criarConta();
                case 2 -> fazerLogin(); // Armazena o utilizador autenticado na variável global
                case 0 -> {
                    mostrarDespedida();
                    sair = true;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}


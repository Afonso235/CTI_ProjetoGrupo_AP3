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

        switch (tipo) {
            case 1:
                gereUtilizadores.criarConta(login, password, nome, false, email, TipoUtilizador.CLIENTE);
                System.out.println("Conta de cliente criada com sucesso!");
                break;
            case 2:
                gereUtilizadores.criarConta(login, password, nome, false, email, TipoUtilizador.MECANICO);
                System.out.println("Conta de mecanico criada com sucesso!");
                break;
            case 3:
                gereUtilizadores.criarConta(login, password, nome, false, email, TipoUtilizador.GESTOR);
                System.out.println("Conta de gestor criada com sucesso!");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }

    }

    private static Utilizador fazerLogin() {
        System.out.println("===== Fazer Login =====");
        System.out.print("Login: ");
        String loginLogin = scanner.nextLine();
        System.out.print("Password: ");
        String passwordLogin = scanner.nextLine();

        Utilizador utilizador = gereUtilizadores.login(loginLogin, passwordLogin);
        if (utilizador != null) {
            System.out.println("Login efetuado com sucesso!");
            System.out.println("Bem Vindo " + utilizador.getLogin());
            System.out.println("Tipo: " + utilizador.getTipo());
        } else {
            System.out.println("Login falhou. Verifique o login e a password.");
        }
        return utilizador;
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
                    switch (opcao){
                        case 1:
                            System.out.println("Insira a nova Password: ");
                            String newPassword = scanner.nextLine();
                            System.out.println("Insira o novo Nome: ");
                            String newNome = scanner.nextLine();
                            System.out.println("Insira o novo Email");
                            String newEmail = scanner.nextLine();

                            gereUtilizadores.alterarInfos(utilizadorAutenticado.getLogin(), newPassword, newNome, newEmail);
                            break;
                        case 0:
                            mostrarDespedida();
                            xau = true;
                            break;
                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                    }
                }
            }
            exibirMenuPrincipal();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    utilizadorAutenticado = fazerLogin(); // Armazena o utilizador autenticado na variável global
                    break;
                case 0:
                    mostrarDespedida();
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}


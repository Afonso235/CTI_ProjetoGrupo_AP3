import java.util.List;
import java.util.Scanner;

public class Main {
    private static GereUtilizadores gereUtilizadores;
    private static GereVeiculos gereVeiculos;
    private static GereMecanico gereMecanicos;
    private static Mecanico mecanicoAtual;
    private static Scanner scanner;
    private static Utilizador utilizadorAutenticado;
    private static GereAplicacao gereAplicacao;
    private static  SistemaInfo sistemaInfo;



    public static void main(String[] args) {
        gereUtilizadores = new GereUtilizadores();
        gereMecanicos = new GereMecanico();
        gereAplicacao = new GereAplicacao();
        sistemaInfo = new SistemaInfo();
        scanner = new Scanner(System.in);
        gereVeiculos = new GereVeiculos();
        sistemaInfo.incrementarNumeroExecucoes();
        gereAplicacao.carregarDados();

        gereUtilizadores.carregarCredenciais();
        gereAplicacao.carregarDados();
        realizarOperacoes();
        sistemaInfo.guardarInfo();
    }
    private static void mostrarDespedida() {
        gereAplicacao.guardarDados();
        if (utilizadorAutenticado != null) {
            sistemaInfo.setUltimoUtilizadorAcesso("Último Utilizador");
            System.out.println("Adeus " + utilizadorAutenticado.getLogin());
        } else {
            sistemaInfo.setUltimoUtilizadorAcesso("Último Utilizador");
            System.out.println("Adeus!");
        }
    }
    private static void exibirMenuCliente() {
        boolean sair = false;

        if (utilizadorAutenticado == null) {
            System.out.println("Nenhum utilizador autenticado.");
            return;
        }

        while (!sair) {
            System.out.println("===== Menu Cliente =====");
            System.out.println("1. Pedir Serviço");
            System.out.println("2. Consultar Serviços");
            System.out.println("3. Alterar Dados");
            System.out.println("0. Sair");
            System.out.print("Opção: ");

            GereServico gereServico = new GereServico();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1 -> {
                    gereServico.solicitarServico();
                    System.out.println();
                }
                case 2 -> {
                    //consultar só serviços de cliente
                    gereServico.fazerLoginCliente(utilizadorAutenticado.getLogin());
                    gereServico.consultarServicosCliente();
                    System.out.println();
                }
                case 3 -> {
                    alterarDados();
                }
                case 0 -> {
                    sair = true;
                    utilizadorAutenticado = null;
                    realizarOperacoes();
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
    private static int lerOpcao() {
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }
    private static void exibirMenuPrincipal() {
        System.out.println("===== Menu Principal =====");
        System.out.println("1. Criar conta");
        System.out.println("2. Fazer login");
        System.out.println("3. Ordenar todos os utilizadores alfabeticamente");
        System.out.println("4. Ordenar todos os veiculos por matricula");
        System.out.println("5. Listar todos os utilizadores");
        System.out.println("6. Listar todos os utilizadores por tipo");
        System.out.println("7. Listar todos os veiculos");
        System.out.println("8. Listar todos os mecanicos associados a um serviço");
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

            boolean ativo;

            switch (tipo) {
                case 1 -> {
                    ativo = false;
                    SistemaInfo.carregarInfo();
                    gereUtilizadores.criarConta(login, password, nome, email, TipoUtilizador.CLIENTE);
                }
                case 2 -> {
                    ativo = false;
                    SistemaInfo.carregarInfo();
                    //gereMecanicos.loginMecanico(login, password, TipoUtilizador.MECANICO);
                    iniciarMecanico();
                    gereUtilizadores.criarConta(login, password, nome, email, TipoUtilizador.MECANICO);
                }
                case 3 -> {
                    Utilizador gestor = new Utilizador(login, password, nome, true, email, TipoUtilizador.GESTOR);
                    gereUtilizadores.getUtilizadores().add(gestor);
                    gereUtilizadores.salvarCredenciais();
                    System.out.println("Conta de gestor criada com sucesso!");
                }
                default -> {
                    System.out.println("Opção inválida. Tente novamente.");
                    continue;
                }
            }

            if (tipo == 3) {
                exibirMenuGestor();
            } else {
                utilizadorAutenticado = new Utilizador(login, password, nome, false, email, TipoUtilizador.fromInt(tipo));
                System.out.println("Precisa de ter a sua conta ativada para poder usufruir da aplicação na totalidade!");
                mostrarDespedida();
            }
            sair = true;
        }
    }
    public static void exibirMenuGestor() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n===== Menu do Gestor =====");
            System.out.println("1. Aprovar pedidos");
            System.out.println("2. Aprovar servicos");
            System.out.println("3. Consultar todos os serviços");
            System.out.println("4. Gerir Log");
            System.out.println("5. Definições de Veiculos");
            System.out.println("6. Alterar Dados");
            System.out.println("0. Logout");
            System.out.print("Opção: ");

            int opcao = lerOpcao();
            GereServico gereServico = new GereServico();

            switch (opcao) {
                case 1 -> {
                    List<Utilizador> utilizadoresPorAprovar = gereUtilizadores.getUtilizadoresPorAprovarLogin();
                    aprovarUtilizador(utilizadoresPorAprovar);
                    gereUtilizadores.processarPedidos();
                }
                case 2 ->{
                    gereServico.aceitarServicos();
                }
                case 3 -> {
                    gereServico.consultarTodosServicos();
                    System.out.println();
                }
                case 4 -> {
                    gereAplicacao.consultarLog();
                    System.out.println("Pressione a tecla enter para voltar...");
                    new Scanner(System.in).nextLine();
                }
                case 5 -> {
                    menuVeiculo();
                }
                case 6 ->{
                    alterarDados();
                }
                case 0 -> {
                    sair = true;
                    utilizadorAutenticado = null;
                    realizarOperacoes();
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
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
                System.out.println((i + 1) + ". " + utilizador.getLogin());
            }
            System.out.print("Selecione o número do utilizador que deseja aprovar: ");
            int numeroSelecionado = lerOpcao();

            if (numeroSelecionado >= 1 && numeroSelecionado <= utilizadoresPorAprovar.size()) {
                Utilizador utilizadorSelecionado = utilizadoresPorAprovar.get(numeroSelecionado - 1);
                String loginUtilizadorSelecionado = utilizadorSelecionado.getLogin();
                gereUtilizadores.definirAtivo(loginUtilizadorSelecionado, true);
                System.out.println("Utilizador " + utilizadorSelecionado.getLogin() + " aprovado com sucesso.");
                gereAplicacao.registarAcao("Gestor", "Aprovou login de utilizador");
                SistemaInfo.carregarInfo();
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
                case CLIENTE -> {
                    if(processarConta(utilizador)){
                        exibirMenuCliente();
                    }
                }
                case MECANICO ->  {
                    if(processarConta(utilizador)){
                        exibirMenuMecanico();
                    }
                }
                default -> System.out.println("Tipo de utilizador desconhecido.");
            }
            utilizadorAutenticado = utilizador;
        } else {
            System.out.println("Login falhou. Verifique o login e a password.");
            exibirMenuPrincipal();
        }
    }
    private static Boolean processarConta(Utilizador utilizador) {
        if (utilizador.isAtivo()) {
            System.out.println("A sua conta já está ativada!");
            return true;
        } else {
            System.out.println("Precisa de ter a sua conta ativada para poder usufruir da aplicação na totalidade!");
            return false;
        }
    }
    private static void realizarOperacoes() {
        boolean sair = false;
        boolean xau = false;

        while (gereUtilizadores.getUtilizadores() == null || gereUtilizadores.getUtilizadores().isEmpty()) {
            System.out.println("Nenhum utilizador encontrado, por favor criar conta!");
            criarConta();
        }
        exibirMenuPrincipal();
        int opcao = lerOpcao();

        switch (opcao) {
            case 1 -> criarConta();
            case 2 -> {
                fazerLogin();
                if (utilizadorAutenticado != null && utilizadorAutenticado.getTipo() == TipoUtilizador.CLIENTE) {
                    exibirMenuCliente();
                }
            }
            case 3 -> {
                gereUtilizadores.ordenarUtilizadoresPorNome();
                System.out.println("Pressione a tecla enter para voltar...");
                new Scanner(System.in).nextLine();
                realizarOperacoes();
            }
            case 4 -> {
                System.out.println("4. Ordenar todos os veiculos por matricula");
                System.out.println("Pressione a tecla enter para voltar...");
                new Scanner(System.in).nextLine();
                realizarOperacoes();
            }
            case 5 -> {
                gereUtilizadores.listarTodosUtilizadores();
                System.out.println("Pressione a tecla enter para voltar...");
                new Scanner(System.in).nextLine();
                realizarOperacoes();
            }
            case 6 -> {
                System.out.println("Utilizador tipo Cliente:");
                gereUtilizadores.listarUtilizadoresPorTipo(TipoUtilizador.CLIENTE);
                System.out.println("Utilizador tipo Gestor:");
                gereUtilizadores.listarUtilizadoresPorTipo(TipoUtilizador.GESTOR);
                System.out.println("Utilizador tipo Mecanico:");
                gereUtilizadores.listarUtilizadoresPorTipo(TipoUtilizador.MECANICO);
                System.out.println("Pressione a tecla enter para voltar...");
                new Scanner(System.in).nextLine();
                realizarOperacoes();
            }
            case 7 -> {
                List<Veiculo> todosVeiculos = gereVeiculos.listarVeiculos("credenciais_veiculo.txt");
                for (Veiculo veiculo : todosVeiculos) {
                    System.out.println(veiculo);
                }
                System.out.println("Pressione a tecla enter para voltar...");
                new Scanner(System.in).nextLine();
                realizarOperacoes();
            }
            case 8 -> {
                System.out.println("8. Listar todos os mecanicos associados a um serviço");
                System.out.println("Pressione a tecla enter para voltar...");
                new Scanner(System.in).nextLine();
                realizarOperacoes();
            }
            case 0 -> {
                mostrarDespedida();
                sair = true;
            }
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    }


    //  GereVeículos
    private static void menuVeiculo() {
        gereVeiculos = new GereVeiculos();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("=== Menu ===");
            System.out.println("1. Inserir veículo");
            System.out.println("2. Remover veículo");
            System.out.println("3. Listar veículos");
            System.out.println("4. Listar veículos por cliente");
            System.out.println("5. Listar veículos com serviço concluído");
            System.out.println("6. Listar veículos com serviço em andamento");
            System.out.println("7. Listar veículos por mecânico");
            System.out.println("8. Pesquisar veículo por matrícula");
            System.out.println("9. Pesquisar veículos por peça");
            System.out.println("10. Pesquisar veículos após um determinado ano");
            System.out.println("11. Pesquisar veículos com tempo despendido superior a um limite");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    gereVeiculos.inserirVeiculo(scanner);
                    break;
                case 2:
                    gereVeiculos.removerVeiculo(scanner);
                    break;
                case 3:
                    List<Veiculo> todosVeiculos = gereVeiculos.listarVeiculos("credenciais_veiculo.txt");
                    for (Veiculo veiculo : todosVeiculos) {
                        System.out.println(veiculo);
                    }
                    System.out.println("Pressione a tecla enter para voltar...");
                    new Scanner(System.in).nextLine();
                    menuVeiculo();
                    break;
                case 4:
                    gereVeiculos.listarVeiculosPorCliente(scanner);
                    break;
                case 5:
                    gereVeiculos.listarVeiculosComServicoConcluido();
                    break;
                case 6:
                    gereVeiculos.listarVeiculosComServicoEmAndamento();
                    break;
                case 7:
                    gereVeiculos.listarVeiculosPorMecanico(scanner);
                    break;
                case 8:
                    gereVeiculos.pesquisarVeiculoPorMatricula(scanner);
                    break;
                case 9:
                    gereVeiculos.pesquisarVeiculosPorPeca(scanner);
                    break;
                case 10:
                    gereVeiculos.pesquisarVeiculosAposAno(scanner);
                    break;
                case 11:
                    gereVeiculos.pesquisarVeiculosComTempoDespendidoSuperior(scanner);
                    break;
                case 0:
                    exibirMenuGestor();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (choice != 0);

        scanner.close();
    }
    // GereMecanicos
    public static void iniciarMecanico() {
        boolean sair = false;
        while (!sair) {
            exibirMenuMecanico();
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (opcao) {
                case 1:
                    criarMecanico();
                    break;
                case 2:
                    realizarLoginMecanico();
                    break;
                case 3:
                    if (mecanicoAtual != null) {
                        consultarServicosMecanicoAtual();
                    } else {
                        System.out.println("Faça o login como mecânico primeiro.");
                    }
                    break;
                case 4:
                    if (mecanicoAtual != null) {
                        listarServicosRealizadosMecanicoAtual();
                    } else {
                        System.out.println("Faça o login como mecânico primeiro.");
                    }
                    break;
                case 5:
                    if (mecanicoAtual != null) {
                        pesquisarServicosRealizadosMecanicoAtual();
                    } else {
                        System.out.println("Faça o login como mecânico primeiro.");
                    }
                    break;
                case 6:
                    if (mecanicoAtual != null) {
                        logoutMecanico();
                    } else {
                        System.out.println("Faça o login como mecânico primeiro.");
                    }
                    break;
                case 7:
                    sair = true;
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
    private static void exibirMenuMecanico() {
        System.out.println("------ MENU PRINCIPAL ------");
        System.out.println("1. Criar Mecânico");
        System.out.println("2. Alterar Dados");
        System.out.println("3. Realizar Login como Mecânico");
        System.out.println("4. Consultar Serviços (Mecânico Atual)");
        System.out.println("5. Listar Serviços Realizados (Mecânico Atual)");
        System.out.println("6. Pesquisar Serviços Realizados (Mecânico Atual)");
        System.out.println("7. Logout (Mecânico Atual)");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }
    private static void criarMecanico() {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Ativo (true/false): ");
        boolean ativo = scanner.nextBoolean();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        scanner.nextLine(); // Consume the newline character

        Mecanico mecanico = new Mecanico(login, password, nome, ativo, email, TipoUtilizador.MECANICO);
        gereMecanicos.adicionarMecanico(mecanico);
        System.out.println("Mecânico criado com sucesso!");
    }
    public static void alterarDados() {
        System.out.println("Insira a nova Password: ");
        String newPassword = scanner.nextLine();
        System.out.println("Insira o novo Nome: ");
        String newNome = scanner.nextLine();
        System.out.println("Insira o novo Email");
        String newEmail = scanner.nextLine();
        gereUtilizadores.alterarInfos(utilizadorAutenticado.getLogin(), newPassword, newNome, newEmail);
    }
    private static void realizarLoginMecanico() {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Mecanico mecanico = gereMecanicos.loginMecanico(login, password, TipoUtilizador.MECANICO);
        if (mecanico != null) {
            mecanicoAtual = mecanico;
            System.out.println("Login realizado com sucesso como " + mecanico.getNome());
        } else {
            System.out.println("Login inválido. Tente novamente.");
        }
    }
    private static void consultarServicosMecanicoAtual() {
        mecanicoAtual.consultarServicos();
    }
    private static void listarServicosRealizadosMecanicoAtual() {
        mecanicoAtual.listarServicosRealizados();
    }
    private static void pesquisarServicosRealizadosMecanicoAtual() {
        System.out.print("Termo de Pesquisa: ");
        String termoPesquisa = scanner.nextLine();

        mecanicoAtual.pesquisarServicosRealizados(termoPesquisa);
    }
    private static void logoutMecanico() {
        mecanicoAtual = null;
        System.out.println("Logout realizado com sucesso.");
    }
}





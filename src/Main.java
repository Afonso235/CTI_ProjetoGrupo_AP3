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
            System.out.println("3. Pesquisar Serviços (Código Único)");
            System.out.println("4. Alterar Dados");
            System.out.println("0. Sair");
            System.out.print("Opção: ");

            GereServico gereServico = new GereServico();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1 -> {
                    gereServico.solicitarServico(utilizadorAutenticado);
                    System.out.println();
                }
                case 2 -> {
                    gereServico.fazerLoginCliente(utilizadorAutenticado.getLogin());
                    gereServico.consultarServicosCliente();
                    System.out.println();
                }
                case 3 -> {
                    System.out.print("Digite o código único do serviço: ");
                    String codigo = scanner.nextLine();
                    Servico servicoEncontrado = gereServico.pesquisarServicoPorCodigo(codigo);
                    if (servicoEncontrado != null) {
                        gereAplicacao.registarAcao("Cliente", "Consulta os seus serviços através de código único");
                        System.out.println( ". Responsável: " + servicoEncontrado.getMecanicoResponsavel());
                        System.out.println("   Data: " + servicoEncontrado.getData());
                        System.out.println("   Descrição: " + servicoEncontrado.getDescricao());
                        System.out.println("   Codigo: " + servicoEncontrado.getCodUnico());
                        System.out.println("--------------------------------");                    } else {
                        System.out.println("Nenhum serviço encontrado com o código fornecido.");
                    }
                }
                case 4 -> {
                    alterarDados();
                }
                case 0 -> {
                    sair = true;
                    gereServico.fazerLogoutCliente();
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

            // Check if name or email already exist
            boolean duplicateName = gereUtilizadores.verificarNomeUtilizador(login);
            boolean duplicateEmail = gereUtilizadores.verificarEmailUtilizador(email);

            if (duplicateName || duplicateEmail) {
                System.out.println("Nome de utilizador ou email já existe. Por favor, insira novamente.");
                continue;
            }

            switch (tipo) {
                case 1 -> {
                    ativo = false;
                    SistemaInfo.carregarInfo();
                    gereUtilizadores.criarConta(login, password, nome, email, TipoUtilizador.CLIENTE);
                }
                case 2 -> {
                    ativo = false;
                    SistemaInfo.carregarInfo();
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
            System.out.println("7. Listagens e pesquisas");
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
                case 5 -> menuVeiculo();
                case 6 -> alterarDados();

                case 7 -> menuListas();
                case 0 -> {
                    sair = true;
                    realizarOperacoes();
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
    private static void menuListas() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n===== Menu de Listas e Pesquisas =====");
            System.out.println("1. Ordenar todos os utilizadores alfabeticamente");
            System.out.println("2. Ordenar todos os veiculos por matricula");
            System.out.println("3. Listar todos os utilizadores");
            System.out.println("4. Listar todos os utilizadores por tipo");
            System.out.println("5. Listar todos os veiculos");
            System.out.println("6. Listar todos os mecanicos associados a um serviço");
            System.out.println("7. Pesquisar utilizadores por nome de login");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");

            int opcao = lerOpcao();
            GereServico gereServico = new GereServico();

            switch (opcao) {
                case 1 -> {
                    gereUtilizadores.ordenarUtilizadoresPorNome();
                    System.out.println("Pressione a tecla enter para voltar...");
                    new Scanner(System.in).nextLine();
                    menuListas();
                }
                case 2 -> {
                    System.out.println("2. Ordenar todos os veiculos por matricula");
                    gereVeiculos.listarVeiculosOrdenadosPorMatricula("credenciais_veiculo.txt");
                    System.out.println("Pressione a tecla enter para voltar...");
                    new Scanner(System.in).nextLine();
                    menuListas();
                }
                case 3 -> {
                    gereUtilizadores.listarTodosUtilizadores();
                    System.out.println("Pressione a tecla enter para voltar...");
                    new Scanner(System.in).nextLine();
                    menuListas();
                }
                case 4 -> {
                    System.out.println("Utilizador tipo Cliente:");
                    gereUtilizadores.listarUtilizadoresPorTipo(TipoUtilizador.CLIENTE);
                    System.out.println("Utilizador tipo Gestor:");
                    gereUtilizadores.listarUtilizadoresPorTipo(TipoUtilizador.GESTOR);
                    System.out.println("Utilizador tipo Mecanico:");
                    gereUtilizadores.listarUtilizadoresPorTipo(TipoUtilizador.MECANICO);
                    System.out.println("Pressione a tecla enter para voltar...");
                    new Scanner(System.in).nextLine();
                    menuListas();
                }
                case 5 -> {
                    gereVeiculos.listarVeiculos("credenciais_veiculo.txt");
                    System.out.println("Pressione a tecla enter para voltar...");
                    new Scanner(System.in).nextLine();
                    menuListas();
                }
                case 6 -> {
                    System.out.println("6. Listar todos os mecanicos associados a um serviço");
                    System.out.println("Pressione a tecla enter para voltar...");
                    new Scanner(System.in).nextLine();
                    menuListas();
                }
                case 7 -> {
                    System.out.print("Digite o login do utilizador que deseja pesquisar: ");
                    String nomePesquisado = scanner.nextLine();

                    List<Utilizador> resultados = gereUtilizadores.pesquisaLogin(nomePesquisado);

                    if (resultados.isEmpty()) {
                        System.out.println("Nenhum utilizador encontrado com o nome especificado.");
                    } else {
                        System.out.println("Utilizadores encontrados:");
                        for (Utilizador utilizador : resultados) {
                            System.out.println("Nome: " + utilizador.getNome());
                            System.out.println("Login: " + utilizador.getLogin());
                            System.out.println("Email: " + utilizador.getEmail());
                        }
                    }
                }
                case 0 -> exibirMenuGestor();
                default -> System.out.println("opção inválida, tente novamente!");
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
                        utilizadorAutenticado = utilizador;
                        exibirMenuCliente();
                    }
                }
                case MECANICO ->  {
                    if(processarConta(utilizador)){
                        utilizadorAutenticado = utilizador;
                        gereMecanicos.adicionarMecanico(mecanicoAtual);
                        iniciarMecanico();
                    }
                }
                default -> System.out.println("Tipo de utilizador desconhecido.");
            }
            utilizadorAutenticado = utilizador;
        } else {
            System.out.println("Login falhou. Verifique o login e a password.");
            realizarOperacoes();
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
            case 0 -> {
                mostrarDespedida();
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
            System.out.println("4. Listar veículos por mecânico");
            System.out.println("5. Pesquisar veículo por matrícula");
            System.out.println("6. Pesquisar veículos após um determinado ano");
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
                    gereVeiculos.listarVeiculos("credenciais_veiculo.txt");
                    System.out.println("Pressione a tecla enter para voltar...");
                    new Scanner(System.in).nextLine();
                    menuVeiculo();
                    break;
                case 4:
                    gereVeiculos.listarVeiculosPorMecanico(mecanicoAtual);
                    menuVeiculo();
                    break;
                case 5:
                    gereVeiculos.pesquisarVeiculoPorMatricula(scanner, "credenciais_veiculo.txt");
                    menuVeiculo();
                    break;
                case 6:
                    gereVeiculos.pesquisarVeiculosAposAno(scanner, "credenciais_veiculo.txt");
                    menuVeiculo();
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
        while (!sair && utilizadorAutenticado.getTipo() == TipoUtilizador.MECANICO) {
            exibirMenuMecanico();
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    if (utilizadorAutenticado != null) {
                        gereMecanicos.pesquisarServicosPorLoginMecanico(scanner, "credenciais_veiculo.txt");
                    } else {
                        System.out.println("Faça o login como mecânico primeiro.");
                    }
                    break;
                case 2:
                    if (utilizadorAutenticado != null) {

                    } else {
                        System.out.println("Faça o login como mecânico primeiro.");
                    }
                    break;
                case 3:
                    if (utilizadorAutenticado != null) {

                    } else {
                        System.out.println("Faça o login como mecânico primeiro.");
                    }
                    break;
                case 4:
                    if (utilizadorAutenticado != null) {
                        realizarOperacoes();
                    } else {
                        System.out.println("Faça o login como mecânico primeiro.");
                    }
                    break;
                case 5:
                    alterarDados();
                    break;
                case 0:
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
        System.out.println("------ MENU MECANICO ------");
        System.out.println("1. Pesquisar nome de mecanico");
        System.out.println("2. Consultar Serviços (Mecânico Atual)");
        System.out.println("3. Listar Serviços Realizados (Mecânico Atual)");
        System.out.println("4. Pesquisar Serviços Realizados (Mecânico Atual)");
        System.out.println("5. Logout (Mecânico Atual)");
        System.out.println("6. Alterar Dados (Login/Passwordx)");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
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

}





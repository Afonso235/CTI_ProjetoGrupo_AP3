import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class GereVeiculos {
    private List<Veiculo> veiculos;

    public GereVeiculos() {
        veiculos = new ArrayList<>();
    }

    public void inserirVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    public void removerVeiculo(Veiculo veiculo) {
        veiculos.remove(veiculo);
    }

    public List<Veiculo> listarVeiculos() {
        return veiculos;
    }

    public List<Veiculo> listarVeiculosPorCliente(Cliente cliente) {
        List<Veiculo> veiculosPorCliente = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getCliente().equals(cliente)) {
                veiculosPorCliente.add(veiculo);
            }
        }
        return veiculosPorCliente;
    }

    public List<Veiculo> listarVeiculosComServico() {
        List<Veiculo> veiculosComServico = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getDataConclusao() != null) {
                veiculosComServico.add(veiculo);
            }
        }
        return veiculosComServico;
    }

    public List<Veiculo> listarVeiculosSemServico() {
        List<Veiculo> veiculosSemServico = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getDataConclusao() == null) {
                veiculosSemServico.add(veiculo);
            }
        }
        return veiculosSemServico;
    }

    public List<Veiculo> listarVeiculosPorMecanico(Mecanico mecanico) {
        List<Veiculo> veiculosPorMecanico = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getMecanicoResponsavel().equals(mecanico)) {
                veiculosPorMecanico.add(veiculo);
            }
        }
        return veiculosPorMecanico;
    }

    public Veiculo pesquisarVeiculoPorMatricula(String matricula) {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getMatricula().equals(matricula)) {
                return veiculo;
            }
        }
        return null;
    }

    public List<Veiculo> pesquisarVeiculosPorPeca(String codigoPeca) {
        List<Veiculo> veiculosComPeca = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getListagemReparacoes().contains(codigoPeca)) {
                veiculosComPeca.add(veiculo);
            }
        }
        return veiculosComPeca;
    }

    public List<Veiculo> pesquisarVeiculosAposAno(int ano) {
        List<Veiculo> veiculosAposAno = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getAnoFabrico() > ano) {
                veiculosAposAno.add(veiculo);
            }
        }
        return veiculosAposAno;
    }

    public List<Veiculo> pesquisarVeiculosComTempoDespendidoSuperior(int limiteTempo) {
        List<Veiculo> veiculosComTempoDespendidoSuperior = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            int tempoDespendido = calcularTempoDespendido(veiculo);
            if (tempoDespendido > limiteTempo) {
                veiculosComTempoDespendidoSuperior.add(veiculo);
            }
        }
        return veiculosComTempoDespendidoSuperior;
    }

    private int calcularTempoDespendido(Veiculo veiculo) {
        // Lógica para calcular o tempo despendido em um veículo
        return 0;
    }
    public void inserirVeiculo(Scanner scanner) {
        System.out.println("=== Inserir Veículo ===");
        // Read input values from the user
        // Example:
        // Cliente cliente = ...;
        // String matricula = ...;
        // String marca = ...;
        // String modelo = ...;
        // int anoFabrico = ...;
        // String numeroChassis = ...;
        // List<String> listagemReparacoes = ...;
        // String dataEntrada = ...;
        // String dataConclusao = ...;
        // Mecanico mecanicoResponsavel = ...;

        // Create a new Veiculo object and add it to the GereVeiculos instance
        // Veiculo veiculo = new Veiculo(cliente, matricula, marca, modelo, anoFabrico,
        //                               numeroChassis, listagemReparacoes, dataEntrada, dataConclusao,
        //                               mecanicoResponsavel);
        // gereVeiculos.inserirVeiculo(veiculo);

        System.out.println("Veículo inserido com sucesso.");
    }

    public void removerVeiculo(Scanner scanner) {
        System.out.println("=== Remover Veículo ===");
        System.out.print("Informe a matrícula do veículo a ser removido: ");
        String matricula = scanner.nextLine();

        // Call the removeVeiculo method of GereVeiculos
        // gereVeiculos.removerVeiculo(matricula);

        System.out.println("Veículo removido com sucesso.");
    }

  /*  private void listarVeiculos() {
        System.out.println("=== Listar Veículos ===");
        // Call the listarVeiculos method of GereVeiculos and display the list of vehicles
        // List<Veiculo> veiculos = gereVeiculos.listarVeiculos();
        // for (Veiculo veiculo : veiculos) {
        //     System.out.println(veiculo);
        // }
    }*/

    public void listarVeiculosPorCliente(Scanner scanner) {
        System.out.println("=== Listar Veículos por Cliente ===");
        // Read input values from the user
        // Example:
        // String clienteId = ...;

        // Call the listarVeiculosPorCliente method of GereVeiculos and display the list of vehicles
        // List<Veiculo> veiculos = gereVeiculos.listarVeiculosPorCliente(clienteId);
        // for (Veiculo veiculo : veiculos) {
        //     System.out.println(veiculo);
        // }
    }

    public void listarVeiculosComServicoConcluido() {
        System.out.println("=== Listar Veículos com Serviço Concluído ===");
        // Call the listarVeiculosComServico method of GereVeiculos and display the list of vehicles
        // List<Veiculo> veiculos = gereVeiculos.listarVeiculosComServico();
        // for (Veiculo veiculo : veiculos) {
        //     if (veiculo.getDataConclusao() != null) {
        //         System.out.println(veiculo);
        //     }
        // }
    }

    public void listarVeiculosComServicoEmAndamento() {
        System.out.println("=== Listar Veículos com Serviço em Andamento ===");
        // Call the listarVeiculosComServico method of GereVeiculos and display the list of vehicles
        // List<Veiculo> veiculos = gereVeiculos.listarVeiculosComServico();
        // for (Veiculo veiculo : veiculos) {
        //     if (veiculo.getDataConclusao() == null) {
        //         System.out.println(veiculo);
        //     }
        // }
    }

    public void listarVeiculosPorMecanico(Scanner scanner) {
        System.out.println("=== Listar Veículos por Mecânico ===");
        // Read input values from the user
        // Example:
        // String mecanicoId = ...;

        // Call the listarVeiculosPorMecanico method of GereVeiculos and display the list of vehicles
        // List<Veiculo> veiculos = gereVeiculos.listarVeiculosPorMecanico(mecanicoId);
        // for (Veiculo veiculo : veiculos) {
        //     System.out.println(veiculo);
        // }
    }

    public void pesquisarVeiculoPorMatricula(Scanner scanner) {
        System.out.println("=== Pesquisar Veículo por Matrícula ===");
        System.out.print("Informe a matrícula do veículo: ");
        String matricula = scanner.nextLine();

        // Call the pesquisarVeiculoPorMatricula method of GereVeiculos and display the result
        // Veiculo veiculo = gereVeiculos.pesquisarVeiculoPorMatricula(matricula);
        // System.out.println(veiculo);
    }

    public void pesquisarVeiculosPorPeca(Scanner scanner) {
        System.out.println("=== Pesquisar Veículos por Peça ===");
        System.out.print("Informe o código ou a designação da peça: ");
        String peca = scanner.nextLine();

        // Call the pesquisarVeiculosPorPeca method of GereVeiculos and display the list of vehicles
        // List<Veiculo> veiculos = gereVeiculos.pesquisarVeiculosPorPeca(peca);
        // for (Veiculo veiculo : veiculos) {
        //     System.out.println(veiculo);
        // }
    }

    public void pesquisarVeiculosAposAno(Scanner scanner) {
        System.out.println("=== Pesquisar Veículos após um Determinado Ano ===");
        System.out.print("Informe o ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Call the pesquisarVeiculosAposAno method of GereVeiculos and display the list of vehicles
        // List<Veiculo> veiculos = gereVeiculos.pesquisarVeiculosAposAno(ano);
        // for (Veiculo veiculo : veiculos) {
        //     System.out.println(veiculo);
        // }
    }

    public void pesquisarVeiculosComTempoDespendidoSuperior(Scanner scanner) {
        System.out.println("=== Pesquisar Veículos com Tempo Despendido Superior a um Limite ===");
        System.out.print("Informe o limite de tempo: ");
        int limiteTempo = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Call the pesquisarVeiculosComTempoDespendidoSuperior method of GereVeiculos and display the list of vehicles
        // List<Veiculo> veiculos = gereVeiculos.pesquisarVeiculosComTempoDespendidoSuperior(limiteTempo);
        // for (Veiculo veiculo : veiculos) {
        //     System.out.println(veiculo);
        // }
    }

}

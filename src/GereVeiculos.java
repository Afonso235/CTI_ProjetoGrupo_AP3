import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

class GereVeiculos {
    private List<Veiculo> veiculos;
    private GereMecanico gereMecanico = new GereMecanico();
    private GereUtilizadores gereUtilizadores = new GereUtilizadores();
    private String nomeArquivoVeiculo = "credenciais_veiculo.txt";

    public GereVeiculos() {
        veiculos = new ArrayList<>();
    }

    public void inserirVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
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
    public void salvarVeiculo(Veiculo veiculo, String nomeArquivoVeiculos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivoVeiculos, true))) {
            String linha = veiculo.getCliente().getLogin() + ":" +
                     veiculo.getMatricula() + ":" +
                     veiculo.getMarca() + ":" +
                     veiculo.getModelo() + ":" +
                     veiculo.getAnoFabrico() + ":" +
                     veiculo.getNumeroChassis() + ":" +
                     String.join(",", veiculo.getListagemReparacoes()) + ":" +
                     veiculo.getDataEntrada() + ":" +
                     veiculo.getMecanicoResponsavel().getNome();

            writer.write(linha);
            writer.newLine();

            System.out.println("Dados do veículo guardados com sucesso no ficheiro!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados do veículo.");
        }
    }


    public void inserirVeiculo(Scanner scanner) {
        System.out.println("=== Inserir Veículo ===");

        // Read input values from the user
        System.out.print("Login do Cliente: ");
        String loginCliente = scanner.nextLine();

        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();

        System.out.print("Marca: ");
        String marca = scanner.nextLine();

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();

        System.out.print("Ano de Fabrico: ");
        int anoFabrico = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Número de Chassis: ");
        String numeroChassis = scanner.nextLine();

        System.out.print("Listagem de Reparações (separadas por vírgula): ");
        String listagemReparacoesInput = scanner.nextLine();
        List<String> listagemReparacoes = Arrays.asList(listagemReparacoesInput.split(","));

        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy/MM/dd");
        String dataAtual = formatoData.format(new Date());

        System.out.println("Dia de entrada do veículo: " + dataAtual);

        System.out.println("Lista de Mecânicos Disponíveis:");
        List<Mecanico> mecanicos = gereMecanico.listarMecanicos();
        int counter = 1;
        for (Mecanico mecanico : mecanicos) {
            System.out.println(counter + ". " + mecanico.getNome());
            counter++;
        }

        System.out.print("Selecione o número do Mecânico Responsável: ");
        int mecanicoIndex = scanner.nextInt();
        scanner.nextLine();

        if (mecanicoIndex < 1 || mecanicoIndex > mecanicos.size()) {
            System.out.println("Número inválido. Veículo não inserido.");
            return;
        }

        Mecanico mecanicoResponsavel = mecanicos.get(mecanicoIndex - 1);


        Cliente cliente = gereUtilizadores.getClienteByLogin(loginCliente);
        cliente = new Cliente(loginCliente, "", "", true, "");

        if (cliente == null) {
            System.out.println("Cliente não encontrado. Veículo não inserido.");
            return;
        }

        // Add the cliente to the list
        gereUtilizadores.adicionarCliente(cliente);

        Veiculo veiculo = new Veiculo(cliente, matricula, marca, modelo, anoFabrico,
                numeroChassis, listagemReparacoes, dataAtual, mecanicoResponsavel);
        inserirVeiculo(veiculo);

        salvarVeiculo(veiculo, nomeArquivoVeiculo);

        System.out.println("Veículo inserido com sucesso.");
    }

    public void removerVeiculo(Scanner scanner) {
        System.out.println("Digite a matrícula do veículo a ser removido: ");
        String matricula = scanner.nextLine();

        if (verificarExistenciaMatricula(matricula)) {
            Veiculo veiculoRemovido = veiculos.stream()
                    .filter(veiculo -> veiculo.getMatricula().equals(matricula))
                    .findFirst()
                    .orElse(null);

            veiculos.remove(veiculoRemovido);
            // Remover veículo do arquivo
            removerVeiculoDoArquivo(matricula);

            System.out.println("Veículo removido com sucesso.");
        } else {
            System.out.println("Matrícula não encontrada.");
        }
    }

    private void removerVeiculoDoArquivo(String matricula) {
        File arquivoVeiculos = new File(nomeArquivoVeiculo);
        File arquivoTemporario = new File(nomeArquivoVeiculo + ".tmp");

        boolean linhaRemovida = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoVeiculos));
             BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTemporario))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(":");
                if (campos.length >= 2) {
                    String matriculaVeiculo = campos[1].trim();
                    if (matriculaVeiculo.equals(matricula)) {
                        linhaRemovida = true;
                        continue;
                    }
                }

                writer.write(line);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler ou escrever no arquivo de veículos.");
            return;
        }

        if (linhaRemovida) {
            if (arquivoVeiculos.delete() && arquivoTemporario.renameTo(arquivoVeiculos)) {
                System.out.println("Veículo removido do arquivo com sucesso.");
            } else {
                System.out.println("Falha ao atualizar o arquivo de veículos.");
            }
        } else {
            System.out.println("Matrícula não encontrada no arquivo.");
        }
    }
    private boolean verificarExistenciaMatricula(String matricula) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivoVeiculo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] campos = line.split(":");
                if (campos.length >= 2) {
                    String matriculaVeiculo = campos[1].trim();
                    if (matriculaVeiculo.equals(matricula)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de veículos.");
        }
        return false;
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

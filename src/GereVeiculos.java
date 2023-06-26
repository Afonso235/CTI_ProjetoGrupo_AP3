
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

class GereVeiculos {
    private List<Veiculo> veiculos;
    private GereAplicacao gereAplicacao = new GereAplicacao();
    private GereMecanico gereMecanico = new GereMecanico();
    private GereUtilizadores gereUtilizadores = new GereUtilizadores();
    private String nomeArquivoVeiculo = "credenciais_veiculo.txt";
    public GereVeiculos() {
        veiculos = new ArrayList<>();
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

            veiculos.add(veiculo);

            System.out.println("Dados do veículo guardados com sucesso no ficheiro!");
            gereAplicacao.registarAcao("Gestor", "Inserir veiculo");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados do veículo.");
        }
    }
    public void inserirVeiculo(Scanner scanner) {
        System.out.println("=== Inserir Veículo ===");

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


        System.out.print("Selecione o número do Mecânico Responsável: ");
        int mecanicoIndex = scanner.nextInt();
        scanner.nextLine();

        if (mecanicoIndex < 1 || mecanicoIndex > mecanicos.size()) {
            System.out.println("Número inválido. Veículo não inserido.");
            return;
        }

        Mecanico mecanicoResponsavel = mecanicos.get(mecanicoIndex - 1);


        Cliente cliente = gereUtilizadores.getClienteByLogin(loginCliente);
        cliente = new Cliente(loginCliente, "", "", true, "", TipoUtilizador.CLIENTE);

        if (cliente == null) {
            System.out.println("Cliente não encontrado. Veículo não inserido.");
            gereAplicacao.registarAcao("Gestor", "Cliente não encontrado. Veiculo não inserido.");
            return;
        }

        gereUtilizadores.adicionarCliente(cliente);

        Veiculo veiculo = new Veiculo(cliente, matricula, marca, modelo, anoFabrico,
                numeroChassis, listagemReparacoes, dataAtual, mecanicoResponsavel);

        salvarVeiculo(veiculo, nomeArquivoVeiculo);

        gereAplicacao.registarAcao("Gestor","Veículo inserido com sucesso.");
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
    public void listarVeiculosOrdenadosPorMatricula(String nomeArquivo) {
        List<Veiculo> veiculos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(":");
                if (campos.length >= 9) {
                    String loginCliente = campos[0];
                    String matricula = campos[1];
                    String marca = campos[2];
                    String modelo = campos[3];
                    int anoFabrico = Integer.parseInt(campos[4]);
                    String numeroChassis = campos[5];
                    List<String> listagemReparacoes = Arrays.asList(campos[6].split(","));
                    String dataEntrada = campos[7];
                    String mecanicoResponsavel = campos[8];

                    Cliente cliente = gereUtilizadores.getClienteByLogin(loginCliente);
                    if (cliente != null) {
                        Mecanico mecanico = new Mecanico(mecanicoResponsavel, "", "", true, "", TipoUtilizador.MECANICO);

                        Veiculo veiculo = new Veiculo(cliente, matricula, marca, modelo, anoFabrico,
                                numeroChassis, listagemReparacoes, dataEntrada, mecanico);
                        veiculos.add(veiculo);
                    }
                }
            }

            Collections.sort(veiculos, Comparator.comparing(Veiculo::getMatricula));

            System.out.println("=== Veículos Ordenados por Matrícula ===");
            for (Veiculo veiculo : veiculos) {
                System.out.println("=== Veículo ===");
                System.out.printf("Login Cliente: %s\n", veiculo.getCliente().getLogin());
                System.out.printf("Matrícula: %s\n", veiculo.getMatricula());
                System.out.printf("Marca: %s\n", veiculo.getMarca());
                System.out.printf("Modelo: %s\n", veiculo.getModelo());
                System.out.printf("Ano de Fabrico: %s\n", veiculo.getAnoFabrico());
                System.out.printf("Número de Chassis: %s\n", veiculo.getNumeroChassis());
                System.out.printf("Mecânico Responsável: %s\n", veiculo.getMecanicoResponsavel().getNome());
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo.");
        }
    }
    public void listarVeiculos(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(":");
                if (campos.length >= 8) {
                    String loginCliente = campos[0];
                    String matricula = campos[1];
                    String marca = campos[2];
                    String modelo = campos[3];
                    String ano = campos[4];
                    String dataInicio = campos[7];
                    String mecanicoResponsavel = campos[8];

                    System.out.printf("Login Cliente: %s\n", loginCliente);
                    System.out.printf("Matrícula: %s\n", matricula);
                    System.out.printf("Marca: %s\n", marca);
                    System.out.printf("Modelo: %s\n", modelo);
                    System.out.printf("Ano: %s\n", ano);
                    System.out.printf("Data de Chegada: %s\n", dataInicio);
                    System.out.printf("Mecânico Responsável: %s\n", mecanicoResponsavel);

                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo.");
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
            gereAplicacao.registarAcao("Gestor","Erro ao ler ou escrever no arquivo de veículos.");
            return;
        }

        if (linhaRemovida) {
            if (arquivoVeiculos.delete() && arquivoTemporario.renameTo(arquivoVeiculos)) {
                gereAplicacao.registarAcao("Gestor","Veículo removido do arquivo com sucesso.");
            } else {
                gereAplicacao.registarAcao("Gestor","Falha ao atualizar o arquivo de veículos.");
            }
        } else {
            gereAplicacao.registarAcao("Gestor","Matrícula não encontrada no arquivo.");
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
    public void pesquisarVeiculoPorMatricula(Scanner scanner, String nomeArquivo) {
        System.out.println("=== Pesquisar Veículo por Matrícula ===");
        System.out.print("Informe a matrícula do veículo: ");
        String matricula = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            boolean encontrouVeiculo = false;
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(":");
                if (campos.length >= 9) {
                    String matriculaVeiculo = campos[1];
                    String marca = campos[2];
                    String modelo = campos[3];
                    int anoFabrico = Integer.parseInt(campos[4]);

                    if (matriculaVeiculo.equalsIgnoreCase(matricula)) {
                        encontrouVeiculo = true;
                        System.out.println("=== Veículo Encontrado ===");
                        System.out.printf("Matrícula: %s\n", matriculaVeiculo);
                        System.out.printf("Marca: %s\n", marca);
                        System.out.printf("Modelo: %s\n", modelo);
                        System.out.printf("Ano de Fabrico: %d\n", anoFabrico);
                        System.out.println();
                        break; // Se encontrar o veículo, não é necessário continuar lendo o arquivo
                    }
                }
            }

            if (!encontrouVeiculo) {
                System.out.println("Nenhum veículo encontrado com a matrícula informada.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo.");
        }
    }
    public void pesquisarVeiculosAposAno(Scanner scanner, String nomeArquivo) {
        System.out.println("=== Pesquisar Veículos após um Determinado Ano ===");
        System.out.print("Informe o ano: ");
        int ano = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            boolean encontrouVeiculos = false;
            System.out.println("=== Veículos Encontrados ===");
            while ((linha = reader.readLine()) != null) {
                String[] campos = linha.split(":");
                if (campos.length >= 9) {
                    String matricula = campos[1];
                    String marca = campos[2];
                    String modelo = campos[3];
                    int anoFabrico = Integer.parseInt(campos[4]);

                    if (anoFabrico > ano) {
                        encontrouVeiculos = true;
                        System.out.println("=== Veículo ===");
                        System.out.printf("Matrícula: %s\n", matricula);
                        System.out.printf("Marca: %s\n", marca);
                        System.out.printf("Modelo: %s\n", modelo);
                        System.out.printf("Ano de Fabrico: %d\n", anoFabrico);
                        System.out.println();
                    }
                }
            }

            if (!encontrouVeiculos) {
                System.out.println("Nenhum veículo encontrado após o ano informado.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo.");
        }
    }
}

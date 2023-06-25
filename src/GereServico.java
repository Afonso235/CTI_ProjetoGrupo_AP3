import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GereServico {
    private List<Servico> servicos;
    private GereAplicacao gereAplicacao;

    private String clienteAtual;

    public GereServico() {
        servicos = new ArrayList<>();
        clienteAtual = null;
    }

    public void solicitarServico() {
        Scanner scanner = new Scanner(System.in);

        // Obtenha os detalhes do serviço a ser solicitado
        System.out.print("Responsável: ");
        String responsavel = scanner.nextLine();

        SimpleDateFormat formatoData = new SimpleDateFormat("yyyy/MM/dd");
        String dataAtual = formatoData.format(new Date());

        System.out.println("Dia de entrada do veículo: " + dataAtual);

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        String aceiteString = "false";
        boolean aceite = false;

        Servico servico = new Servico(
                new Mecanico(responsavel, "", "", true, "", TipoUtilizador.MECANICO),
                dataAtual,
                0,
                0.0,
                descricao,
                new ArrayList<>(),
                TipoServico.REPARACAO,
                EstadoServico.PENDENTE,
                new ArrayList<>(),
                aceite
        );

        servicos.add(servico);

        System.out.println("Serviço solicitado com sucesso!");

        try (FileWriter writer = new FileWriter("credenciais_servico.txt", true)) {
            String linha = String.format("%s:%s:%d:%.2f:%s:%s\n",
                    responsavel, dataAtual, 0, 0.0, descricao, aceite ? "true" : "false");
            writer.write(linha);
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo.");
        }
    }

    public void aceitarServicos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("credenciais_servico.txt"))) {
            String linha;
            List<Servico> servicosDisponiveis = new ArrayList<>();
            int contador = 1;
            System.out.println("===== Serviços Disponíveis =====");
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(":");
                if (dados.length == 6) {
                    String responsavel = dados[0];
                    String data = dados[1];
                    int tempoDespendido = Integer.parseInt(dados[2]);
                    double custoReparacao = Double.parseDouble(dados[3]);
                    String descricao = dados[4];
                    boolean aceite = Boolean.parseBoolean(dados[5]);

                    Servico servico = new Servico(
                            new Mecanico(responsavel, "", "", true, "", TipoUtilizador.MECANICO),
                            data,
                            tempoDespendido,
                            custoReparacao,
                            descricao,
                            new ArrayList<>(),
                            TipoServico.REPARACAO,
                            EstadoServico.PENDENTE,
                            new ArrayList<>(),
                            aceite
                    );

                    servicosDisponiveis.add(servico);

                    System.out.println(contador + ". Responsável: " + servico.getMecanicoResponsavel().getNome());
                    System.out.println("   Data: " + servico.getData());
                    System.out.println("   Descrição: " + servico.getDescricao());
                    System.out.println("--------------------------------");
                    contador++;
                }
            }

            if (servicosDisponiveis.isEmpty()) {
                System.out.println("Não há serviços disponíveis.");
                return;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite o número do serviço que você deseja aceitar (0 para cancelar): ");
            int escolha = scanner.nextInt();

            if (escolha > 0 && escolha <= servicosDisponiveis.size()) {
                Servico servicoEscolhido = servicosDisponiveis.get(escolha - 1);
                aceitarServico(servicoEscolhido);
            } else if (escolha != 0) {
                System.out.println("Opção inválida.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de serviços.");
        }
    }
    public void consultarTodosServicos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("credenciais_servico.txt"))) {
            String linha;
            List<Servico> servicosDisponiveis = new ArrayList<>();
            int contador = 1;
            System.out.println("===== Serviços Disponíveis =====");
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(":");
                if (dados.length == 6) {
                    String responsavel = dados[0];
                    String data = dados[1];
                    int tempoDespendido = Integer.parseInt(dados[2]);
                    double custoReparacao = Double.parseDouble(dados[3]);
                    String descricao = dados[4];
                    boolean aceite = Boolean.parseBoolean(dados[5]);

                    Servico servico = new Servico(
                            new Mecanico(responsavel, "", "", true, "", TipoUtilizador.MECANICO),
                            data,
                            tempoDespendido,
                            custoReparacao,
                            descricao,
                            new ArrayList<>(),
                            TipoServico.REPARACAO,
                            EstadoServico.PENDENTE,
                            new ArrayList<>(),
                            aceite
                    );

                    servicosDisponiveis.add(servico);

                    System.out.println(contador + ". Responsável: " + dados[0]);
                    System.out.println("   Data: " + servico.getData());
                    System.out.println("   Descrição: " + servico.getDescricao());
                    System.out.println("--------------------------------");
                    contador++;
                }
            }

            if (servicosDisponiveis.isEmpty()) {
                System.out.println("Não há serviços disponíveis.");
                return;
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de serviços.");
        }
    }

    public void fazerLoginCliente(String nomeCliente){
        clienteAtual = nomeCliente;
        System.out.println("Login efetuado com sucesso para o cliente: " + nomeCliente);
    }

    public void fazerLogoutCliente() {
        clienteAtual = null;
        System.out.println("Logout feito com sucesso");
    }

    public void consultarServicosCliente() {
        if (clienteAtual == null) {
            System.out.println("Nenhum cliente está logado.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("credenciais_servico.txt"))) {
            String linha;
            List<Servico> servicosCliente = new ArrayList<>();
            int contador = 1;
            System.out.println("===== Serviços do Cliente " + clienteAtual + " =====");
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(":");
                if (dados.length == 6) {
                    String responsavel = dados[0];
                    String data = dados[1];
                    int tempoDespendido = Integer.parseInt(dados[2]);
                    double custoReparacao = Double.parseDouble(dados[3]);
                    String descricao = dados[4];
                    boolean aceite = Boolean.parseBoolean(dados[5]);

                    if (responsavel.equals(clienteAtual)) {
                        Servico servico = new Servico(
                                new Mecanico(responsavel, "", "", true, "", TipoUtilizador.MECANICO),
                                data,
                                tempoDespendido,
                                custoReparacao,
                                descricao,
                                new ArrayList<>(),
                                TipoServico.REPARACAO,
                                EstadoServico.PENDENTE,
                                new ArrayList<>(),
                                aceite
                        );

                        servicosCliente.add(servico);

                        System.out.println(contador + ". Responsável: " + dados[0]);
                        System.out.println("   Data: " + servico.getData());
                        System.out.println("   Descrição: " + servico.getDescricao());
                        System.out.println("--------------------------------");
                        contador++;
                    }
                }
            }

            if (servicosCliente.isEmpty()) {
                System.out.println("Não há serviços disponíveis para o cliente " + clienteAtual + ".");
                return;
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de serviços.");
        }
    }




    public void aceitarServico(Servico servico) {
        servico.setAceite(true);
        System.out.println("Serviço aceito com sucesso!");

        try {
            List<String> linhas = new ArrayList<>();
            String linha;

            // Ler o arquivo e atualizar as linhas
            try (BufferedReader reader = new BufferedReader(new FileReader("credenciais_servico.txt"))) {
                while ((linha = reader.readLine()) != null) {
                    String[] dados = linha.split(":");
                    if (dados.length == 6) {
                        String responsavel = dados[0];
                        String data = dados[1];
                        int tempoDespendido = Integer.parseInt(dados[2]);
                        double custoReparacao = Double.parseDouble(dados[3]);
                        String descricao = dados[4];
                        String aceite = dados[5];

                        boolean aceiteAtualizado = servico.equals(new Servico(
                                new Mecanico(responsavel, "", "", true, "", TipoUtilizador.MECANICO),
                                data,
                                tempoDespendido,
                                custoReparacao,
                                descricao,
                                new ArrayList<>(),
                                TipoServico.REPARACAO,
                                EstadoServico.PENDENTE,
                                new ArrayList<>(),
                                false
                        ));

                        // Atualizar o parâmetro de aceite
                        if (aceiteAtualizado) {
                            linha = String.format("%s:%s:%d:%.2f:%s:%s\n",
                                    responsavel, data, tempoDespendido, custoReparacao, descricao, "true");
                        }

                        linhas.add(linha);
                    }
                }
            }

            try (FileWriter writer = new FileWriter("credenciais_servico.txt", false)) {
                for (String updatedLine : linhas) {
                    writer.write(updatedLine);
                }
            }

            // Atualizar a lista de serviços com o status de aceite atualizado
            servico.setAceite(true);
        } catch (IOException e) {
            System.out.println("Erro ao atualizar o arquivo de serviços.");
        }
    }




}

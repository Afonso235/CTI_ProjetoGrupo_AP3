import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GereServico {
    private List<Servico> servicos;

    public GereServico() {
        servicos = new ArrayList<>();
    }

    public void solicitarServico() {
        Scanner scanner = new Scanner(System.in);

        // Obtenha os detalhes do serviço a ser solicitado
        System.out.print("Responsável: ");
        String responsavel = scanner.nextLine();

        System.out.print("Data: ");
        String data = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        Servico servico = new Servico(
                new Mecanico(responsavel, "", "", true, "", TipoUtilizador.MECANICO),
                data,
                0,
                0.0,
                descricao,
                new ArrayList<>(),
                TipoServico.REPARACAO,
                EstadoServico.PENDENTE,
                new ArrayList<>()
        );

        servicos.add(servico);

        System.out.println("Serviço solicitado com sucesso!");

        try (FileWriter writer = new FileWriter("credenciais_servico.txt", true)) {
            String linha = String.format("%s:%s:%d:%.2f:%s\n",
                    responsavel, data, 0, 0.0, descricao);
            writer.write(linha);
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo.");
        }
    }

    public void consultarServicos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("credenciais_servico.txt"))) {
            String linha;
            System.out.println("===== Serviços Disponíveis =====");
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(":");
                if (dados.length == 5) {
                    String responsavel = dados[0];
                    String data = dados[1];
                    int tempoDespendido = Integer.parseInt(dados[2]);
                    double custoReparacao = Double.parseDouble(dados[3]);
                    String descricao = dados[4];

                    Servico servico = new Servico(
                            new Mecanico(responsavel, "", "", true, "", TipoUtilizador.MECANICO),
                            data,
                            tempoDespendido,
                            custoReparacao,
                            descricao,
                            new ArrayList<>(),
                            TipoServico.REPARACAO,
                            EstadoServico.PENDENTE,
                            new ArrayList<>()
                    );

                    System.out.println("Responsável: " + servico.getMecanicoResponsavel().getNome());
                    System.out.println("Data: " + servico.getData());
                    System.out.println("Descrição: " + servico.getDescricao());
                    System.out.println("================================");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de serviços.");
        }
    }




    public static void main(String[] args) {
        GereServico gereServico = new GereServico();

        // Exemplo de uso da função solicitarServico()
        gereServico.solicitarServico();
    }
}

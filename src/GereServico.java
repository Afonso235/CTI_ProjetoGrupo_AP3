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

        System.out.print("Tempo Despendido: ");
        int tempoDespendido = scanner.nextInt();

        System.out.print("Custo de Reparação: ");
        double custoReparacao = scanner.nextDouble();

        scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        // Crie um novo serviço com base nos detalhes fornecidos
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

        servicos.add(servico);

        System.out.println("Serviço solicitado com sucesso!");
    }


    public static void main(String[] args) {
        GereServico gereServico = new GereServico();

        // Exemplo de uso da função solicitarServico()
        gereServico.solicitarServico();
    }
}

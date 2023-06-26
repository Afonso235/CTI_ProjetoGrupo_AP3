import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class GereMecanico {
    private List<Mecanico> mecanicos;
    private GereAplicacao gereAplicacao;
    private String nomeArquivo = "credenciais_acesso.txt";

    public GereMecanico() {
        mecanicos = new ArrayList<>();
    }

    public void listarMecanicosAssociadosAoServico(Servico servico) {
        for (Mecanico mecanico : mecanicos) {
            if (mecanico.realizouServico(servico)) {
                System.out.println(mecanico);
            }
        }
    }

    public void pesquisarServicosPorLoginMecanico(Scanner scanner, String nomeArquivo) {
        System.out.println("=== Pesquisar Serviços por Login do Mecânico Responsável ===");
        System.out.print("Informe o nome de login parcial do Mecânico: ");
        String nomeLoginParcial = scanner.nextLine();

        File arquivo = new File(nomeArquivo);
        try (Scanner fileScanner = new Scanner(arquivo)) {
            boolean encontrouServicos = false;
            while (fileScanner.hasNextLine()) {
                String linha = fileScanner.nextLine();
                String[] campos = linha.split(":");
                if (campos.length >= 9) {
                    String loginMecanico = campos[8];
                    if (loginMecanico.contains(nomeLoginParcial)) {
                        encontrouServicos = true;
                        System.out.println("=== Serviço Encontrado ===");
                        System.out.printf("Login do Cliente: %s\n", campos[0]);
                        System.out.printf("Data de Início do Serviço: %s\n", campos[1]);
                        System.out.printf("Estado: %s\n", campos[2]);
                        System.out.printf("Valor Total: %s\n", campos[3]);
                        System.out.printf("Descrição: %s\n", campos[4]);
                        System.out.printf("Data de Conclusão do Serviço: %s\n", campos[5]);
                        System.out.printf("Estado de Pagamento: %s\n", campos[6]);
                        System.out.printf("Data de Pagamento: %s\n", campos[7]);
                        System.out.printf("Mecanico Responsável: %s\n", loginMecanico);
                        System.out.println();
                    }
                }
            }

            if (!encontrouServicos) {
                System.out.println("Nenhum serviço encontrado para o nome de login do Mecânico informado.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + nomeArquivo);
        }
    }



    public List<Mecanico> listarMecanicos() {
        if (mecanicos.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
                mecanicos = reader.lines()
                        .map(linha -> linha.split(":"))
                        .filter(partes -> partes.length == 4)
                        .filter(partes -> partes[3].equalsIgnoreCase("true"))
                        .filter(partes -> partes[2].equalsIgnoreCase("MECANICO"))
                        .map(partes -> {
                            boolean ativo = Boolean.parseBoolean(partes[3]);
                            return new Mecanico(partes[0], partes[1], partes[0], ativo, partes[2], TipoUtilizador.MECANICO);
                        })
                        .collect(Collectors.toList());

                for (int i = 0; i < mecanicos.size(); i++) {
                    Mecanico mecanico = mecanicos.get(i);
                    System.out.println((i + 1) + ". " + mecanico.getNome());
                }
            } catch (IOException e) {
                System.out.println("Erro ao carregar as credenciais de acesso.");
            }
        }

        return mecanicos;
    }




    @Override
    public String toString() {
        return "GereMecanico{" +
                "mecanicos=" + mecanicos +
                '}';
    }



    public void adicionarMecanico(Mecanico mecanico) {
        mecanicos.add(mecanico);
    }

    public void removerMecanico(Mecanico mecanico) {
        mecanicos.remove(mecanico);
    }

    public Mecanico getMecanicoByNome(String nome) {
        for (Mecanico mecanico : mecanicos) {
            if (mecanico.getNome().equals(nome)) {
                return mecanico;
            }
        }
        return null;
    }
}

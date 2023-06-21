import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class GereMecanico {
    private List<Mecanico> mecanicos;
    private String nomeArquivo = "credenciais_acesso.txt";

    public GereMecanico() {
        mecanicos = new ArrayList<>();
        listarMecanicos();
    }

    public void listarMecanicosAssociadosAoServico(Servico servico) {
        for (Mecanico mecanico : mecanicos) {
            if (mecanico.realizouServico(servico)) {
                System.out.println(mecanico);
            }
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


    public Mecanico loginMecanico(String login, String password, TipoUtilizador tipo) {
        for (Mecanico mecanico : mecanicos) {
            if (mecanico.getLogin().equals(login) && mecanico.getPassword().equals(password)) {
                return mecanico;
            }
        }
        return null;
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

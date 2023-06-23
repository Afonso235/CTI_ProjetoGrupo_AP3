import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GereUtilizadores {
    private List<Cliente> clientes;
    private List<Utilizador> utilizadores;
    private List<Utilizador> pedidosPendentes;
    private GereAplicacao gereAplicacao = new GereAplicacao();
    private String nomeArquivoCredenciais = "credenciais_acesso.txt";

    public GereUtilizadores() {
        this.utilizadores = new ArrayList<>();
        this.pedidosPendentes = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    public List<Utilizador> getUtilizadoresPorAprovarLogin() {
        return utilizadores.stream()
                .filter(utilizador -> !utilizador.isAtivo())
                .collect(Collectors.toList());
    }

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }


    public void adicionarPedido(Utilizador utilizador) {
        pedidosPendentes.add(utilizador);
    }

    public void processarPedidos() {
        for (Utilizador utilizador : pedidosPendentes) {
            if (utilizador.isAtivo()) {
                // Realizar ações adicionais, como enviar um email de boas-vindas, por exemplo
                System.out.println("Pedido de " + utilizador.getNome() + " aprovado!");
            } else {
                System.out.println("Pedido de " + utilizador.getNome() + " rejeitado!");
            }
        }
    }

    public Cliente getClienteByLogin(String login) {
        for (Cliente cliente : clientes) {
            if (cliente.getLogin().equals(login)) {
                return cliente;
            }
        }
        return null;
    }

    public List<Utilizador> getUtilizadores() {
        return utilizadores;
    }

    public void criarConta(String login, String password, String nome, String email, TipoUtilizador tipo) {
        boolean ativo = tipo == TipoUtilizador.GESTOR; // Definir como ativo se for um gestor
        Utilizador utilizador = new Utilizador(login, password, nome, ativo, email, tipo);
        utilizadores.add(utilizador);
        salvarCredenciais();
    }

    public Utilizador login(String login, String password) {
        for (Utilizador utilizador : utilizadores) {
            if (utilizador.getLogin().equals(login) && utilizador.getPassword().equals(password)) {
                return utilizador;
            }
        }
        return null;
    }


    public List<Utilizador> pesquisa(String nome) {
        List<Utilizador> resultados = new ArrayList<>();
        for (Utilizador utilizador : utilizadores) {
            if (utilizador.getNome().equalsIgnoreCase(nome)) {
                resultados.add(utilizador);
            }
        }
        return resultados;
    }


    public void carregarCredenciais() {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivoCredenciais))) {
            utilizadores = reader.lines()
                    .map(linha -> linha.split(":"))
                    .filter(partes -> partes.length == 4) // Verificar se a linha possui 4 partes
                    .map(partes -> {
                        boolean ativo = Boolean.parseBoolean(partes[3]);
                        return new Utilizador(partes[0], partes[1], "", ativo, "", TipoUtilizador.valueOf(partes[2]));
                    })
                    .collect(Collectors.toList());
            System.out.println("Credenciais carregadas com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao carregar as credenciais de acesso.");
        }
    }

    public Utilizador encontrarUtilizadorPorLogin(String login) {
        for (Utilizador utilizador : utilizadores) {
            if (utilizador.getLogin().equals(login)) {
                return utilizador;
            }
        }
        return null;
    }

    public void definirAtivo(String login, boolean ativo) {
        Utilizador utilizador = encontrarUtilizadorPorLogin(login);
        if (utilizador != null) {
            System.out.println("Utilizador encontrado: " + utilizador.getLogin());
            utilizador.setAtivo(ativo);
            salvarCredenciais();
            System.out.println("Status de ativação do utilizador '" + login + "' atualizado com sucesso!");
            gereAplicacao.registarAcao("Gestor", "Admite um utilizador na base de dados");

            try {
                List<String> linhas = Files.readAllLines(Path.of(nomeArquivoCredenciais));
                for (int i = 0; i < linhas.size(); i++) {
                    String linha = linhas.get(i);
                    String[] partes = linha.split(":");
                    if (partes.length == 4 && partes[0].equals(login)) {
                        partes[3] = "true";
                        linhas.set(i, String.join(":", partes));
                        break;
                    }
                }
                Files.write(Path.of(nomeArquivoCredenciais), linhas, StandardOpenOption.WRITE);
            } catch (IOException e) {
                System.out.println("Erro ao atualizar o status de ativação no arquivo.");
            }
        } else {
            System.out.println("Utilizador não encontrado!");
        }
    }

    public void salvarCredenciais() {
        try {
            List<String> linhas = new ArrayList<>();
            for (Utilizador utilizador : utilizadores) {
                String linha = utilizador.getLogin() + ":" +
                                utilizador.getPassword() + ":" +
                                utilizador.getTipo() + ":" +
                                utilizador.isAtivo();
                linhas.add(linha);
            }
            Files.write(Path.of(nomeArquivoCredenciais), linhas, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Credenciais guardadas com sucesso no ficheiro!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar as credenciais de acesso.");
        }
        gereAplicacao.registarAcao("Utilizador", "Foi inserido no ficheiro credenciais_acesso.txt ");
    }



    public void alterarInfos(String login, String aPassword, String aNome, String aEmail) {
        for (Utilizador utilizador : utilizadores) {
            if (utilizador.getLogin().equals(login)) {
                utilizador.setPassword(aPassword);
                utilizador.setNome(aNome);
                utilizador.setEmail(aEmail);
                salvarCredenciais();
                System.out.println("Dados guardados com sucesso!");
                gereAplicacao.registarAcao("Cliente", "Alterar Dados de login");
                return;
            }
        }
        System.out.println("Utilizador inexistente!");
    }

    @Override
    public String toString() {
        return "pedidosPendentes= " + pedidosPendentes;
    }
}

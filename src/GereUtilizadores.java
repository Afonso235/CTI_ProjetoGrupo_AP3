import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class GereUtilizadores {
    private List<Cliente> clientes;
    private List<Utilizador> utilizadores;
    private List<Utilizador> pedidosPendentes;
    private GereAplicacao gereAplicacao = new GereAplicacao();
    private String nomeArquivoCredenciais = "credenciais_acesso.txt";
    private static Set<String> logins = new HashSet<>();
    private static Set<String> emails = new HashSet<>();

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
                System.out.println("Pedido de " + utilizador.getNome() + " aprovado!");
                gereAplicacao.registarAcao("Gestor", "Pedido de Login aprovado!");
            } else {
                System.out.println("Pedido de " + utilizador.getNome() + " rejeitado!");
                gereAplicacao.registarAcao("Gestor", "Pedido de Login rejeitado!");
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
        salvarCredenciais(utilizador);
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
            salvarCredenciais(utilizador);
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

    public void salvarCredenciais(Utilizador novoUtilizador) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivoCredenciais))) {
            String login = novoUtilizador.getLogin();
            String password = novoUtilizador.getPassword();

            if (verificaCredenciais(login, password)) {
                System.out.println("Login ou senha já existem: " + login);
                System.exit(0); // Encerra a aplicação
            }

            for (Utilizador utilizador : utilizadores) {
                String linha = utilizador.getLogin() + ":" + utilizador.getPassword() + ":" +
                        utilizador.getTipo() + ":" +
                        utilizador.isAtivo();
                writer.write(linha);
                writer.newLine();
            }
            System.out.println("Credenciais guardadas com sucesso no ficheiro!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar as credenciais de acesso.");
        }
        gereAplicacao.registarAcao("Utilizador", "Foi inserido no ficheiro credenciais_acesso.txt");
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
    public boolean verificaCredenciais(String login, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivoCredenciais))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                String savedLogin = parts[0];
                String savedPassword = parts[1];

                if (login.equals(savedLogin) && password.equals(savedPassword)) {
                    return true; // Login e senha já existem
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de credenciais.");
            e.printStackTrace();
        }
        return false; // Login e senha estão disponíveis
    }



    public void alterarInfos(String login, String aPassword, String aNome, String aEmail) {
        for (Utilizador utilizador : utilizadores) {
            if (utilizador.getLogin().equals(login)) {
                utilizador.setPassword(aPassword);
                utilizador.setNome(aNome);
                utilizador.setEmail(aEmail);
                salvarCredenciais(utilizador);
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

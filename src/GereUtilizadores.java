import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GereUtilizadores {
    private List<Utilizador> utilizadores;
    private List<Utilizador> pedidosPendentes;
    private String nomeArquivoCredenciais = "credenciais_acesso.txt";

    public GereUtilizadores() {
        this.utilizadores = new ArrayList<>();
        this.pedidosPendentes = new ArrayList<>();

        carregarCredenciais();
    }

    public List<Utilizador> getUtilizadoresPorAprovarLogin() {
        return utilizadores.stream()
                .filter(utilizador -> !utilizador.isAtivo())
                .collect(Collectors.toList());
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
        return utilizadores.stream()
                .filter(utilizador -> utilizador.getLogin().equals(login) && utilizador.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public List<Utilizador> pesquisa(String nome) {
        return utilizadores.stream()
                .filter(utilizador -> utilizador.getNome().equalsIgnoreCase(nome))
                .collect(Collectors.toList());
    }

    public void carregarCredenciais() {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivoCredenciais))) {
            utilizadores = reader.lines()
                    .map(linha -> linha.split(":"))
                    .filter(partes -> partes.length == 3)
                    .map(partes -> new Utilizador(partes[0], partes[1], "", true, "", TipoUtilizador.valueOf(partes[2])))
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
            if (utilizador.getTipo() != TipoUtilizador.GESTOR) {
                utilizador.setAtivo(ativo);
                salvarCredenciais();
                System.out.println("Status de ativação do utilizador '" + login + "' atualizado com sucesso!");
            } else {
                System.out.println("O gestor não precisa esperar pela ativação da conta.");
            }
        } else {
            System.out.println("Utilizador não encontrado!");
        }
    }


    private void salvarCredenciais() {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(nomeArquivoCredenciais), StandardOpenOption.CREATE)) {
            for (Utilizador utilizador : utilizadores) {
                String linha = utilizador.getLogin() + ":" + utilizador.getPassword() + ":" + utilizador.getTipo();
                writer.write(linha);
                writer.newLine();
            }
            System.out.println("Credenciais guardadas com sucesso no ficheiro!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar as credenciais de acesso.");
        }
    }

    public void alterarInfos(String login, String aPassword, String aNome, String aEmail) {
        for (Utilizador utilizador : utilizadores) {
            if (utilizador.getLogin().equals(login)) {
                utilizador.setPassword(aPassword);
                utilizador.setNome(aNome);
                utilizador.setEmail(aEmail);
                salvarCredenciais();
                System.out.println("Dados guardados com sucesso!");
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

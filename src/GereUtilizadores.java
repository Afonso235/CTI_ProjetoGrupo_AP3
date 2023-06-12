import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GereUtilizadores {
    private List<Utilizador> utilizadores;
    private static Scanner scanner;

    private String nomeArquivoCredenciais = "credenciais_acesso.txt";

    public GereUtilizadores() {
        this.utilizadores = new ArrayList<>();
        carregarCredenciais();
    }

    public List<Utilizador> getUtilizadores() {
        return utilizadores;
    }

    public void criarConta(String login, String password, String nome, boolean ativo, String email, TipoUtilizador tipo) {
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
        List<Utilizador> resultado = new ArrayList<>();
        for (Utilizador utilizador : utilizadores) {
            if (utilizador.getNome().equalsIgnoreCase(nome)) {
                resultado.add(utilizador);
            }
        }
        return resultado;
    }




    public void carregarCredenciais() {
        try {
            File arquivo = new File(nomeArquivoCredenciais);
            if (arquivo.exists()) {
                //limpar memoria antes de ler
                BufferedReader reader = new BufferedReader(new FileReader(arquivo));
                String linha;
                while ((linha = reader.readLine()) != null) {
                    String[] partes = linha.split(":");
                    if (partes.length == 3) {
                        String login = partes[0];
                        String password = partes[1];
                        TipoUtilizador tipo = TipoUtilizador.valueOf(partes[2]);
                        Utilizador utilizador = new Utilizador(login, password, "", false, "", tipo);
                        utilizadores.add(utilizador);
                    }
                }
                reader.close();
                System.out.println("Credenciais carregadas com sucesso!");
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar as credenciais de acesso.");
        }
    }

    private void salvarCredenciais() {
        try {
            FileWriter writer = new FileWriter(nomeArquivoCredenciais);
            for (Utilizador utilizador : utilizadores) {
                String linha = utilizador.getLogin() + ":" + utilizador.getPassword() + ":" + utilizador.getTipo();
                writer.write(linha + System.lineSeparator());
            }
            writer.close();
            System.out.println("Credenciais guardadas com sucesso no ficheiro!");


        } catch (IOException e) {
            System.out.println("Erro ao salvar as credenciais de acesso.");
        }
    }


    public void alterarInfos(String login, String aPassword, String aNome, String aEmail){

        for(int i = 0; i < utilizadores.size(); i++){
            Utilizador utilizador = utilizadores.get(i);
            if(utilizador.getLogin().equals(login)){
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
}

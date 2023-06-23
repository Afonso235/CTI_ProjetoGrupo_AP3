import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class GereAplicacao {
    private static final String NOME_ARQUIVO = "dados_apl.dat";
    private static final String NOME_ARQUIVO_LOG = "log.txt";
    public void registarAcao(String utilizador, String acao) {
        try {
            File arquivoLog = new File(NOME_ARQUIVO_LOG);

            StringBuilder conteudoLog = new StringBuilder();
            if (arquivoLog.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(arquivoLog))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        conteudoLog.append(linha).append("\n");
                    }
                }
            }


            // Adicionar a nova ação no início do conteúdo com o prefixo correto
            String novaAcao = utilizador + " " + acao + "\n";
            conteudoLog.insert(0, novaAcao);


            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoLog))) {
                writer.write(conteudoLog.toString());
            }

            System.out.println("Ação registrada com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao registrar ação: " + e.getMessage());
        }
    }

    public void consultarLog() {
        try {
            File arquivoLog = new File(NOME_ARQUIVO_LOG);

            if (arquivoLog.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(arquivoLog))) {
                    String linha;
                    while ((linha = reader.readLine()) != null) {
                        System.out.println(linha);
                    }
                }
            } else {
                System.out.println("O arquivo de log não existe ou está vazio.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao consultar o log: " + e.getMessage());
        }
    }
    public void guardarDados() {
        try {
            File arquivo = new File(NOME_ARQUIVO);
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }

            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(arquivo));
            outputStream.close();
            System.out.println("Os dados foram guardados com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao guardar os dados: " + e.getMessage());
        }
    }


    public void carregarDados() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO));
            inputStream.close();
            System.out.println("Os dados foram lidos com sucesso.");
        } catch (FileNotFoundException e) {
            System.out.println("O arquivo não está disponível.");
        } catch (IOException e) {
            System.out.println("Erro ao ler os dados: " + e.getMessage());
        }
    }
}

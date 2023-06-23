import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;

public class GereAplicacao {
    private static final String NOME_ARQUIVO = "dados_apl.dat";

    public GereAplicacao() {
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GereAplicacao {

    private static final String NOME_ARQUIVO = "dados_apl.dat";
    private static void guardarDados() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO));
            outputStream.close();
            System.out.println("Os dados foram guardados com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao guardar os dados: " + e.getMessage());
        }
    }

    private static void carregarDados() {
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

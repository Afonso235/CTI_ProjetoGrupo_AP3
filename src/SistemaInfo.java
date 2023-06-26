import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

public class SistemaInfo implements Serializable {
    private static final String NOME_ARQUIVO_INFO = "info_sistema.dat";
    private int numeroExecucoes;
    private String ultimoUtilizadorAcesso;
    public SistemaInfo() {
        this.numeroExecucoes = 1;
        this.ultimoUtilizadorAcesso = "";
    }
    public void incrementarNumeroExecucoes() {
        ++this.numeroExecucoes;
    }
    public void setUltimoUtilizadorAcesso(String utilizador) {
        this.ultimoUtilizadorAcesso = utilizador;
    }
    public void guardarInfo() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO_INFO));
            outputStream.writeObject(this);
            outputStream.close();
            System.out.println("Informações do sistema foram guardadas com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao guardar as informações do sistema: " + e.getMessage());
        }
    }
    public static SistemaInfo carregarInfo() {
        try {
            File arquivoInfo = new File(NOME_ARQUIVO_INFO);

            if (!arquivoInfo.exists()) {
                // Cria um novo arquivo se não existir
                SistemaInfo novoSistemaInfo = new SistemaInfo();
                novoSistemaInfo.guardarInfo();
                return novoSistemaInfo;
            }
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO_INFO));
            SistemaInfo sistemaInfo = (SistemaInfo) inputStream.readObject();
            inputStream.close();
            System.out.println("Informações do sistema foram carregadas com sucesso.");
            return sistemaInfo;
        } catch (FileNotFoundException e) {
            System.out.println("O arquivo de informações do sistema não está disponível.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar as informações do sistema: " + e.getMessage());
        }
        return null;
    }
}
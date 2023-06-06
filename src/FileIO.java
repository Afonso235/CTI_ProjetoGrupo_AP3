import java.io.*;

public class FileIO {

    public void readDataFromFile() {
        try {
            File file = new File("dados_apl.dat");
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                // Lógica para ler os objetos do arquivo

                objectInputStream.close();
                System.out.println("Dados lidos com sucesso.");
            } else {
                System.out.println("O arquivo de dados não está disponível.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataToFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("dados_apl.dat");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            // Lógica para escrever os objetos no arquivo

            objectOutputStream.close();
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

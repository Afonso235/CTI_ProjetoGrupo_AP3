import java.util.ArrayList;
import java.util.List;

public class Oficina {
    private List<Utilizador> utilizadores;
    private List<Veiculo> veiculos;
    private List<Peca> pecas;
    private List<Fornecedor> fornecedores;

    public Oficina() {
        utilizadores = new ArrayList<>();
        veiculos = new ArrayList<>();
        pecas = new ArrayList<>();
        fornecedores = new ArrayList<>();
    }

    // métodos para gerir a oficina, como adicionar/utilizar/utilizar utilizadores, veículos, peças e fornecedores
}
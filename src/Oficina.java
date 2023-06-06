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

    public void adicionarUtilizador(Utilizador utilizador) {
        utilizadores.add(utilizador);
    }

    public List<Utilizador> getUtilizadores() {
        return utilizadores;
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    public List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public void adicionarPeca(Peca peca) {
        pecas.add(peca);
    }

    public List<Peca> getPecas() {
        return pecas;
    }

    public void adicionarFornecedor(Fornecedor fornecedor) {
        fornecedores.add(fornecedor);
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

}
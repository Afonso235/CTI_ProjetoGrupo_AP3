public class Utilizador {

    private Pessoa pessoa;

    public Utilizador(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    // gettes e setters
    public Pessoa getPessoa() {
        return pessoa;
    }

    @Override
    public String toString() {
        return "Utilizador{" +
                "pessoa=" + pessoa +
                '}';
    }
}

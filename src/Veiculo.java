import java.util.List;

class Veiculo {
    private Cliente cliente;
    private String matricula;
    private String marca;
    private String modelo;
    private int anoFabrico;
    private String numeroChassis;
    private List<String> listagemReparacoes;
    private String dataEntrada;
    private String dataConclusao;

    public Veiculo(Cliente cliente, String matricula, String marca, String modelo, int anoFabrico,
                   String numeroChassis, List<String> listagemReparacoes, String dataEntrada, String dataConclusao) {
        this.cliente = cliente;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.anoFabrico = anoFabrico;
        this.numeroChassis = numeroChassis;
        this.listagemReparacoes = listagemReparacoes;
        this.dataEntrada = dataEntrada;
        this.dataConclusao = dataConclusao;
    }

    // Getters e setters
}

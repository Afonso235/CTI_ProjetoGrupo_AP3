import java.util.ArrayList;
import java.util.List;

class Veiculo {
    private Cliente cliente;
    private final String matriculas;
    private String marca;
    private String modelo;
    private int anoFabrico;
    private final String numeroChassis;
    private List<String> listagemReparacoes;
    private String dataEntrada;
    private String dataConclusao;
    private Mecanico mecanicoResponsavel;


    public Veiculo(Cliente cliente, String matriculas, String marca, String modelo, int anoFabrico,
                   String numeroChassis, List<String> listagemReparacoes, String dataEntrada, String dataConclusao, Mecanico mecanicoResponsavel) {
        this.cliente = cliente;
        this.matriculas = matriculas;
        this.marca = marca;
        this.modelo = modelo;
        this.anoFabrico = anoFabrico;
        this.numeroChassis = numeroChassis;
        this.listagemReparacoes = listagemReparacoes;
        this.dataEntrada = dataEntrada;
        this.dataConclusao = dataConclusao;
        this.mecanicoResponsavel = mecanicoResponsavel;
    }

    // Getters e setters


    public Mecanico getMecanicoResponsavel() {
        return mecanicoResponsavel;
    }

    public void setMecanicoResponsavel(Mecanico mecanicoResponsavel) {
        this.mecanicoResponsavel = mecanicoResponsavel;
    }
}

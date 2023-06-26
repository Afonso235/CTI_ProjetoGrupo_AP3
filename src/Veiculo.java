import java.text.SimpleDateFormat;
import java.util.Date;
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
    private Mecanico mecanicoResponsavel;


    public Veiculo(Cliente cliente, String matricula, String marca, String modelo, int anoFabrico,
                   String numeroChassis, List<String> listagemReparacoes, String dataEntrada, Mecanico mecanicoResponsavel) {
        if (cliente != null) {
            this.cliente = cliente;
        } else {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.anoFabrico = anoFabrico;
        this.numeroChassis = numeroChassis;
        this.listagemReparacoes = listagemReparacoes;
        this.dataEntrada = dataEntrada;
        this.mecanicoResponsavel = mecanicoResponsavel;
    }
    public Veiculo() {
    }
    public Mecanico getMecanicoResponsavel() {
        return mecanicoResponsavel;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public String getMatricula() {
        return matricula;
    }
    public String getMarca() {
        return marca;
    }
    public String getModelo() {
        return modelo;
    }
    public int getAnoFabrico() {
        return anoFabrico;
    }
    public String getNumeroChassis() {
        return numeroChassis;
    }
    public List<String> getListagemReparacoes() {
        return listagemReparacoes;
    }
    public String getDataEntrada() {
        return dataEntrada;
    }
    public String getDataConclusao() {
        return dataConclusao;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Veiculo {\n");
        sb.append("  Cliente: ").append(cliente).append("\n");
        sb.append("  Matrícula: ").append(matricula).append("\n");
        sb.append("  Marca: ").append(marca).append("\n");
        sb.append("  Modelo: ").append(modelo).append("\n");
        sb.append("  Ano de Fabrico: ").append(anoFabrico).append("\n");
        sb.append("  Número de Chassis: ").append(numeroChassis).append("\n");
        sb.append("  Listagem de Reparações: ").append(listagemReparacoes).append("\n");
        sb.append("  Data de Entrada: ").append(dataEntrada).append("\n");
        sb.append("  Data de Conclusão: ").append(dataConclusao).append("\n");
        sb.append("  Mecânico Responsável: ").append(mecanicoResponsavel).append("\n");
        sb.append("}");

        return sb.toString();
    }
}

import java.util.List;
import java.util.Objects;

class Servico {
    private Mecanico mecanicoResponsavel;
    private String data;
    private int tempoDespendido;
    private double custoReparacao;
    private String descricao;
    private List<Peca> pecasUsadas;
    private TipoServico tipo;
    private EstadoServico estado;
    private List<SubTarefa> subTarefas;
    private String codUnico;
    private boolean aceite;

    public Servico(Mecanico mecanicoResponsavel, String data, int tempoDespendido, double custoReparacao,
                   String descricao, List<Peca> pecasUsadas, TipoServico tipo, EstadoServico estado,
                   List<SubTarefa> subTarefas, boolean aceite, String codUnico) {
        this.mecanicoResponsavel = mecanicoResponsavel;
        this.data = data;
        this.tempoDespendido = tempoDespendido;
        this.custoReparacao = custoReparacao;
        this.descricao = descricao;
        this.pecasUsadas = pecasUsadas;
        this.tipo = tipo;
        this.estado = estado;
        this.subTarefas = subTarefas;
        this.aceite = aceite;
        this.codUnico = codUnico;
    }
    public String getCodUnico() {
        return codUnico;
    }
    public void setCodUnico(String codUnico) {
        this.codUnico = codUnico;
    }
    public void setAceite(boolean aceite) {
        this.aceite = aceite;
    }
    public String getMecanicoResponsavel() {
        return mecanicoResponsavel.getLogin();
    }
    public String getData() {
        return data;
    }
    public double getCustoReparacao() {
        return custoReparacao++;
    }
    public String getDescricao() {
        return descricao;
    }
    public EstadoServico getEstado() {
        return estado;
    }
    @Override
    public int hashCode() {
        return Objects.hash(mecanicoResponsavel.getNome(), data, descricao);
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servico servico = (Servico) o;
        return Objects.equals(mecanicoResponsavel.getNome(), servico.mecanicoResponsavel.getNome()) &&
                Objects.equals(data, servico.data) &&
                Objects.equals(descricao, servico.descricao);
    }
}
// Enumeração para os tipos de serviço
enum TipoServico {
    REPARACAO,
    REVISAO,
    INSPECAO
}

// Enumeração para os estados do serviço
enum EstadoServico {
    PENDENTE,
    ACEITE,
    EM_ANDAMENTO,
    CONCLUIDO,
    ENCERRADO
}


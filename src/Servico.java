import java.util.List;

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

    public Servico(Mecanico mecanicoResponsavel, String data, int tempoDespendido, double custoReparacao,
                   String descricao, List<Peca> pecasUsadas, TipoServico tipo, EstadoServico estado,
                   List<SubTarefa> subTarefas) {
        this.mecanicoResponsavel = mecanicoResponsavel;
        this.data = data;
        this.tempoDespendido = tempoDespendido;
        this.custoReparacao = custoReparacao;
        this.descricao = descricao;
        this.pecasUsadas = pecasUsadas;
        this.tipo = tipo;
        this.estado = estado;
        this.subTarefas = subTarefas;
    }

    // Getters e setters

    public Mecanico getMecanicoResponsavel() {
        return mecanicoResponsavel;
    }

    public void setMecanicoResponsavel(Mecanico mecanicoResponsavel) {
        this.mecanicoResponsavel = mecanicoResponsavel;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getTempoDespendido() {
        return tempoDespendido;
    }

    public void setTempoDespendido(int tempoDespendido) {
        this.tempoDespendido = tempoDespendido;
    }

    public double getCustoReparacao() {
        return custoReparacao;
    }

    public void setCustoReparacao(double custoReparacao) {
        this.custoReparacao = custoReparacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Peca> getPecasUsadas() {
        return pecasUsadas;
    }

    public void setPecasUsadas(List<Peca> pecasUsadas) {
        this.pecasUsadas = pecasUsadas;
    }

    public TipoServico getTipo() {
        return tipo;
    }

    public void setTipo(TipoServico tipo) {
        this.tipo = tipo;
    }

    public EstadoServico getEstado() {
        return estado;
    }

    public void setEstado(EstadoServico estado) {
        this.estado = estado;
    }

    public List<SubTarefa> getSubTarefas() {
        return subTarefas;
    }

    public void setSubTarefas(List<SubTarefa> subTarefas) {
        this.subTarefas = subTarefas;
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


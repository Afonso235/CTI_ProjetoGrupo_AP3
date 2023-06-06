import java.util.List;

class Servico {
    private Mecanico mecanicoResponsavel;
    private Mecanico mecanicoPrincipal;
    private String data;
    private int tempoDespendido;
    private double custoReparacao;
    private String descricao;
    private List<Peca> pecasUsadas;
    private TipoServico tipo;
    private EstadoServico estado;
    private List<SubTarefa> subTarefas;

    public Servico(Mecanico mecanicoResponsavel, Mecanico mecanicoPrincipal, String data, int tempoDespendido, double custoReparacao,
                   String descricao, List<Peca> pecasUsadas, TipoServico tipo, EstadoServico estado,
                   List<SubTarefa> subTarefas) {
        this.mecanicoResponsavel = mecanicoResponsavel;
        this.mecanicoPrincipal = mecanicoPrincipal;
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
}

// Enumeração para os tipos de serviço
enum TipoServico {
    REPARACAO,
    REVISAO,
    INSPECAO
}

// Enumeração para os estados do serviço
enum EstadoServico {
    ACEITE,
    EM_ANDAMENTO,
    CONCLUIDO,
    ENCERRADO
}


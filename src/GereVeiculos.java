import java.util.ArrayList;
import java.util.List;

class GereVeiculos {
    private List<Veiculo> veiculos;

    public GereVeiculos() {
        veiculos = new ArrayList<>();
    }

    public void inserirVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    public void removerVeiculo(Veiculo veiculo) {
        veiculos.remove(veiculo);
    }

    public List<Veiculo> listarVeiculos() {
        return veiculos;
    }

    public List<Veiculo> listarVeiculosPorCliente(Cliente cliente) {
        List<Veiculo> veiculosPorCliente = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getCliente().equals(cliente)) {
                veiculosPorCliente.add(veiculo);
            }
        }
        return veiculosPorCliente;
    }

    public List<Veiculo> listarVeiculosComServico() {
        List<Veiculo> veiculosComServico = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getDataConclusao() != null) {
                veiculosComServico.add(veiculo);
            }
        }
        return veiculosComServico;
    }

    public List<Veiculo> listarVeiculosSemServico() {
        List<Veiculo> veiculosSemServico = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getDataConclusao() == null) {
                veiculosSemServico.add(veiculo);
            }
        }
        return veiculosSemServico;
    }

    public List<Veiculo> listarVeiculosPorMecanico(Mecanico mecanico) {
        List<Veiculo> veiculosPorMecanico = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getMecanicoResponsavel().equals(mecanico)) {
                veiculosPorMecanico.add(veiculo);
            }
        }
        return veiculosPorMecanico;
    }

    public Veiculo pesquisarVeiculoPorMatricula(String matricula) {
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getMatricula().equals(matricula)) {
                return veiculo;
            }
        }
        return null;
    }

    public List<Veiculo> pesquisarVeiculosPorPeca(String codigoPeca) {
        List<Veiculo> veiculosComPeca = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getListagemReparacoes().contains(codigoPeca)) {
                veiculosComPeca.add(veiculo);
            }
        }
        return veiculosComPeca;
    }

    public List<Veiculo> pesquisarVeiculosAposAno(int ano) {
        List<Veiculo> veiculosAposAno = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            if (veiculo.getAnoFabrico() > ano) {
                veiculosAposAno.add(veiculo);
            }
        }
        return veiculosAposAno;
    }

    public List<Veiculo> pesquisarVeiculosComTempoDespendidoSuperior(int limiteTempo) {
        List<Veiculo> veiculosComTempoDespendidoSuperior = new ArrayList<>();
        for (Veiculo veiculo : veiculos) {
            int tempoDespendido = calcularTempoDespendido(veiculo);
            if (tempoDespendido > limiteTempo) {
                veiculosComTempoDespendidoSuperior.add(veiculo);
            }
        }
        return veiculosComTempoDespendidoSuperior;
    }

    private int calcularTempoDespendido(Veiculo veiculo) {
        // Lógica para calcular o tempo despendido em um veículo
        return 0;
    }
}

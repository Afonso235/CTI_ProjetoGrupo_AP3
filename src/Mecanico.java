import java.util.ArrayList;
import java.util.List;

class Mecanico extends Utilizador {

    private List<Servico> servicosRealizados;
    public Mecanico(String login, String password, String nome, boolean ativo, String email, TipoUtilizador tipoUtilizador) {
        super(login, password, nome, ativo, email, TipoUtilizador.MECANICO);
        servicosRealizados = new ArrayList<>();
    }

    public Mecanico() {
    }


    public boolean realizouServico(Servico servico) {
        return servicosRealizados.contains(servico);
    }
    public void listarServicosRealizados() {
        for (Servico servico : servicosRealizados) {
            System.out.println(servico);
        }
    }

    public void pesquisarServicosRealizados(String termoPesquisa) {
        for (Servico servico : servicosRealizados) {
            if (servico.getDescricao().contains(termoPesquisa)) {
                System.out.println(servico);
            }
        }
    }

    public void adicionarServicoRealizado(Servico servico) {
        servicosRealizados.add(servico);
    }

    public void removerServicoRealizado(Servico servico) {
        servicosRealizados.remove(servico);
    }

    public List<Servico> getServicosRealizados() {
        return servicosRealizados;
    }

    public void setServicosRealizados(List<Servico> servicosRealizados) {
        this.servicosRealizados = servicosRealizados;
    }

    @Override
    public String toString() {

        return "\nMecanico [\n" +
                "  Login: " + getLogin() + "\n" +
                "  Ativo: " + isAtivo() + "\n" +
                "  Serviços Realizados: " + servicosRealizados + "\n" +
                "]";
    }
}

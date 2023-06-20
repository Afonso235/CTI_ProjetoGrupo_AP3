import java.util.ArrayList;
import java.util.List;

class Mecanico extends Utilizador {

    private List<Servico> servicosRealizados;

    public Mecanico(String login, String password, String nome, boolean ativo, String email) {
        super(login, password, nome, ativo, email, TipoUtilizador.MECANICO);
        servicosRealizados = new ArrayList<>();
    }

    public void consultarServicos() {
        // Implement logic to allow mechanics to view their services
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
}

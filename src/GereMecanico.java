import java.util.ArrayList;
import java.util.List;

class GereMecanico {
    private List<Mecanico> mecanicos;

    public GereMecanico() {
        mecanicos = new ArrayList<>();
    }

    public void listarMecanicosAssociadosAoServico(Servico servico) {
        for (Mecanico mecanico : mecanicos) {
            if (mecanico.realizouServico(servico)) {
                System.out.println(mecanico);
            }
        }
    }
    public Mecanico loginMecanico(String login, String password, TipoUtilizador tipo) {
        for (Mecanico mecanico : mecanicos) {
            if (mecanico.getLogin().equals(login) && mecanico.getPassword().equals(password)) {
                return mecanico;
            }
        }
        return null;
    }
    public void adicionarMecanico(Mecanico mecanico) {
        mecanicos.add(mecanico);
    }

    public void removerMecanico(Mecanico mecanico) {
        mecanicos.remove(mecanico);
    }
}
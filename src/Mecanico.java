import java.util.ArrayList;
import java.util.List;

class Mecanico extends Utilizador {

    private List<Servico> servicosRealizados;
    public Mecanico(String login, String password, String nome, boolean ativo, String email, TipoUtilizador tipoUtilizador) {
        super(login, password, nome, ativo, email, TipoUtilizador.MECANICO);
        servicosRealizados = new ArrayList<>();
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

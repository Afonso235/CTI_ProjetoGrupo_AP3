class Mecanico extends Utilizador {
    public Mecanico(String login, String password, String nome, boolean ativo, String email) {
        super(login, password, nome, ativo, email, TipoUtilizador.MECANICO);
    }

}
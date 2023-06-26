class Gestor extends Utilizador {
    public Gestor(String login, String password, String nome, boolean ativo, String email) {
        super(login, password, nome, ativo, email, TipoUtilizador.GESTOR);
    }


}

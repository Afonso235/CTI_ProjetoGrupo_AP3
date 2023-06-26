class Cliente extends Utilizador {
    public Cliente(String login, String password, String nome, boolean ativo, String email, TipoUtilizador tipoUtilizador) {
        super(login, password, nome, ativo, email, TipoUtilizador.CLIENTE);
    }
}

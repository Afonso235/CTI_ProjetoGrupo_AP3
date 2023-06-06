class Cliente extends Utilizador {
    public Cliente(String login, String password, String nome, boolean ativo, String email) {
        super(login, password, nome, ativo, email, TipoUtilizador.CLIENTE);
    }

    // Outros métodos específicos para Cliente
}

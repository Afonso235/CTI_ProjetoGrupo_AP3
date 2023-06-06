class Utilizador {
    private String login;
    private String password;
    private String nome;
    private boolean ativo;
    private String email;
    private TipoUtilizador tipo;

    public Utilizador(String login, String password, String nome, boolean ativo, String email, TipoUtilizador tipo) {
        this.login = login;
        this.password = password;
        this.nome = nome;
        this.ativo = ativo;
        this.email = email;
        this.tipo = tipo;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoUtilizador getTipo() {
        return tipo;
    }

    public void setTipo(TipoUtilizador tipo) {
        this.tipo = tipo;
    }
}
enum TipoUtilizador {
    GESTOR,
    MECANICO,
    CLIENTE
}
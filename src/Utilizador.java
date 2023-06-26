
class Utilizador {
    private String login;
    private String password;
    private String nome;
    private boolean ativo;
    private String email;
    private TipoUtilizador tipo;
    private String nif;
    private String morada;
    private String contactoTelefonico;
    public Utilizador(String login, String password, String nome, boolean ativo, String email, TipoUtilizador tipo) {
        this.login = login;
        this.password = password;
        this.nome = nome;
        this.ativo = ativo;
        this.email = email;
        this.tipo = tipo;
    }
    public Utilizador(String nome, String nif, String morada, String contactoTelefonico) {
        this.nome = nome;
        this.nif = nif;
        this.morada = morada;
        this.contactoTelefonico = contactoTelefonico;
    }
    public String getLogin() {
        return login;
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
    @Override
    public String toString() {
        return "Nome Login: " + login;
    }
}
enum TipoUtilizador {
    GESTOR,
    MECANICO,
    CLIENTE;

    public static TipoUtilizador fromInt(int value) {
        switch (value) {
            case 1:
                return CLIENTE;
            case 2:
                return MECANICO;
            case 3:
                return GESTOR;
            default:
                throw new IllegalArgumentException("Invalid integer value for TipoUtilizador");
        }
    }
}
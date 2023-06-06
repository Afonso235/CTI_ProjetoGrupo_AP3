public class Main {
    public static void main(String[] args) {
        // Criação de objetos Pessoa
        Pessoa pessoa1 = new Pessoa("João", "123456789", "Rua A", "123456789");
        Pessoa pessoa2 = new Pessoa("Maria", "987654321", "Rua B", "987654321");
        Pessoa pessoa3 = new Pessoa("Pedro", "456789123", "Rua C", "456789123");

        // Criação de objetos Utilizador (mecânicos, gestores e clientes)
        Utilizador utilizador1 = new Mecanico(pessoa1);
        Utilizador utilizador2 = new Gestor(pessoa2);
        Utilizador utilizador3 = new Cliente(pessoa3);

        // Adicionar utilizadores à lista de utilizadores da oficina
        Oficina oficina = new Oficina();
        oficina.adicionarUtilizador(utilizador1);
        oficina.adicionarUtilizador(utilizador2);
        oficina.adicionarUtilizador(utilizador3);

        // Exemplo de utilização dos utilizadores adicionados
        for (Utilizador utilizador : oficina.getUtilizadores()) {
            if (utilizador instanceof Mecanico) {
                System.out.println("Mecânico: " + utilizador.getPessoa().getNome());
            } else if (utilizador instanceof Gestor) {
                System.out.println("Gestor: " + utilizador.getPessoa().getNome());
            } else if (utilizador instanceof Cliente) {
                System.out.println("Cliente: " + utilizador.getPessoa().getNome());
            }
        }
    }
}

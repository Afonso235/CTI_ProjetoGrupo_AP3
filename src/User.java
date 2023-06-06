import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    private String login;
    private String password;
    private String name;
    private boolean active;
    private String email;
    private UserType type;

    private static List<User> users = new ArrayList<>();

    public User(String login, String password, String name, String email, UserType type) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.type = type;
        this.active = true;
    }

    public User() {

    }

    public boolean authenticateUser(String username, String password) {
        try {
            File file = new File("credenciais_acesso.txt");
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Ficheiro criado com sucesso!");
            }
            System.out.println("Ficheiro existe na base de dados!");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] credentials = line.split(":");
                if (credentials.length == 2 && credentials[0].equals(username) && credentials[1].equals(password)) {
                    scanner.close();
                    return true;
                }
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void updateProfile(String password, String name, String email) {
        if (this.login.equals(getCurrentUserLogin())) {
            this.password = password;
            this.name = name;
            this.email = email;
        } else {
            System.out.println("Acesso não autorizado para atualizar perfil.");
        }
    }

    public static User login(String login, String password) {
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password) && user.isActive()) {
                return user;
            }
        }
        return null;
    }

    public static void addUser(User user) {
        for (User existingUser : users) {
            if (existingUser.getLogin().equals(user.getLogin()) || existingUser.getEmail().equals(user.getEmail())) {
                System.out.println("Login ou email já existente. Não foi possível adicionar o utilizador.");
                return;
            }
        }
        users.add(user);
        System.out.println("Utilizador adicionado com sucesso.");
    }

    public static void removeUser(User user) {
        if (user.isActive()) {
            user.setActive(false);
            System.out.println("Utilizador removido com sucesso.");
        } else {
            System.out.println("O utilizador já está inativo.");
        }
    }

    private static String getCurrentUserLogin() {
        // Lógica para obter o login do utilizador atual
        return "login do utilizador atual";
    }

    // Getters e setters

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User -> " +
                "login='" + login +
                ", password='" + password +
                ", name='" + name +
                ", active=" + active +
                ", email='" + email +
                ", type=" + type;
    }
}

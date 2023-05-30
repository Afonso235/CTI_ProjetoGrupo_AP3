import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GereUser {

    private List<User> users;

    public GereUser(List<User> users) {
        users = new ArrayList<>();
    }

    public boolean hasExistingUsersFile() {
        try {
            File file = new File("credenciais_acesso.txt");
            if (file.exists() && file.length() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Métodos para gestão dos utilizadores
    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }


}

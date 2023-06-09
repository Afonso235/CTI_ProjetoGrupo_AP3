import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GestaoOficina {
    private static Garage garage;
    private static User currentUser;
    private static GereUser user;
    private static Vehicle vehicle;
    private static Scanner scanner;
    private static FileIO file;
    private static GereVehicle gVehicle;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        garage = new Garage();
        currentUser = new User();
        vehicle = new Vehicle();
        user = new GereUser();
        file = new FileIO();

        file.readDataFromFile();


        // Load data from file
        user.addUser(currentUser);


        // Verifica se existem utilizadores criados
        if (user.hasExistingUsersFile()) {
            System.out.println("O ficheiro ja contém utilizadores!");
            menuLogin();
        } else {
            System.out.println("Não existem utilizadores!");
            createUser();
        }

        menuLogic();


    }

    private static void menuLogin() {
        boolean exit = false;
        while (!exit) {
            System.out.println("----- Menu Login -----");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Terminar a Aplicação");
            System.out.println("--------------------------");
            int choice = getUserChoice();

            switch (choice) {
                case 1 -> login();
                case 2 -> createUser();
                case 3 -> {
                    // Terminar a aplicação
                    file.writeDataToFile();
                    exit = true;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
        // Fecha o scanner
        scanner.close();
    }


    private static void menuLogic() {
        // Menu principal
        // Exibe mensagem de boas-vindas
        System.out.println("Bem-vindo, " + currentUser.getName());
        boolean exit = false;
        while (!exit) {
            displayMainMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1 -> vehicleManagement();
                case 2 -> serviceManagement();
               // case 3 -> listingsAndSearches();
                case 4 -> {
                    // Terminar a aplicação
                    file.writeDataToFile();
                    exit = true;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }

        // Exibe mensagem de despedida
        System.out.println("Adeus, " + currentUser.getName());

        // Fecha o scanner
        scanner.close();
    }

    private static void login() {
        System.out.print("Username: ");
        String login = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        boolean isAuthenticated = currentUser.authenticateUser(login, password);

        // If the authentication fails, prompt for credentials again
        if (!isAuthenticated) {
            System.out.println("Invalid credentials. Please try again.");
            menuLogin();
        } else {
            System.out.println("Authentication successful!");
            menuLogic();
        }
    }

    private static void createUser() {
        System.out.print("Registo\n");
        System.out.print("Nome: ");
        String name = scanner.next();
        System.out.print("Username: ");
        String login = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        // Armazenamento das credenciais
        if (armazenarCredenciais(login, password)) {
            System.out.println("Credenciais armazenadas com sucesso!");
            // Restante da lógica da sua aplicação
            login();
        } else {
            System.out.println("Erro ao armazenar as credenciais.");
            // Lógica para lidar com o erro de armazenamento das credenciais
        }


        User newUser = new User();
        user.addUser(newUser);
    }

    private static boolean armazenarCredenciais(String login, String senha) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("credenciais_acesso.txt", true));
            writer.write(login + ":" + senha);
            writer.newLine();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    private static void displayMainMenu() {
        System.out.println("----- Menu Principal -----");
        System.out.println("1. Gestão de Veículos");
        System.out.println("2. Gestão de Serviços");
        System.out.println("3. Listagens e Pesquisas");
        System.out.println("4. Terminar a Aplicação");
        System.out.println("--------------------------");
    }

    private static int getUserChoice() {
        System.out.print("Escolha uma opção: ");
        return scanner.nextInt();
    }


    private static void displayVehicleManagementMenu() {
        System.out.println("----- Gestão de Veículos -----");
        System.out.println("1. Criar um novo veículo");
        System.out.println("2. Eliminar um veículo existente");
        System.out.println("3. Voltar ao menu principal");
        System.out.println("-----------------------------");
    }

    private static void vehicleManagement() {
        boolean exit = false;
        while (!exit) {
            displayVehicleManagementMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1 -> createVehicle();
                case 2 -> deleteVehicle();
                case 3 -> {
                    file.writeDataToFile();
                    exit = true;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void createVehicle() {
        System.out.println("Cliente: "+ vehicle.getClient());
        System.out.println("Matrícula: ");
        String licensePlate = scanner.next();
        System.out.println("Marca: ");
        String brand = scanner.next();
        System.out.println("Modelo: ");
        String model = scanner.next();
        System.out.println("Ano: ");
        int year = scanner.nextInt();
        scanner.next(); // Limpar a nova linha pendente
        System.out.println("Número de chassi: ");
        String chassisNumber = scanner.next();

        gVehicle.createVehicle(vehicle.getClient(), licensePlate, brand, model, year, chassisNumber);
    }

    private static void deleteVehicle() {
        System.out.print("Matrícula do veículo a eliminar: ");
        String licensePlate = scanner.next();

        gVehicle.deleteVehicleByLicensePlate(licensePlate);
    }


    private static void serviceManagement() {
        boolean exit = false;
        while (!exit) {
            displayServiceManagementMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1 -> Service.createService(scanner, garage);
                case 2 -> Service.updateServiceStatus(scanner, garage);
                case 3 -> {
                    file.writeDataToFile();
                    exit = true;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }

    }

    private static void displayServiceManagementMenu() {
        System.out.println("----- Gestão de Serviços -----");
        System.out.println("1. Criar um novo serviço");
        System.out.println("2. Atualizar estado de um serviço");
        System.out.println("3. Voltar ao Menu Principal");
        System.out.println("-----------------------------");
    }

}
package Projeto1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.IOException;

public class Garage {
    private List<User> users;
    private List<Vehicle> vehicles;
    private List<Service> services;
    private Map<String, Part> parts;


    public Garage() {
        users = new ArrayList<>();
        vehicles = new ArrayList<>();
        services = new ArrayList<>();
        parts = new HashMap<>();
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

    public boolean updateServiceStatus(String serviceCode, String newStatus) {
        for (Service service : services) {
            if (service.getCode().equals(serviceCode)) {
                service.setStatus(newStatus);
                return true;
            }
        }
        return false;
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
                String[] credentials = line.split(":"+"\n");
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


    public void readDataFromFile() {
        try {
            File file = new File("dados_apl.dat");
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                // Lógica para ler os objetos do arquivo

                objectInputStream.close();
                System.out.println("Dados lidos com sucesso.");
            } else {
                System.out.println("O arquivo de dados não está disponível.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataToFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("dados_apl.dat");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            // Lógica para escrever os objetos no arquivo

            objectOutputStream.close();
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void createVehicle(int client, String licensePlate, String brand, String model, int year, String chassisNumber) {
        Vehicle newVehicle = new Vehicle(client, licensePlate, brand, model, year, chassisNumber);
        vehicles.add(newVehicle);
        System.out.println("Novo veículo criado com sucesso!");
    }

    public boolean deleteVehicleByLicensePlate(String licensePlate) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getLicensePlate().equals(licensePlate)) {
                vehicles.remove(vehicle);
                System.out.println("Veículo eliminado com sucesso!");
                return true;
            }
        }
        System.out.println("Veículo não encontrado. Verifique a matrícula e tente novamente.");
        return false;
    }

    // Métodos para gestão dos veículos
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    // Métodos para gestão dos serviços
    public void addService(Service service) {
        services.add(service);
    }

    public List<Service> getServices() {
        return services;
    }

    @Override
    public String toString() {
        return "Garage{" +
                "users=" + users +
                ", vehicles=" + vehicles +
                ", services=" + services +
                ", parts=" + parts +
                '}';
    }
}

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



    public boolean updateServiceStatus(String serviceCode, String newStatus) {
        for (Service service : services) {
            if (service.getCode().equals(serviceCode)) {
                service.setStatus(newStatus);
                return true;
            }
        }
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

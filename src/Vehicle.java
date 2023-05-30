
import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private int client = 202111;
    private String licensePlate;
    private String brand;
    private String model;
    private int year;
    private String chassisNumber;
    private List<Service> services;

    public Vehicle(int client, String licensePlate, String brand, String model, int year, String chassisNumber) {
        this.client = client;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.chassisNumber = chassisNumber;
        this.services = new ArrayList<>();
    }
    public Vehicle() {
        this.client = client;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.chassisNumber = chassisNumber;
        this.services = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "client='" + client + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", chassisNumber='" + chassisNumber + '\'' +
                ", services=" + services +
                '}';
    }

    // Getters e setters


    public int getClient() {
        return client++;
    }

    public void setClient(int client) {
        this.client = client;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void addService(Service service) {
        services.add(service);
    }
}


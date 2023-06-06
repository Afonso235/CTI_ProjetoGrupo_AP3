import java.util.List;

public class GereVehicle {
    private List<Vehicle> vehicles;


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

}

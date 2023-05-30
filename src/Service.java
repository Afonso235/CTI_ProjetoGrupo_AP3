import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Service {
    private String code;
    private String mechanic;
    private List<String> mechanics;
    private Date date;
    private double hoursSpent;
    private double repairCost;
    private String description;
    private List<Part> usedParts;
    private String type;
    private String status;

    public Service(String code, String mechanic, Date date, double hoursSpent, double repairCost, String description,
                   List<Part> usedParts, String type, String status) {
        this.code = code;
        this.mechanic = mechanic;
        this.mechanics = new ArrayList<>();
        this.date = date;
        this.hoursSpent = hoursSpent;
        this.repairCost = repairCost;
        this.description = description;
        this.usedParts = usedParts;
        this.type = type;
        this.status = status;
    }

    public static void createService(Scanner scanner, Garage garage) {
        System.out.print("Código: \n");
        String code = scanner.nextLine();
        System.out.print("Mecânico Responsável: ");
        String mechanic = scanner.nextLine();
        System.out.print("Data (formato AAAAMMDD): ");
        String dateString = scanner.nextLine();
        System.out.print("Tempo despendido (horas): ");
        double hoursSpent = scanner.nextDouble();
        System.out.print("Custo da reparação: ");
        double repairCost = scanner.nextDouble();
        scanner.nextLine(); // Limpar a nova linha pendente
        System.out.print("Descrição: ");
        String description = scanner.nextLine();

        List<Part> usedParts = getUsedParts(scanner); // Lógica para obter a lista de peças usadas

        System.out.print("Tipo: ");
        String type = scanner.nextLine();
        System.out.print("Estado: ");
        String status = scanner.nextLine();

        Date date = parseDate(dateString); // Função para converter a string de data para objeto Date

        Service newService = new Service(code, mechanic, date, hoursSpent, repairCost, description, usedParts, type, status);
        garage.addService(newService);
    }

    private static List<Part> getUsedParts(Scanner scanner) {
        List<Part> usedParts = new ArrayList<>();

        boolean done = false;
        while (!done) {
            System.out.println("Adicionar peça usada:");
            System.out.print("Código da peça: ");
            String code = scanner.nextLine();
            System.out.print("Nome da peça: ");
            String name = scanner.nextLine();
            System.out.print("Marca da peça: ");
            String brand = scanner.nextLine();
            System.out.print("Categoria da peça: ");
            String category = scanner.nextLine();
            System.out.print("Quantidade em estoque: ");
            int stockQuantity = scanner.nextInt();
            System.out.print("Preço da peça: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Limpar a nova linha pendente
            System.out.print("Fornecedor da peça: ");
            String supplier = scanner.nextLine();

            Part usedPart = new Part(code, name, brand, category, stockQuantity, price, supplier);
            usedParts.add(usedPart);

            System.out.print("Deseja adicionar mais peças usadas? (s/n): ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("n")) {
                done = true;
            }
        }

        return usedParts;
    }


    private static Date parseDate(String dateString) {
        Date parsedDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            parsedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Utilize o formato AAAAMMDD.");
        }
        return parsedDate;
    }

    public static void updateServiceStatus(Scanner scanner, Garage garage) {
        System.out.print("Código do serviço: ");
        String code = scanner.nextLine();
        System.out.print("Novo estado: ");
        String newStatus = scanner.nextLine();

        boolean success = garage.updateServiceStatus(code, newStatus);
        if (success) {
            System.out.println("Estado do serviço atualizado com sucesso!");
        } else {
            System.out.println("Serviço não encontrado. Verifique o código e tente novamente.");
        }
    }

    @Override
    public String toString() {
        return "Service{" +
                "code='" + code + '\'' +
                ", mechanic='" + mechanic + '\'' +
                ", mechanics=" + mechanics +
                ", date=" + date +
                ", hoursSpent=" + hoursSpent +
                ", repairCost=" + repairCost +
                ", description='" + description + '\'' +
                ", usedParts=" + usedParts +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    // Getters e setters

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMechanic() {
        return mechanic;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
    }

    public List<String> getMechanics() {
        return mechanics;
    }

    public void setMechanics(List<String> mechanics) {
        this.mechanics = mechanics;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(double hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

    public double getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(double repairCost) {
        this.repairCost = repairCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Part> getUsedParts() {
        return usedParts;
    }

    public void setUsedParts(List<Part> usedParts) {
        this.usedParts = usedParts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

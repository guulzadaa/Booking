package models;

public class Package {
    private String name;
    private double price;
    private String[] services;

    public Package(String name, double price, String[] services) {
        this.name = name;
        this.price = price;
        this.services = services;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void displayInfo() {
        System.out.println("models.Package: " + name);
        System.out.printf("Price per night: $%.2f\n", price);
        System.out.println("Included Services:");
        for (String service : services) {
            System.out.println("  - " + service);
        }
    }

    public static void main(String[] args) {
        Package bronzePackage = new Package(
                "Bronze",
                5000,
                new String[]{
                        "Standard room",
                        "Free Wi-Fi",
                        "Complimentary breakfast"
                }
        );

        Package silverPackage = new Package(
                "Silver",
                8000,
                new String[]{
                        "Deluxe room",
                        "Free Wi-Fi",
                        "Complimentary breakfast",
                        "Access to the fitness center",
                        "Late check-out (2 PM)"
                }
        );

        Package goldPackage = new Package(
                "Gold",
                10000,
                new String[]{
                        "Suite room",
                        "Free Wi-Fi",
                        "Complimentary breakfast",
                        "Access to the fitness center",
                        "Late check-out (4 PM)",
                        "Personal concierge service",
                        "Airport shuttle service"
                }
        );

        System.out.println("Available Hotel Packages:\n");
        bronzePackage.displayInfo();
        System.out.println("\n---\n");
        silverPackage.displayInfo();
        System.out.println("\n---\n");
        goldPackage.displayInfo();
    }
}


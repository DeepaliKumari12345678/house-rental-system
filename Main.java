import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

    class House {
        private String City;
        private String Location;
        private String houseNo;
        private double basePricePerDay;
        private double basePricePerMonth;
        private boolean isAvailable;

        public House(String City, String Location, String houseNo, double basePricePerMonth ,double basePricePerDay) {
            this.City = City;
            this.Location = Location;
            this.houseNo = houseNo;
            this.basePricePerMonth = basePricePerMonth;
            this.basePricePerDay = basePricePerDay;
            this.isAvailable = true;
        }
        public String getCity() {
            return City;
        }
        public String gethouseNo() {
            return houseNo;
        }


        public String getLocation() {
            return Location;
        }

        public double calculatePriceDay(int rentalDays) {
            return basePricePerDay * rentalDays;
        }

        public double calculatePriceMonth(int rentalMonths) {
            return basePricePerMonth * rentalMonths;
        }
        public boolean isAvailable() {
            return isAvailable;
        }

        public void rent() {
            isAvailable = true;
        }


        public void returnHouse() {
            isAvailable = true;
        }
    }

    class Customer {
        private String customerId;
        private String name;

        public Customer(String customerId, String name) {
            this.customerId = customerId;
            this.name = name;
        }

        public String getCustomerId() {
            System.out.println("Enter Your Aadhar Id");
            return customerId;
        }

        public String getName() {
            System.out.println("Enter Your Full Name");
            return name;
        }
    }

    class Rental {
        private House house;
        private Customer customer;
        private int houseNo;
        private int days;
        private int months;

        public Rental(House house, Customer customer, int days , int months) {
            this.house = house;
            this.customer = customer;

            this.days = days;
            this.months = months;
        }

        public House getHouse() {
            return house;
        }

        public Customer getCustomer() {
            return customer;
        }


        public int getDays() {
            return days;
        }
        public int getMonths() {
            return months;
        }
    }

    class HouseRentalSystem {
        private List<House> houses;
        private List<Customer> customers;
        private List<Rental> rentals;

        public HouseRentalSystem() {
            houses = new ArrayList<>();
            customers = new ArrayList<>();
            rentals = new ArrayList<>();
        }

        public void addCar(House house) {
            houses.add(house);
        }

        public void addCustomer(Customer customer) {
            customers.add(customer);
        }

        public void rentHouse(House house, Customer customer, int days , int months) {
            if (house.isAvailable()) {
                house.rent();
                rentals.add(new Rental(house, customer, days , months));

            } else {
                System.out.println("house is not available for rent.");
            }
        }

        public void returnHouse(House house) {
            house.returnHouse();
            Rental rentalToRemove = null;
            for (Rental rental : rentals) {
                if (rental.getHouse() == house) {
                    rentalToRemove = rental;
                    break;
                }
            }
            if (rentalToRemove != null) {
                rentals.remove(rentalToRemove);

            } else {
                System.out.println("House was not rented.");
            }
        }

        public void menu() {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("===== House Rental System =====");
                System.out.println("1. Rent a House");
                System.out.println("2. Return  a House");
                System.out.println("3. Leave a House");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 1) {
                    System.out.println("\n== Rent a House ==\n");
                    System.out.print("Enter your name: ");
                    String customerName = scanner.nextLine();

                    System.out.println("\nAvailable Houses:");
                    for (House house : houses) {
                        if (house.isAvailable()) {
                            System.out.println(house.gethouseNo() + " - " + house.getCity() + " " + house.getLocation());
                        }
                    }

                    System.out.print("\nEnter the House No. you want to rent: ");
                    String carId = scanner.nextLine();

                    System.out.print("Enter the number of days for rental: ");
                    int rentalDays = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter the number of months for rental: ");
                    int rentalMonths = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter your house number: ");
                    String houseNo = scanner.next();

                    Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                    addCustomer(newCustomer);

                    House selectedHouse = null;
                    for (House house : houses) {
                        if (house.gethouseNo().equals(houseNo) && house.isAvailable()){
                                selectedHouse = house;
                                break;
                            }
                    }

                    if (selectedHouse != null) {
                        double totalPrice = selectedHouse.calculatePriceDay(rentalDays);
                        double monthTotalPrice = selectedHouse.calculatePriceMonth(rentalMonths);
                        System.out.println("\n== Rental Information ==\n");
                        System.out.println("Customer ID: " + newCustomer.getCustomerId());
                        System.out.println("Customer Name: " + newCustomer.getName());
                        System.out.println("House: " + " " + selectedHouse.getCity() + selectedHouse.gethouseNo() + " " + selectedHouse.getLocation());
                        System.out.println("Rental Days: " + rentalDays);
                        System.out.println("Rental Days: " + rentalMonths);
                        System.out.printf("Total Price: $%.2f%n", totalPrice);
                        System.out.printf("Total Price: $%.5f%n", monthTotalPrice);

                        System.out.print("\nConfirm rental (Y/N): ");
                        String confirm = scanner.nextLine();

                        if (confirm.equalsIgnoreCase("Y")) {
                            rentHouse(selectedHouse, newCustomer, rentalDays, rentalMonths);
                            System.out.println("\nHouse rented successfully.");
                            break;
                        } else if (confirm == "N") {
                            System.out.println("\nRental canceled.");
                            break;
                        } else {
                            System.out.println("\nInvalid House selection or House not available for rent.");
                            break;
                        }
                    }} else if (choice == 2) {
                    System.out.println("\n== Return House ==\n");
                    System.out.print("Enter the House No you want to return: ");
                    String houseNo = scanner.nextLine();

                    House houseToReturn = null;
                    for (House house : houses) {
                        if (house.gethouseNo().equals(houseNo) && !house.isAvailable()) {
                            houseToReturn = house;
                            break;
                        }
                    }

                    if (houseToReturn != null) {
                        Customer customer = null;
                        for (Rental rental : rentals) {
                            if (rental.getHouse() == houseToReturn) {
                                customer = rental.getCustomer();
                                break;
                            }
                        }

                        if (customer != null) {
                            returnHouse(houseToReturn);
                            System.out.println("House returned successfully by " + customer.getName());
                        } else {
                            System.out.println("House was not rented or rental information is missing.");
                        }
                    } else {
                        System.out.println("Invalid House Number or House is not rented.");
                    }
                } else if (choice == 3) {
                    System.out.println("thankyou you ");
                }
                else if (choice==4){
                    break;}
                 else {
                    System.out.println("Invalid choice. Please enter a valid option.");
                }
            }

            System.out.println("\nThank you for using the House Rental System!");
        }

    }
    public class Main{
        public static void main(String[] args) {
            HouseRentalSystem rentalSystem = new HouseRentalSystem();

            House house1 = new House("Delhi", "Laxminagar", "001BHK", 5000.0, 500); // Different base price per day for each car
            House house2 = new House("Haryana", "Panipat", "002BHK", 3500.0,300);
            House house3 = new House("Mumbai", "Lokhandwala", "003BHK", 10000.0,800);
            rentalSystem.addCar(house1);
            rentalSystem.addCar(house2);
            rentalSystem.addCar(house3);

            rentalSystem.menu();
        }
    }


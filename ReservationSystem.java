import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ReservationSystem {
    private List<Train> trainList = new ArrayList<>();
    private ArrayList<Customer> customerList = new ArrayList<>();
    private String filePath = "/D:/Nirma University/Second Year/Semester 3/OOP/Assingment/Train Reservatinon System/Train Routes";

    // read file
    public void readTrains() {
        String line = "";
        String splitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] route = line.split(splitBy);
                Train train = new Train(
                        route[0],
                        route[1],
                        route[2],
                        route[3],
                        route[4],
                        route[5],
                        Integer.parseInt(route[6]));
                trainList.add(train);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // update file
    public void updataInfo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Write the header
            bw.write("TrainNumber,TrainName,Source,Destination,DepartureTime,ArrivalTime,SeatsAvailable\n");
            // Write each train's updated data
            for (Train train : trainList) {
                bw.write(train.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // show trains
    public void displayTrains() {
        System.out.println("Available Train Routes:");
        for (Train train : trainList) {
            System.out.println(train.getTrainNumber() + " - " + train.getTrainName() +
                    " from " + train.getSource() + " to " + train.getDestination() + " at " +
                    " | Seats Available: " + train.getSeatsAvailable());
        }
    }

    // book tickets
    public void bookTicket(String trainNumber, int seats, String date, String customerName) {
        for (Train train : trainList) {
            if (train.getTrainNumber().equals(trainNumber)) {
                if (seats > train.getSeatsAvailable()) {
                    System.out.println("seats exceed available seats.");
                    System.out.println("Available seats on Train " + trainNumber + ": " + train.getSeatsAvailable());
                    return;
                }

                if (train.bookSeats(seats)) {
                    Customer customer = new Customer(customerName);
                    customer.bookTickets(seats);
                    customerList.add(customer);

                    System.out.println(
                            "Successfully booked " + seats + " seats on Train " + trainNumber + " for " + date);
                    System.out.println("Customer ID: " + customer.getCustomerID());

                    // receipt
                    Receipt receipt = new Receipt("Booking", train, seats, date, train.getSeatsAvailable(),
                            customer.getCustomerID());
                    receipt.generateReceipt();
                } else {
                    System.out.println("Not enough seats available.");
                }
                return;
            }
        }
        System.out.println("Train not found.");
    }

    // cancel tickets
    public void cancelTicket(String trainNumber, String customerID, int seats) {
        for (Train train : trainList) {
            if (train.getTrainNumber().equals(trainNumber)) {
                for (Customer customer : customerList) {
                    if (customer.getCustomerID().equals(customerID)) {
                        if (seats > customer.getBookedTickets()) {
                            System.out.println("trying to cancel more seats than you have booked.");
                            return;
                        }

                        train.cancelSeats(seats);
                        customer.cancelTickets(seats);
                        System.out.println("Successfully canceled " + seats + " seats for Customer ID: " + customerID);
                        System.out.println("Total remaining booked tickets: " + customer.getBookedTickets());

                        // receipt
                        Receipt receipt = new Receipt("Cancellation", train, seats, "N/A", train.getSeatsAvailable(),
                                customerID);
                        receipt.generateReceipt();
                        return;
                    }
                }
                System.out.println("Customer ID not found.");
                return;
            }
        }
        System.out.println("Train not found.");
    }

    public void viewAllCustomers() {
        if (customerList.isEmpty()) {
            System.out.println("No customers have booked tickets yet.");
        } else {
            System.out.println("List of Customers who booked tickets:");
            for (Customer customer : customerList) {
                System.out.println("Customer ID: " + customer.getCustomerID());
                System.out.println("Name: " + customer.getName());
                System.out.println("Booked Tickets: " + customer.getBookedTickets());
                System.out.println("----------------------------------");
            }
        }
    }

    public static void main(String[] args) {
        ReservationSystem t1 = new ReservationSystem();
        t1.readTrains();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            try {

                System.out.println("\n+------------------------------+");
                System.out.println("|  Train Ticket Reservation    |");
                System.out.println("+------------------------------+");
                System.out.printf("| %-3s | %-25s |\n", "1", "View Available Trains");
                System.out.printf("| %-3s | %-25s |\n", "2", "Book Tickets");
                System.out.printf("| %-3s | %-25s |\n", "3", "Cancel Tickets");
                System.out.printf("| %-3s | %-25s |\n", "4", "View All Customers");
                System.out.printf("| %-3s | %-25s |\n", "5", "Exit");
                System.out.println("+------------------------------+");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        t1.displayTrains(); // Display available trains and seats
                        break;
                    case 2:
                        try {
                            System.out.print("Enter Customer Name: ");
                            String customerName = scanner.nextLine();
                            System.out.print("Enter Train Number: ");
                            String trainNumber = scanner.nextLine();
                            System.out.print("Enter Departure Date (YYYY-MM-DD): ");
                            String date = scanner.nextLine();
                            System.out.print("Enter number of seats to book: ");
                            int seatsToBook = scanner.nextInt();
                            scanner.nextLine(); // Consume newline character
                            t1.bookTicket(trainNumber, seatsToBook, date, customerName);

                            t1.updataInfo();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input! Please enter a valid input.");
                            scanner.nextLine(); // Clear buffer
                        } catch (Exception e) {
                            System.out.println("An error occurred: " + e.getMessage());
                        }
                        break;
                    case 3:
                        try {
                            System.out.print("Enter Train Number: ");
                            String cancelTrainNumber = scanner.nextLine();
                            System.out.print("Enter Customer ID: ");
                            String customerID = scanner.nextLine();
                            System.out.print("Enter number of seats to cancel: ");
                            int seatsToCancel = scanner.nextInt();
                            scanner.nextLine();
                            t1.cancelTicket(cancelTrainNumber, customerID, seatsToCancel);

                            t1.updataInfo();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input! Please enter a valid input.");
                            scanner.nextLine();
                        } catch (Exception e) {
                            System.out.println("An error occurred: " + e.getMessage());
                        }
                        break;

                    case 4:
                        t1.viewAllCustomers();
                        break;

                    case 5:
                        exit = true;
                        break;

                    default:
                        System.out.println("Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid input.");
                scanner.nextLine();
            }

        }
        scanner.close();
    }
}
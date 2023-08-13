import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FoodieFaveQueueManagementSystem queueManagementSystem = new FoodieFaveQueueManagementSystem();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n----Welcome To Foodies Fave Food Center----");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("100 or VFQ: View all Queues");
            System.out.println("101 or VEQ: View all Empty Queues");
            System.out.println("102 or ACQ: Add customer to a Queue");
            System.out.println("103 or RCQ: Remove a customer from a Queue");
            System.out.println("104 or PCQ: Remove a served customer");
            System.out.println("105 or VCS: View Customers Sorted in alphabetical order");
            System.out.println("106 or SPD: Store Program Data into file");
            System.out.println("107 or LPD: Load Program Data from file");
            System.out.println("108 or STK: View Remaining burgers Stock");
            System.out.println("109 or AFS: Add burgers to Stock");
            System.out.println("110 or IF: Print the income of each queue");
            System.out.println("999 or EXT: Exit the Program");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice.toLowerCase()) {
                case "100":
                case "vfq":
                    queueManagementSystem.viewAllQueues();
                    break;
                case "101":
                case "veq":
                    queueManagementSystem.viewAllEmptyQueues();
                    break;
                case "102":
                case "acq":
                    System.out.print("Enter first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter last name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Enter number of burgers required: ");
                    int burgersRequired = Integer.parseInt(scanner.nextLine());
                    queueManagementSystem.addCustomerToQueue(firstName, lastName, burgersRequired);
                    break;
                case "103":
                case "rcq":
                    System.out.print("Enter queue number: ");
                    int queueNumber = Integer.parseInt(scanner.nextLine());
                    System.out.println("Queue 1 position from 1-2");
                    System.out.println("Queue 2 position from 1-3");
                    System.out.println("Queue 3 position from 1-5");
                    System.out.print("Enter position: ");
                    int position = Integer.parseInt(scanner.nextLine()) - 1;
                    queueManagementSystem.removeCustomerFromQueue(queueNumber, position);
                    System.out.println();
                    break;
                case "104":
                case "pcq":
                    queueManagementSystem.removeServedCustomer();
                    break;
                case "105":
                case "vcs":
                    queueManagementSystem.viewCustomersSortedAlphabetically();
                    break;
                case "106":
                case "spd":
                    queueManagementSystem.storeProgramData();
                    break;
                case "107":
                case "lpd":
                    queueManagementSystem.loadProgramData();
                    break;
                case "108":
                case "stk":
                    queueManagementSystem.viewRemainingBurgersStock();
                    break;
                case "109":
                case "afs":
                    System.out.print("Enter quantity of burgers to add: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    queueManagementSystem.addBurgersToStock(quantity);
                    break;
                case "110":
                case "if":
                    queueManagementSystem.printIncomeOfEachQueue();
                    break;
                case "999":
                case "ext":
                    System.out.println("Exiting the program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

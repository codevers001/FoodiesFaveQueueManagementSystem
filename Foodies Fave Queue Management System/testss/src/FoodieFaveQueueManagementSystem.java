import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class FoodieFaveQueueManagementSystem {
    private static final int MAX_QUEUE_SIZE_1 = 2;
    private static final int MAX_QUEUE_SIZE_2 = 3;
    private static final int MAX_QUEUE_SIZE_3 = 5;
    private static final int BURGERS_STOCK = 50;
    private static final int BURGER_PRICE = 650;

    private List<FoodQueue> queue1;
    private List<FoodQueue> queue2;
    private List<FoodQueue> queue3;
    private WaitingListQueue waitingListQueue;
    private int burgersRemaining;

    public FoodieFaveQueueManagementSystem() {
        queue1 = new ArrayList<>();
        queue2 = new ArrayList<>();
        queue3 = new ArrayList<>();
        waitingListQueue = new WaitingListQueue();
        burgersRemaining = BURGERS_STOCK;
    }

    public void viewAllQueues() {
        System.out.println("\n*******************");
        System.out.println("*    Cashiers    *");
        System.out.println("*******************");
        printQueue(queue1, 2);
        printQueue(queue2, 3);
        printQueue(queue3, 5);
    }

    private void printQueue(List<FoodQueue> queue, int maxQueueSize) {
        for (int i = 0; i < maxQueueSize; i++) {
            if (i < queue.size()) {
                System.out.print("O ");
            } else {
                System.out.print("X ");
            }
        }
        System.out.println();
    }
    public void viewAllEmptyQueues() {
        System.out.println("Empty Queues:");
        if (queue1.isEmpty()) {
            System.out.println("Queue 1");
        }
        if (queue2.isEmpty()) {
            System.out.println("Queue 2");
        }
        if (queue3.isEmpty()) {
            System.out.println("Queue 3");
        }
    }

    public void addCustomerToQueue(String firstName, String lastName, int burgersRequired) {
        if (burgersRequired < 1 || burgersRequired > 5) {
            System.out.println("Invalid number of burgers. Please enter a value between 1 and 5.");
            return;
        }
        if (burgersRequired > burgersRemaining) {
            System.out.println("Insufficient stock: " + burgersRequired);
            System.out.println("Customer cannot be added to the queue.");
            System.out.println("Maximum stock: 50");
            return;
        }

        int minQueueSize = Math.min(queue1.size(), Math.min(queue2.size(), queue3.size()));

        if (queue1.size() == minQueueSize) {
            if (queue1.size() < MAX_QUEUE_SIZE_1) {
                queue1.add(new FoodQueue(firstName, lastName, burgersRequired));
                System.out.println("\n" + firstName + " " + lastName + " successfully added to Queue 1.");
            } else if (queue2.size() < MAX_QUEUE_SIZE_2) {
                queue2.add(new FoodQueue(firstName, lastName, burgersRequired));
                System.out.println("\n" + firstName + " " + lastName + " successfully added to Queue 2.");
            } else if (queue3.size() < MAX_QUEUE_SIZE_3) {
                queue3.add(new FoodQueue(firstName, lastName, burgersRequired));
                System.out.println("\n" + firstName + " " + lastName + " successfully added to Queue 3.");
            } else {
                addToWaitingList(firstName, lastName, burgersRequired);
            }
        } else if (queue2.size() == minQueueSize) {
            if (queue2.size() < MAX_QUEUE_SIZE_2) {
                queue2.add(new FoodQueue(firstName, lastName, burgersRequired));
                System.out.println("\n" + firstName + " " + lastName + " successfully added to Queue 2.");
            } else if (queue3.size() < MAX_QUEUE_SIZE_3) {
                queue3.add(new FoodQueue(firstName, lastName, burgersRequired));
                System.out.println("\n" + firstName + " " + lastName + " successfully added to Queue 3.");
            } else {
                addToWaitingList("\n" + firstName, lastName, burgersRequired);
            }
        } else if (queue3.size() == minQueueSize) {
            if (queue3.size() < MAX_QUEUE_SIZE_3) {
                queue3.add(new FoodQueue(firstName, lastName, burgersRequired));
                System.out.println("\n" + firstName + " " + lastName + " successfully added to Queue 3.");
            } else {
                addToWaitingList(firstName, lastName, burgersRequired);
            }
        } else {
            addToWaitingList(firstName, lastName, burgersRequired);
        }
        updateBurgersStock(burgersRequired);
    }


    private void addToWaitingList(String firstName, String lastName, int burgersRequired) {
        FoodQueue foodQueue = new FoodQueue(firstName, lastName, burgersRequired);
        if (waitingListQueue.isFull()) {
            System.out.println("\nAll queues and waiting list are full. Customer cannot be added.");
        } else {
            waitingListQueue.enqueue(foodQueue);
            System.out.println("Added customer to the waiting list.");
        }
    }

    public void removeCustomerFromQueue(int queueNumber, int position) {
        List<FoodQueue> queue = getQueueByNumber(queueNumber);
        if (queue != null) {
            if (position >= 0 && position < queue.size()) {
                FoodQueue removedCustomer = queue.remove(position );
                System.out.println("Removed customer: " + removedCustomer.getFirstName() + " " + removedCustomer.getLastName());
                if (!waitingListQueue.isEmpty()) {
                    FoodQueue nextCustomer = waitingListQueue.dequeue();
                    queue.add(nextCustomer);
                    System.out.println("Next customer in the waiting list added to the queue.");
                }
            } else {
                System.out.println("Invalid position. Cannot remove customer.");
            }
        } else {
            System.out.println("Invalid queue number. Cannot remove customer.");
        }
    }

    public void removeServedCustomer() {
        if (!queue1.isEmpty()) {
            FoodQueue removedCustomer = queue1.remove(0);
            System.out.println("Removed served customer from Queue 1: " + removedCustomer.getFirstName() + " " + removedCustomer.getLastName());
            if (!waitingListQueue.isEmpty()) {
                FoodQueue nextCustomer = waitingListQueue.dequeue();
                queue1.add(nextCustomer);
                System.out.println("Next customer in the waiting list added to Queue 1." + nextCustomer);
            }
        } else if (!queue2.isEmpty()) {
            FoodQueue removedCustomer = queue2.remove(0);
            System.out.println("Removed served customer from Queue 2: " + removedCustomer.getFirstName() + " " + removedCustomer.getLastName());
            if (!waitingListQueue.isEmpty()) {
                FoodQueue nextCustomer = waitingListQueue.dequeue();
                queue2.add(nextCustomer);
                System.out.println("Next customer in the waiting list added to Queue 2:" + nextCustomer);
            }
        } else if (!queue3.isEmpty()) {
            FoodQueue removedCustomer = queue3.remove(0);
            System.out.println("Removed served customer from Queue 3: " + removedCustomer.getFirstName() + " " + removedCustomer.getLastName());
            if (!waitingListQueue.isEmpty()) {
                FoodQueue nextCustomer = waitingListQueue.dequeue();
                queue3.add(nextCustomer);
                System.out.println("Next customer in the waiting list added to Queue 3." + nextCustomer);
            }
        } else {
            System.out.println("All queues are empty. Cannot remove served customer.");
        }
    }

    public void viewCustomersSortedAlphabetically() {
        List<Customer> customers = new ArrayList<>();
        for (FoodQueue foodQueue : queue1) {
            customers.add(new Customer(foodQueue.getFirstName(), foodQueue.getLastName()));
        }
        for (FoodQueue foodQueue : queue2) {
            customers.add(new Customer(foodQueue.getFirstName(), foodQueue.getLastName()));
        }
        for (FoodQueue foodQueue : queue3) {
            customers.add(new Customer(foodQueue.getFirstName(), foodQueue.getLastName()));
        }
        customers.sort((c1, c2) -> c1.getLastName().compareToIgnoreCase(c2.getLastName()));
        System.out.println("Customers sorted alphabetically:");
        for (Customer customer : customers) {
            System.out.println(customer.getFirstName() + " " + customer.getLastName());
        }
    }


    public void storeProgramData() {
        try {
            File file = new File("FoodieFave.txt");
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (FoodQueue foodQueue : queue1) {
                String line = "\nFrom QUEUE 1  \nFirstname: " + foodQueue.getFirstName() + " Lastname: " + foodQueue.getLastName() + " Burgers Required " + foodQueue.getBurgersRequired();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            for (FoodQueue foodQueue : queue2) {
                String line = "\nFrom QUEUE 2  \nFirstname: " + foodQueue.getFirstName() + " Lastname: " + foodQueue.getLastName() + " Burgers Required " + foodQueue.getBurgersRequired();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            for (FoodQueue foodQueue : queue3) {
                String line = "\nFrom QUEUE 3 \nFirstname: " + foodQueue.getFirstName() + " Lastname: " + foodQueue.getLastName() + " Burgers Required " + foodQueue.getBurgersRequired();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            System.out.println("Successfully wrote the customer data to the file (FoodieFave.txt).");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the file.");
            e.printStackTrace();
        }
    }



    /**
     * Loads the program data from a file.
     */
    public void loadProgramData() {
        try {
            File file = new File("FoodieFave.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            bufferedReader.close();
            System.out.println("Data loaded from the file (FoodieFave.txt).");
        } catch (IOException e) {
            System.out.println("Error: File not found.");
            e.printStackTrace();
        }
    }




    public void viewRemainingBurgersStock() {
        System.out.println("Remaining burgers stock: " + burgersRemaining);
    }

    public void addBurgersToStock(int quantity) {
        if (burgersRemaining + quantity <= BURGERS_STOCK) {
            burgersRemaining += quantity;
            System.out.println(quantity + " burgers added to stock. Total burgers: " + burgersRemaining);
        } else {
            System.out.println("Cannot add burgers. Maximum stock limit is 50.");
            System.out.println("Current Burger Stock : " + burgersRemaining);
        }
    }


    private void updateBurgersStock(int burgersRequired) {
        if (burgersRemaining - burgersRequired >= 0) {
            burgersRemaining -= burgersRequired;
            if (burgersRemaining <= 10) {
                System.out.println("Warning: Low burgers stock. Remaining burgers: " + burgersRemaining);
            }
        } else {
            System.out.println(" Insufficient burgers stock:" + burgersRequired);
            System.out.println(" Customer cannot be added to the queue");
        }
    }

    private List<FoodQueue> getQueueByNumber(int queueNumber) {
        switch (queueNumber) {
            case 1:
                return queue1;
            case 2:
                return queue2;
            case 3:
                return queue3;
            default:
                return null;
        }
    }

    public void printIncomeOfEachQueue() {
        int incomeQueue1 = calculateQueueIncome(queue1);
        int incomeQueue2 = calculateQueueIncome(queue2);
        int incomeQueue3 = calculateQueueIncome(queue3);

        System.out.println("Income of each queue:");
        System.out.println("Queue 1: " + incomeQueue1);
        System.out.println("Queue 2: " + incomeQueue2);
        System.out.println("Queue 3: " + incomeQueue3);
    }

    private int calculateQueueIncome(List<FoodQueue> queue) {
        int totalBurgersRequired = 0;
        for (FoodQueue foodQueue : queue) {
            totalBurgersRequired += foodQueue.getBurgersRequired();
        }
        return totalBurgersRequired * BURGER_PRICE;
    }
}

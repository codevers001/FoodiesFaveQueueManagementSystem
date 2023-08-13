class WaitingListQueue {
    private static final int MAX_SIZE = 5;

    private FoodQueue[] queue;
    private int front;
    private int rear;
    private int size;

    public WaitingListQueue() {
        queue = new FoodQueue[MAX_SIZE];
        front = 0;
        rear = -1;
        size = 0;
    }

    public boolean isFull() {
        return size == MAX_SIZE;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(FoodQueue foodQueue) {
        if (isFull()) {
            System.out.println("Waiting list is full. Customer cannot be added.");
            return;
        }
        rear = (rear + 1) % MAX_SIZE;
        queue[rear] = foodQueue;
        size++;
    }

    public FoodQueue dequeue() {
        if (isEmpty()) {
            System.out.println("Waiting list is empty. No customer to remove.");
            return null;
        }
        FoodQueue removedCustomer = queue[front];
        front = (front + 1) % MAX_SIZE;
        size--;
        return removedCustomer;
    }
}

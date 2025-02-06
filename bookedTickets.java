public class bookedTickets {
    private String name;
    private int customerId;
    private int bookedTickets;
    
    public bookedTickets(String name, int customerId, int bookedTickets) {
        this.name = name;
        this.customerId = customerId;
        this.bookedTickets = bookedTickets;
    }

    public String getName() {
        return name;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getBookedTickets() {
        return bookedTickets;
    }
}

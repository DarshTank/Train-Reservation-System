import java.util.Random;

class Customer {
   private String customerID = this.generateUniqueID();
   private String name;
   private int bookedTickets;

   public Customer(String name) {
      this.name = name;
      this.bookedTickets = 0;
   }

   private String generateUniqueID() {
      int randomFourDigits = new Random().nextInt(10000); // Generates a random number from 0 to 9999
      return "2024-" + String.format("%04d", randomFourDigits);
      // return UUID.randomUUID().toString();
   }

   public String getCustomerID() {
      return this.customerID;
   }

   public String getName() {
      return this.name;
   }

   public int getBookedTickets() {
      return this.bookedTickets;
   }

   public void bookTickets(int numTickets) {
      this.bookedTickets += numTickets;
   }

   public void cancelTickets(int numTickets) {
      this.bookedTickets -= numTickets;
   }
}

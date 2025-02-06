import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Receipt {
   private String transactionType;
   private Train train;
   private int seats;
   private String date;
   private int remainingSeats;
   private String customerID;

   public Receipt(String transactionType, Train train, int seats, String date, int remainingSeats, String customerID) {
      this.transactionType = transactionType;
      this.train = train;
      this.seats = seats;
      this.date = date;
      this.remainingSeats = remainingSeats;
      this.customerID = customerID;
   }

   public void generateReceipt() {
      LocalDateTime now = LocalDateTime.now();
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      String timestamp = dtf.format(now);
      String receiptContent = "\n===== Ticket Reservation System - Receipt =====\n";
      receiptContent = receiptContent + "Transaction Type: " + this.transactionType + "\n";
      receiptContent = receiptContent + "Customer ID: " + this.customerID + "\n";
      receiptContent = receiptContent + "Train: " + this.train.getTrainName() + " (" + this.train.getTrainNumber() + ")\n";
      receiptContent = receiptContent + "From: " + this.train.getSource() + " To: " + this.train.getDestination() + "\n";
      receiptContent = receiptContent + "Departure Date: " + this.date + "\n";
      receiptContent = receiptContent + "Seats: " + this.seats + "\n";
      receiptContent = receiptContent + "Remaining Seats: " + this.remainingSeats + "\n";
      receiptContent = receiptContent + "Transaction Time: " + timestamp + "\n";
      receiptContent = receiptContent + "===============================================\n";
      System.out.println(receiptContent);
      this.saveReceiptToFile(receiptContent);
   }

   private void saveReceiptToFile(String content) {
      String var10000 = this.train.getTrainNumber();
      String transactionKeyword = this.transactionType.equalsIgnoreCase("Cancellation") ? "Cancel" : "Booking";
      String fileName = "receipt_" + transactionKeyword + "_" + var10000 + "_" + System.currentTimeMillis() + ".txt";

      try {
         FileWriter writer = new FileWriter(fileName);

         try {
            writer.write(content);
            System.out.println("Receipt saved to: " + fileName);
         } catch (Throwable var7) {
            try {
               writer.close();
            } catch (Throwable var6) {
               var7.addSuppressed(var6);
            }

            throw var7;
         }

         writer.close();
      } catch (IOException var8) {
         System.out.println("Error saving receipt: " + var8.getMessage());
      }

   }
}

class Train {
    private String trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int seatsAvailable;
 
    public Train(String trainNumber, String trainName, String source, String destination, String departureTime, String arrivalTime, int seatsAvailable) {
       this.trainNumber = trainNumber;
       this.trainName = trainName;
       this.source = source;
       this.destination = destination;
       this.departureTime = departureTime;
       this.arrivalTime = arrivalTime;
       this.seatsAvailable = seatsAvailable;
    }
 
    public String getTrainNumber() {
       return this.trainNumber;
    }
 
    public String getTrainName() {
       return this.trainName;
    }
 
    public String getSource() {
       return this.source;
    }
 
    public String getDestination() {
       return this.destination;
    }
 
    public int getSeatsAvailable() {
       return this.seatsAvailable;
    }
 
    public boolean bookSeats(int seats) {
       if (this.seatsAvailable >= seats) {
          this.seatsAvailable -= seats;
          return true;
       } else {
          return false;
       }
    }
 
    public void cancelSeats(int seats) {
       this.seatsAvailable += seats;
    }
 
    public String toString() {
       return this.trainNumber + "," + this.trainName + "," + this.source + "," + this.destination + "," + this.departureTime + "," + this.arrivalTime + "," + this.seatsAvailable;
    }
 }
 
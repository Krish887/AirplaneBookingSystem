//Krish Patel
//501018374

import java.util.ArrayList;
import java.util.TreeMap;

/*
 *  Class to model an airline flight. In this simple system, all flights originate from Toronto
 *
 *  This class models a simple flight that has only economy seats
 */
public class Flight {

    public enum Status {
        DELAYED, ONTIME, ARRIVED, INFLIGHT
    }

    ;

    String flightNum;
    String airline;
    String origin, dest;
    String departureTime;
    Status status; // see enum Status above. google this to see how to use it
    int flightDuration;
    Aircraft aircraft;
    protected int passengers; // count of (economy) passengers on this flight - initially 0
    String passengerName;
    String passportNum;

    protected ArrayList<Passenger> manifest; 
    protected TreeMap<String, Passenger> seatMap;
    private FlightType flightType;

    // write code to initialize instance variables to default values
    public Flight() {
        this.flightNum = null;
        this.airline = null;
        this.dest = null;
        this.origin = "Toronto";
        this.departureTime = null;
        this.flightDuration = 0;
        this.aircraft = null;
        passengers = 0;
        status = null;
        this.passengerName = null;
        this.passportNum = null;
        this.flightType = FlightType.MEDIUMHAUL;
        this.manifest = new ArrayList<Passenger>();
        this.seatMap = new TreeMap<String, Passenger>();
    }

    public Flight(String flightNum, String airline, String dest, String departure, int flightDuration,
                  Aircraft aircraft) {
        this.flightNum = flightNum;
        this.airline = airline;
        this.dest = dest;
        this.origin = "Toronto";
        this.departureTime = departure;
        this.flightDuration = flightDuration;
        this.aircraft = aircraft;
        passengers = 0;
        status = Status.ONTIME;
        // TODO: determine the flighttype from constructor argument probably
        this.flightType = FlightType.MEDIUMHAUL;
        this.manifest = new ArrayList<Passenger>();
        this.seatMap = new TreeMap<String, Passenger>();
    }

    //enum of 3 different types
    public static enum FlightType {
        SHORTHAUL, MEDIUMHAUL, LONGHAUL
    }

    //returns the type of flight
    public FlightType getFlightType() {
        return flightType;

    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public Status getStatus() {
        return status;
    }

    public void printPassengerManifest() { // prints all passengers on flight
        System.out.println(manifest);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(int dur) {
        this.flightDuration = dur;
    }

    public int getPassengers() {
        return passengers;
    }

    public ArrayList<Passenger> getManifest() {
        return manifest;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    // Check to see if there is room on this flight - compare current passenger
    // count
    // with aircraft max capacity of economy seats
    public boolean seatsAvailable() {
        if (passengers < aircraft.getNumSeats())
            return true;
        return false;
    }

    public String[][] getaircraftSeats() {
        return aircraft.seatLayout();
    }

    /*
     * Cancel a seat - essentially reduce the passenger count by 1. Make sure the
     * count does not fall below 0 (see instance variable passenger)
     */
    //cancelSeat() method will cancel the seat of the passenger by getting index value of the passenger in manifest
    //program will check to see if the index is less than 0, which would mean that the passenger does not exist in
    //manifest and it will throw a PassengerNotInManifest Exception
    //program will remove the passenger from manifest and it will remove the seat allocated to that passenger from 
    //the seatMap
    public void cancelSeat(Passenger p) throws PassengerNotInManifestException {
        if (passengers == 0) {
            // TODO: may be throw an Exception
        } else {
            int index = manifest.indexOf(p);
            if (index < 0) //if the Passenger is not found, index will be -1
                throw new PassengerNotInManifestException("Error while cancelSeat");

            manifest.remove(p);
            seatMap.remove(p.getSeat());
            passengers--;
        }
    }

    //reserveSeat() method will check to see if seats are available and will add the passenger to the manifest
    //and add the seat string to the seatMap, if the seatMap already contains that seat
    //then the program will throw a SeatOccupiedException
    //if the passenger already exists in the manifest, then program will throw a DuplicatePassengerException
    public boolean reserveSeat(Passenger p, String seat) throws DuplicatePassengerException, SeatOccupiedException {
        if (seatsAvailable()) {
            passengers++;
            if (seatMap.containsKey(seat))
                throw new SeatOccupiedException(p.toString() + ", " + seat);

            if (manifest.indexOf(p) >= 0) { //if the index of the manifest passenger is greater than or equal to 0, throws duplicatePassengerException
                throw new DuplicatePassengerException(p.toString() + ", " + seat);
            }

            manifest.add(p);
            seatMap.put(seat, p);
            return true;
        }
        return false;
    }

    //getPassengerFromManifest() method will return the passenger object from the manifest 
    public Passenger getPassengerFromManifest(String passengerName, String passPortNum){
        Passenger p = new Passenger(passengerName,passPortNum);
        p = manifest.get(manifest.indexOf(p));
        return p;
    }
    
    //printSeats() method will print the seatLayout of the aircraft
    //if the seatMap contains the seat that the user typed, "XX" will be printed instead of the seat string
    //seatLayout will remain unchanged if the user has not selected a seat
    public void printSeats() {
        String[][] seatLayout = aircraft.seatLayout();
        int rows = seatLayout.length;
        int columns = seatLayout[0].length;

        System.out.println();
        for (int i = 0; i < rows; i++) {
            if (i == 2) {
                System.out.println();
            }
            for (int j = 0; j < columns; j++) {
                if (seatMap.containsKey(seatLayout[i][j])) {
                    System.out.print("XX ");

                } else {
                    System.out.print(seatLayout[i][j] + " ");
                }
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("XX = Occupied   + = First Class"); //label at bottom of seatLayout
        System.out.println();
    }

    public String toString() {
        return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime
                + "\t Duration: " + flightDuration + "\t Status: " + status;

    }

}

//created the exceptions
class PassengerNotInManifestException extends Exception {
    public PassengerNotInManifestException(String errorMessage) { //
        super(errorMessage);
    }
}

class SeatOccupiedException extends Exception {
    public SeatOccupiedException(String errorMessage) { //
        super(errorMessage);
    }
}

class DuplicatePassengerException extends Exception {
    public DuplicatePassengerException(String errorMessage) { //
        super(errorMessage);
    }
}


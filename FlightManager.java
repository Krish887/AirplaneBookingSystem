//Krish Patel
//501018374

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FlightManager {
    // Contains list of Flights departing from Toronto in a single day
    ArrayList<Flight> flights = new ArrayList<Flight>();

    String[] cities = {"Dallas", "New York", "London", "Paris", "Tokyo"};
    final int DALLAS = 0;
    final int NEWYORK = 1;
    final int LONDON = 2;
    final int PARIS = 3;
    final int TOKYO = 4;

    // flight times in hours
    int[] flightTimes = {3, // Dallas
            1, // New York
            7, // London
            8, // Paris
            16// Tokyo
    };

    // Contains list of available airplane types and their seat capacity
    ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();

    String errorMsg = null; // if a method finds an error (e.g. flight number not found) set this string.
    // See video!

    Random random = new Random(); // random number generator - google "Java class Random". Use this in
    // generateFlightNumber

    public FlightManager() throws FileNotFoundException {
        // DO NOT ALTER THIS CODE - THE TA'S WILL USE IT TO TEST YOUR PROGRAM
        // IN ASSIGNMENT 2 YOU WILL BE LOADING THIS INFORMATION FROM A FILE

        // Create some aircraft types with max seat capacities
        airplanes.add(new Aircraft(88, "Boeing 737"));
        airplanes.add(new Aircraft(180, "Airbus 320"));
        airplanes.add(new Aircraft(44, "Dash-8 100"));
        airplanes.add(new Aircraft(12, "Bombardier 5000"));
        airplanes.add(new Aircraft(100, 16, "Boeing 747"));
        File file = new File("flights.txt");
        Scanner scanner = new Scanner(file);
        Flight flight;

        //scanner will go through the flights text file and get the information
        while (scanner.hasNext()) {
            String flightEntry = scanner.nextLine(); 
            String[] flightLine = flightEntry.split("\\s"); 
            String flightName = flightLine[0]; //name of flight
            String dest = flightLine[1]; //destination
            String departure = flightLine[2]; //departure time
            int capacity = Integer.parseInt(flightLine[3]); //capacity
            String[] flightNameSplit = flightName.split("_");
            flightName = String.format("%s %s", flightNameSplit[0], flightNameSplit[1]);
            String flightNum = generateFlightNumber(flightName);
            Aircraft aircraft = fetchAircraftWithNearCapacity(capacity);
            //if the aircraft has 0 first class seats, it is a normal flight
            //otherwise it is a longhaul flight
            if(aircraft.getNumFirstClassSeats()==0)
            flight = new Flight(flightNum, flightName, dest, departure, flightTimes[DALLAS],
                    aircraft);
            else
                flight = new LongHaulFlight(flightNum, flightName,dest, departure,
                        flightTimes[TOKYO], aircraft);
            flights.add(flight);
            flight.setStatus(Flight.Status.DELAYED);
        }
    }

    //gets the total seats of the aircraft and checks to see which aircraft the seats range in and returns 
    //the selected aircraft
    private Aircraft fetchAircraftWithNearCapacity(int capacity) {
        Aircraft selectedAircraft = airplanes.get(0);
        for (Aircraft aircraft : airplanes) {
            int totalSeats = aircraft.getTotalSeats();
            if ((selectedAircraft.getTotalSeats() < capacity && aircraft.getTotalSeats() > capacity)
                    || totalSeats >= capacity && totalSeats < selectedAircraft.getTotalSeats())
                selectedAircraft = aircraft;
        }
        return selectedAircraft;
    }

    /*
     * This private helper method generates and returns a flight number string from
     * the airline name parameter For example, if parameter string airline is
     * "Air Canada" the flight number should be "ACxxx" where xxx is a random 3
     * digit number between 101 and 300 (Hint: use class Random - see variable
     * random at top of class) you can assume every airline name is always 2 words.
     *
     */

    
    private String generateFlightNumber(String airline) {
        String flightNumber = "";
        String words[] = airline.split("\\s"); // stores the split airline string and number into array
        for (String word : words) {
            flightNumber += word.charAt(0); // flightNumber string equals to the first character, which is the first two
            // characters
        }
        int low = 101;
        int high = 301;
        int randomNumber = random.nextInt(high - low) + low; // generates random number between 101 and 300
        return flightNumber + randomNumber; // returns the flight number string and the random 3 digits
    }

    // Prints all flights in flights array list (see class Flight toString() method)
    // This one is done for you!
    public void printAllFlights() {
        for (int i = 0; i < flights.size(); i++) {
            System.out.println(flights.get(i).toString());
        }
    }

    // Given a flight number (e.g. "UA220"), check to see if there are economy seats
    // available
    // if so return true, if not return false
    public boolean seatsAvailable(String flightNum) {
        Boolean isValidFlightNum = false;
        Flight flight = null;
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightNum().equals(flightNum)) { // if the flightNum equals to the flight number of
                // each flight in arraylist, isValidFlightNum is set
                // to true
                isValidFlightNum = true;
                flight = flights.get(i); // flight

                //String[][] layout = flight.getaircraftSeats();
            }
        }

        if (isValidFlightNum == false) { // return false if isValidFlightNum is false
            errorMsg = "Flight " + flightNum + " Not Found";
            return false;
        }
        if (flight.seatsAvailable() == false) { // returns false if there are no seats available
            errorMsg = "Flight " + flightNum + " Full";
            return false;
        }
        flight.printSeats();
        return true;
    }
    // First check for a valid flight number
    // if it is not found, set errorMsg String and return false
    // To determine if the given flightNum is valid, search the flights array list
    // and find
    // the Flight object with matching flightNum string
    // Once a Flight object is found, check if seats are available (see class
    // Flight)
    // if flight is full, set errorMsg to an appropriate message (see video) and
    // return false
    // otherwise return true

    // Given a flight number string flightNum and a seat type, reserve a seat on a
    // flight
    // If successful, return a Reservation object
    // NOTE: seat types are not used for basic Flight objects (seats are all
    // "Economy Seats")
    // class LongHaulFlight defines two seat types
    // I suggest you first write this method *without* considering class
    // LongHaulFlight
    // once that works (test it!!), add the long haul flight code

    // if the flight number in the arraylist flights is the same as flightNum, set
    // isValidFlightNum to true and flight equal to that element
    public Reservation reserveSeatOnFlight(String flightNum, String passengerName, String passPortNum, String seatNum,
                                           String seatType) throws SeatOccupiedException, DuplicatePassengerException {
        Boolean isValidFlightNum = false;
        Flight flight = null;
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightNum().equals(flightNum)) {
                isValidFlightNum = true;
                flight = flights.get(i);
            }
        }
        if (!isValidFlightNum) { // if flight Number is not the same, there is no valid flight number
            errorMsg = "Flight " + flightNum + " Not Found";
            return null;
        }

        Reservation reservation = null;
        Passenger passenger = new Passenger(passengerName, passPortNum, seatNum, seatType);

        //if the seat number ends with a "+", flight is a longHaulFlight, isReserved is set to true
        if (seatNum.endsWith("+")) { 
            if (flight instanceof LongHaulFlight) { // checks if flight is a longhaul flight
                LongHaulFlight longHaulFlight = (LongHaulFlight) flight;
                boolean isReserved = longHaulFlight.reserveSeat(passenger, seatNum);
                if (isReserved) { // if first class seat is reserved
                    // TODO: what to set in flightInfo
                    reservation = new Reservation(flightNum, flight.toString() + " FCL", passengerName, passenger.getPassport(), seatNum);
                    reservation.setFirstClass();
                } else {
                    // the flight was full and so we couldn't find a reservation for it
                    errorMsg = "Flight " + flightNum + "  Full";
                }
            }
        } else { // if flight is not a longhaul flight
            boolean isReserved = flight.reserveSeat(passenger, seatNum);
            if (isReserved) { // seat is reserved
                // TODO: what to set in flightInfo
                reservation = new Reservation(flightNum, flight.toString(), passengerName,
                        passenger.getPassport(), seatNum);

            } else {
                errorMsg = "Flight " + flightNum + " Full";
            }
        }
        return reservation;
    }

    public String getErrorMessage() {
        return errorMsg;
    }

    /*
     * Given a Reservation object, cancel the seat on the flight
     */

    // checks to see if flight number of flights is same as flightNum
    //cancelReservation() method will cancel a reservation based on longHaul flight or regular flight
    public boolean cancelReservation(Reservation res) throws PassengerNotInManifestException {

        String flightNum = res.getFlightNum();
        Flight flight = null;
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightNum().equals(flightNum))
                flight = flights.get(i);
        }
        if (flight == null) { // returns false if flight does not exist
            errorMsg = "Flight " + flightNum + " Not Found";
            return false;
        } else {
            List<Passenger> passengerList = flight.getManifest();
            Passenger passenger = new Passenger(res.getPassengerName(), res.getPassportNum(),
                    res.getSeatNumber());
            passenger = passengerList.get(passengerList.indexOf(passenger));
            if (res.isFirstClass() && flight instanceof LongHaulFlight) { // if reservation is first class and flight is
                // a longhaul flight, then cancels longhaul
                // flight seat
                LongHaulFlight longHaulFlight = (LongHaulFlight) flight; // created longhaul flight object
                longHaulFlight.cancelSeat(passenger);
            } else {

                flight.getPassengerFromManifest(res.getPassengerName(), res.getPassportNum());
                flight.cancelSeat(passenger);
            }
        }
        // Get the flight number string from res
        // Search flights to find the Flight object - if not found, set errorMsg
        // variable and return false
        // if found, cancel the seat on the flight (see class Flight)

        // Once you have the above basic functionality working, try to get it working
        // for canceling a first class reservation
        // If this is a first class reservation (see class Reservation) and the flight
        // is a LongHaulFlight (Hint use instanceof)
        // then cancel the first class seat on the LongHaulFlight (Hint: you will need
        // to cast)
        return true; // remove this when you have written the code above
    }

    // Sort the array list of flights by increasing departure time
    // Essentially one line of code but you will be making use of a Comparator
    // object below
    public void sortByDeparture() {
        Collections.sort(flights, new DepartureTimeComparator()); // sorts arraylist flights by increasing departure
        // time
    }

    //printPassengerManifestForFlight() method will print the passengers in manifest arraylist
    public void printPassengerManifestForFlight(String flightNum) {
        Flight flight = null;
        for (int i = 0; i < flights.size(); i++) {
            Flight flight1 = flights.get(i);
            if (flight1.getFlightNum().equals(flightNum))
                flight = flight1;
        }
        if (flight == null) {
            errorMsg = "Flight " + flightNum + "Not Found";
        } else {
            for (Passenger passenger : flight.getManifest())
                System.out.println(passenger);
        }
    }

    public void printPsngrsForFlight(String flightNum) {
        Flight flight = null;
        for (int i = 0; i < flights.size(); i++) {
            Flight flight1 = flights.get(i);
            if (flight1.getFlightNum() == flightNum)
                flight = flight1;
        }
        if (flight == null) {
            errorMsg = "Flight " + flightNum + "Not Found";
        } else {
            System.out.println(flight.getManifest());
        }
    }

    // cancels reservation with the passenger name and passport number from the getPassengerFromManifest method
    public boolean cancelReservation(Reservation reservation, String name, String passportNumber) throws PassengerNotInManifestException {
        String flightNum = reservation.getFlightNum();
        Flight flight = null;
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).equals(flightNum))
                flight = flights.get(i);
        }
        if (flight == null) {
            errorMsg = "Flight " + flightNum + " Not Found";
            return false;
        } else {
            Passenger p = flight.getPassengerFromManifest(name, passportNumber);
            flight.cancelSeat(p);
            return true;
        }
    }

    // Write a simple inner class that implements the Comparator interface (NOTE:
    // not *Comparable*)
    // This means you will be able to compare two Flight objects by departure time
    private class DepartureTimeComparator implements Comparator<Flight> {

        @Override
        // implemented comparator interface, compared flight objects o1 and o2 by
        // departure time
        public int compare(Flight o1, Flight o2) {
            // departure = 0600, 0900
            // TODO: verify if this work
            return o1.getDepartureTime().compareTo(o2.getDepartureTime());
        }
    }

    // Sort the array list of flights by increasing flight duration
    // Essentially one line of code but you will be making use of a Comparator
    // object below
    public void sortByDuration() {
        Collections.sort(flights, new DurationComparator());
    }

    // Write a simple inner class that implements the Comparator interface (NOTE:
    // not *Comparable*)
    // This means you will be able to compare two Flight objects by duration

    // implemented comparator interface to compare two flight objects o1 and o2 by
    // flight duration
    private class DurationComparator implements Comparator<Flight> {

        @Override
        public int compare(Flight o1, Flight o2) {
            if (o1.getFlightDuration() < o2.flightDuration)
                return -1;
            else if (o1.getFlightDuration() < o2.flightDuration)
                return 1;
            return 0;
        }
    }

    // Prints all aircraft in airplanes array list.
    // See class Aircraft for a print() method
    public void printAllAircraft() {
        for (Aircraft aircraft : airplanes)
            aircraft.print();
    }

    // Sort the array list of Aircraft objects
    // This is one line of code. Make sure class Aircraft implements the Comparable
    // interface
    public void sortAircraft() {
        Collections.sort(airplanes);
    }

}

//Krish Patel
//501018374
/*
 * A long haul flight is a flight that travels thousands of kilometers and typically has separate seating areas
 */

public class LongHaulFlight extends Flight {
    int numFirstClassPassengers;
    String seatType;

    // Possible seat types
    public static final String firstClass = "First Class Seat";
    public static final String economy = "Economy Seat";


    public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft) {
        // use the super() call to initialize all inherited variables
        // also initialize the new instance variables
        super(flightNum, airline, dest, departure, flightDuration, aircraft); //initialized all inherited variables

    }

    public LongHaulFlight() {
        // default constructor
    }

    @Override
    public FlightType getFlightType() {
        return FlightType.LONGHAUL;
    }


    /*
     * Reserves a seat on a flight. Essentially just increases the number of passengers, depending on seat type (economy or first class)
     */

    // if seat type is economy
    //			call the superclass method reserveSeat() and return the result
    // else if the seat type is first class then
    // 			check to see if there are more first class seats available (use the aircraft method to get the max first class seats
    // 			of this airplane
    //    	if there is a seat available, increment first class passenger count (see instance variable at the top of the class)
    //    	return true;
    // else return false

    //if seat type is economy, use super class method, else if seat type is first class, do the next
    //if statement to check if the number of first class seats in aircraft are greater than the number of first class passengers
    //increment first class passnegers and return true, else return false if the first class passengers are greater than number of seats
    
    //reserveSeat() method reserves the seat of passenger if the seat does not end with a "+"
    //if the seatMap already contains the seat entered by the user, SeatOccupiedException will be thrown
    //DuplicatePassengerException will be thrown if a passenger with the same name and passport number is 
    //added to the manifest
    public boolean reserveSeat(Passenger p, String seat) throws DuplicatePassengerException,
            SeatOccupiedException {
        if (!seat.endsWith("+")) {
            return super.reserveSeat(p, seat);
        } else {
            if (seatMap.containsKey(seat))
                throw new SeatOccupiedException(p.toString() + ", " + seat);
            if (manifest.indexOf(p) >= 0) { //if the index of the manifest passenger is greater than or equal to 0, throws duplicatePassengerException
                throw new DuplicatePassengerException(p.toString() + ", " + seat);
            }
            if (aircraft.getNumFirstClassSeats() > numFirstClassPassengers) {
                manifest.add(p);
                seatMap.put(seat, p);
                numFirstClassPassengers++;
                return true;
            }
        }

        return false;
    }

    // if seat type is first class and first class passenger count is > 0
    //  decrement first class passengers
    // else
    // decrement inherited (economy) passenger count

    //if the seat type is first class and the number of first class passengers is greater than 0,
    //decrement the number of first class passengers, else decrement the super.passengers

    //cancelSeat() method will cancel the seat of passenger if the passenger is found in the manifest
    public void cancelSeat(Passenger p) throws PassengerNotInManifestException {
        int index = manifest.indexOf(p);
        if (index < 0) //if the Passenger is not found, index will be -1
            throw new PassengerNotInManifestException("Error while cancelSeat");
        p = manifest.get(index);
        manifest.remove(p);
        seatMap.remove(p.getSeat());
        passengers--;
        if (p.getSeat().endsWith("+") && numFirstClassPassengers > 0)
            numFirstClassPassengers--;
        else
            super.passengers--;

    }

    // return the total passenger count of economy passengers *and* first class passengers
    // use instance variable at top and inherited method that returns economy passenger count
    public int getPassengerCount() {
        return numFirstClassPassengers + super.getPassengers();
    }

    public String toString() {
        return super.toString() + " " + "LongHaul";
    }
}

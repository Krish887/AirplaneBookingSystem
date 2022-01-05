//Krish Patel
//501018374

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Flight System for one single day at YYZ (Print this in title) Departing flights!!

public class FlightReservationSystem {
    public static void main(String[] args)
            throws DuplicatePassengerException, PassengerNotInManifestException, FileNotFoundException {
        // Create a FlightManager object
        FlightManager manager = new FlightManager();

        // List of reservations that have been made
        ArrayList<Reservation> myReservations = new ArrayList<Reservation>(); // my flight reservations
        

        Scanner scanner = new Scanner(System.in);
        System.out.print(">");

        while (scanner.hasNextLine()) {
            String inputLine = scanner.nextLine();
            if (inputLine == null || inputLine.equals(""))
                continue;

            // The command line is a scanner that scans the inputLine string
            // For example: list AC201
            Scanner commandLine = new Scanner(inputLine);

            // The action string is the command to be performed (e.g. list, cancel etc)
            String action = commandLine.next();
            
            if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
                return;

            if (action == null || action.equals(""))
                continue;
                

            // List all flights
            else if (action.equalsIgnoreCase("LIST")) {
                manager.printAllFlights();
            }
            // if the command is PASMAN, the printPassengerManifestForFlight method will
            // return the passenger info
            // (passenger name, passport number, and seat number) from the given flight
            // number from the user
            else if (action.equalsIgnoreCase("PASMAN")) {
                String flightNum;
                if (commandLine.hasNext()) {
                    flightNum = commandLine.next();
                    manager.printPassengerManifestForFlight(flightNum);

                }
            }
            // Reserve a flight based on Flight number string (example input: res AC220)
            else if (action.equalsIgnoreCase("RES")) {
                // Get the flight number string from the commndLine scanner (use hasNext() to
                // check if there is a
                // flight number string entered
                String flightNum = null;
                String passengerName = null;
                String passportNum = null;
                String seatNum = null;

                if (commandLine.hasNext()) {
                    flightNum = commandLine.next();
                    passengerName = commandLine.next();
                    passportNum = commandLine.next();
                    seatNum = commandLine.next();
                    Reservation reservation = null;

                    // try block will check to see if the reservation for the seat is able to be
                    // made, otherwise it will catch
                    // the SeatOccupiedException and DuplicatePassengerException
                    try {
                        reservation = manager.reserveSeatOnFlight(flightNum, passengerName, passportNum, seatNum,
                                LongHaulFlight.economy);
                        if (reservation == null) // if reference to Reservation object is null, print the error message
                            System.out.println(manager.getErrorMessage());
                        else { // else add reservation into arraylist and print the reservation
                            myReservations.add(reservation);
                            reservation.print();
                        }
                    } catch (SeatOccupiedException e) {
                        System.out.println(String.format("Seat %s already occupied", seatNum));
                    } catch (DuplicatePassengerException e) {
                        System.out.println(String.format("Duplicate Passenger %s %s", passengerName, passportNum));
                    }

                }

            }

            
            // Query the flight manager to see if seats are still available for a specific
            // flight (example input: seats AC220)
            // This one is done for you as a guide for other commands
            else if (action.equalsIgnoreCase("SEATS")) {
                String flightNum = null;
                String seatNum = null;

                if (commandLine.hasNext()) {
                    flightNum = commandLine.next();
                    // seatNum = commandLine.next();
                    manager.seatsAvailable(flightNum);
                }
            }

            //cancel command will cancel the reservation with the given flight number, passanger name, and
            //passport number if the reservation exists
            //if reservation is not found in myReservations, program will print Flight Not Found message
            else if (action.equalsIgnoreCase("CANCEL")) {
                String flightNum = null;
                String passengerName = null;
                String passportNum = null;

                if (commandLine.hasNext()) {
                    flightNum = commandLine.next(); // gets flight number
                    passengerName = commandLine.next(); //gets passenger name
                    passportNum = commandLine.next(); //gets passport number
                    Reservation reservation = new Reservation(flightNum, null, passengerName, passportNum, null);
                    reservation = myReservations.get(myReservations.indexOf(reservation));
                    if (reservation != null) { // if reservation is found, cancel the reservation
                        manager.cancelReservation(reservation);
                        myReservations.remove(reservation);
                    } else { // else the flight number is not found
                        System.out.println("Flight " + flightNum + " Not Found");
                    }
                }

            }
            // Print all the reservations in array list myReservations
            else if (action.equalsIgnoreCase("MYRES")) {
                for (int i = 0; i < myReservations.size(); i++) {
                    myReservations.get(i).print();
                }
            }

            System.out.print("\n>");
        }
    }

}


import java.util.Scanner;

//Krish Patel
//501018374
/*
 *
 * This class models an aircraft type with a model name, a maximum number of economy seats, and a max number of forst class seats
 *
 * Add code such that class Aircraft implements the Comparable interface
 * Compare two Aircraft objects by first comparing the number of economy seats. If the number is equal, then compare the
 * number of first class seats
 */
public class Aircraft implements Comparable<Aircraft> {
    int numEconomySeats;
    int numFirstClassSeats;
    String model;
    String [][] seatLayout;

    public Aircraft(int seats, String model) {
        this.numEconomySeats = seats;
        this.numFirstClassSeats = 0;
        this.model = model;
    }

    public Aircraft(int economy, int firstClass, String model) {
        this.numEconomySeats = economy;
        this.numFirstClassSeats = firstClass;
        this.model = model;
    }

    public int getNumSeats() {
        return numEconomySeats;
    }

    public int getTotalSeats() {
        return numEconomySeats + numFirstClassSeats;
    }

    public int getNumFirstClassSeats() {
        return numFirstClassSeats;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void print() {
        System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
    }

    @Override
    //compareTo method to compare the numEconomySeats with otherAircraft number of economy seats and 
    //numFirstClassSeats with otherAircraft first class seats
    public int compareTo(Aircraft otherAircraft) {
        if (numEconomySeats < otherAircraft.getNumSeats())
            return -1;
        else if (numEconomySeats > otherAircraft.getNumSeats())
            return 1;
        else {
            if (numFirstClassSeats < otherAircraft.getNumFirstClassSeats())
                return -1;
            else if (numFirstClassSeats > otherAircraft.getNumFirstClassSeats())
                return 1;
            return 0;
        }
    }
    
    //returns the number of columns
    public int getColumns(){
        return (numEconomySeats + numFirstClassSeats)/getRows(); 
    }

    //returns the number of rows, which is 4
    public int getRows(){
        return 4;
    }

    //seatLayout() method will make a 2D array with a number and a letter assigned to each seat
    //for example, first row will be row A, second row will be row B, ....
    //a "+" is added to the seat if it is a first class seat
    //adds the "+" to only the columns in the 2D array and returns the seatLayout 
    public String[][] seatLayout(){
        int firstClassColumns = numFirstClassSeats/4; //number of columns that have first class seat
        
        String letters = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";
        Scanner in = new Scanner(letters);
        this.seatLayout = new String[this.getRows()][this.getColumns()];
        for (int i = 0; i < seatLayout.length; i++){ //row
            String letter = in.next();
            for (int j = 0; j < seatLayout[i].length; j++){ //column
                seatLayout[i][j] =  (j + 1) + letter;
                if(j<firstClassColumns){
                    seatLayout[i][j]+="+";
                }
                }
            }
        return seatLayout;
    }



}
  

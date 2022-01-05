//Krish Patel
//501018374

public class Passenger {
    private String name;
    private String passportNumber;
    private String seatNumber;
    private String seatType;

    public Passenger(String name, String passportNumber, String seatNumber, String seatType) {
        this.name = name;
        this.passportNumber = passportNumber;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }

    public Passenger(String name, String passport, String seat) {
        this.name = name;
        this.passportNumber = passport;
        this.seatNumber = seat;
        this.seatType = seatType;
    }

    public Passenger(String name, String passport) {
        this.name = name;
        this.passportNumber = passport;
    }

    
    @Override
    //passenger is equal to another passenger if they have the same name and passport number
    public boolean equals(Object other) {
        Passenger otherP = (Passenger) other;
        if (this.getName().equals(otherP.getName()) && this.getPassport().equals(otherP.getPassport())) {
            return true;
        }
        return false;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassport() {
        return this.passportNumber;
    }

    public void setPassport(String passport) {
        this.passportNumber = passport;
    }

    public String getSeat() {
        return this.seatNumber;
    }

    public void setSeat(String seat) {
        this.seatNumber = seat;
    }

    public String toString() {
        return this.name + " " + this.passportNumber + " " + this.seatNumber;
    }
}

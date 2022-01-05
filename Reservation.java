import java.util.Objects;

//Krish Patel
//501018374
/*
 * A simple class to model an electronic airline flight reservation
 * 
 * This class has been done for you
 */
public class Reservation
{
	String flightNum;
	String flightInfo;
	boolean firstClass;
	String passengerName;
	String passengerPassport;
	String seat;

	public Reservation(String flightNum, String info)
	{
		this.flightNum = flightNum;
		this.flightInfo = info;
		this.firstClass = false;
	}
	
	public Reservation(String flightNum, String info, String passengerName, String passengerPassport, String seat){
		this.flightNum = flightNum;
		this.flightInfo = info;
		this.firstClass = false;
		this.passengerName = passengerName;
		this.passengerPassport = passengerPassport;
		this.seat = seat;
	}
	
	public boolean isFirstClass()
	{
		return firstClass;
	}

	public void setFirstClass()
	{
		this.firstClass = true;
	}

	public String getFlightNum()
	{
		return flightNum;
	}
	
	public String getFlightInfo()
	{
		return flightInfo;
	}

	public void setFlightInfo(String flightInfo)
	{
		this.flightInfo = flightInfo;
	}

	public void print()
	{
		System.out.println(flightInfo);
	}

	public String getPassengerName(){
		return passengerName;
	}

	public String getPassportNum(){
		return passengerPassport;
	}

	public String getSeatNumber(){
		return seat;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Reservation that = (Reservation) o;
		return Objects.equals(flightNum, that.flightNum) &&
				Objects.equals(passengerName, that.passengerName) &&
				Objects.equals(passengerPassport, that.passengerPassport);
	}

	@Override
	public int hashCode() {
		return Objects.hash(flightNum, passengerName, passengerPassport);
	}
}

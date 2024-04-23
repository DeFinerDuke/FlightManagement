package src;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Flight{

    private String number;
    private String departureCity, destinationCity;
    private LocalTime departureTime, arrivalTime;
    private int duration;
    private String Gate;
    private Map<Integer, Boolean> seats;
    private Date date;

    public Flight() {
    }

    public Flight(String number, String departureCity, String destinationCity, LocalTime departureTime, LocalTime arrivalTime, int duration, String Gate, int totalSeats, Date date) {
        this.number = number;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.Gate = Gate;
        this.seats = new HashMap<>();
        for (int i = 1; i <= totalSeats; i++) {
            this.seats.put(i, true); // true indicates the seat is available
        }
        this.date = date;
    }

   

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getGate() {
        return Gate;
    }

    public void setGate(String Gate) {
        this.Gate = Gate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean hasAvailableSeats() {
        return this.seats.containsValue(true);
    }

    public boolean isSeatAvailable(int seatNumber) {
        return this.seats.getOrDefault(seatNumber, false);
    }

    public void allocateSeat(int seatNumber) {
        if (isSeatAvailable(seatNumber)) {
            this.seats.put(seatNumber, false);
        }
    }

    public Map<Integer, Boolean> getSeats() {
        return seats;
    }

    public void setSeats(Map<Integer, Boolean> seats) {
        this.seats = seats;
    }
    
    
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        String formattedDate = date != null ? sdf.format(date) : "N/A";
        String formattedDepartureTime = departureTime != null ? departureTime.format(dtf) : "N/A";
        String formattedArrivalTime = arrivalTime != null ? arrivalTime.format(dtf) : "N/A";

        return "Flight Details:\n"
                + "Flight Number: " + number + "\n"
                + "Departure City: " + departureCity + "\n"
                + "Destination City: " + destinationCity + "\n"
                + "Departure Time: " + formattedDepartureTime + "\n"
                + "Arrival Time: " + formattedArrivalTime + "\n"
                + "Duration: " + duration + "\n"
                + "Gate: " + Gate + "\n"
                + "Seats Available: "+ seats.size()  + "\n"
                + "Flight Date: " + formattedDate;
    }

   
    
}

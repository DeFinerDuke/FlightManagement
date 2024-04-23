/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author User
 */
public class FlightList extends ArrayList<Flight> {

    public FlightList() {
    }

    //Check the format of the flight number
    public boolean isValidFlightNumber(String flightNumber) {
        String regex = "F\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(flightNumber);
        return matcher.matches();
    }

    //Check the valid time
    public LocalTime inputTime(Scanner scanner, String prompt) {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = null;
        while (time == null) {
            System.out.println(prompt);
            String timeStr = scanner.next();
            try {
                time = LocalTime.parse(timeStr, timeFormat);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format.Please enter the time in the format HH:mm.");
            }
        }
        return time;
    }

    public Date inputDate(Scanner scanner, String prompt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        Date date = null;
        while (date == null) {
            System.out.println(prompt);
            String dateStr = scanner.nextLine();
            try {
                date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format dd/MM/yyyy.");
            }
        }
        return date;
    }

    public void createFlight() {

        Scanner sc = new Scanner(System.in);
        String flightNumber;
        String departureCity, destinationCity;
        String gateEntrance;
        Date dateFlight = null;
        int duration;
        String choice = "y";
        HashSet<String> flightNumbers = new HashSet<>();
        while (choice.equals("y")) {
            do {
                System.out.println("Please enter the flight number (Fxyzt): ");
                flightNumber = sc.nextLine().replaceAll("\\s", "");
                if (!isValidFlightNumber(flightNumber) || flightNumbers.contains(flightNumber)) {
                    System.out.println("Invalid flight number or Duplicate flight number!");
                    flightNumber = null;
                } else {
                    flightNumbers.add(flightNumber);
                }
            } while (flightNumber == null);

            System.out.println("Enter the departure city: ");
            departureCity = sc.nextLine().trim().toLowerCase();

            System.out.println("Enter the destination city: ");
            destinationCity = sc.nextLine().trim().toLowerCase();

            LocalTime departureTime = inputTime(sc, "Please enter the departure time (HH:mm): ");
            LocalTime arrivalTime;

            do {
                arrivalTime = inputTime(sc, "Please enter the arrival time (HH:mm): ");
                if (arrivalTime.isBefore(departureTime)) {
                    System.out.println("Arrival time must be after departure time. Please try again.");
                }
            } while (arrivalTime.isBefore(departureTime));

            duration = (int) Duration.between(departureTime, arrivalTime).toHours();
            sc.nextLine();

            while (dateFlight == null || dateFlight.before(new Date())) {
                dateFlight = inputDate(sc, "Enter flight date (dd/MM/yyyy): ");
                if (dateFlight.before(new Date())) {
                    System.out.println("The flight date must be today or in the future. Please try again.");
                    dateFlight = null;
                }
            }

            System.out.println("Please enter the gate: ");
            gateEntrance = sc.nextLine();
            System.out.println("Please enter total seats: ");
            int totalSeats = sc.nextInt();
            sc.nextLine();
            Flight new_Flight = new Flight(flightNumber, departureCity, destinationCity, departureTime, arrivalTime, duration, gateEntrance, totalSeats, dateFlight);
            this.add(new_Flight);
            System.out.println("A new flight created successfully!");
            System.out.print("Do you want to create another flight?(Y/N): ");
            choice = sc.nextLine().toLowerCase();
        }
    }

    public void saveToFile() {
        try {
            PrintWriter writer = new PrintWriter("flightdata.txt", "UTF-8");
            for (Flight flight : this) {
                writer.println(flight.toString());
            }
            writer.println("-------------------------------------------");
            writer.close();
            System.out.println("Flight data has been saved to flightdata.txt.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the flight data.");
            e.printStackTrace();
        }
    }

    public void importFromFile() {
        ArrayList flights = new ArrayList<>();
        try {

            File file = new File("flightdata.txt");
            Scanner scanner = new Scanner(file);
            String number = "", departureCity = "", destinationCity = "", Gate = "";
            LocalTime departureTime = null, arrivalTime = null;
            int duration = 0, totalSeats = 0;
            Date date = null;
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                if (data.contains("Flight Number:")) {
                    number = data.split(": ")[1].trim();
                } else if (data.contains("Departure City:")) {
                    departureCity = data.split(": ")[1].trim();
                } else if (data.contains("Destination City:")) {
                    destinationCity = data.split(": ")[1].trim();
                } else if (data.contains("Departure Time:")) {
                    departureTime = LocalTime.parse(data.split(": ")[1].trim());
                } else if (data.contains("Arrival Time:")) {
                    arrivalTime = LocalTime.parse(data.split(": ")[1].trim());
                } else if (data.contains("Duration:")) {
                    duration = Integer.parseInt(data.split(": ")[1].trim());
                } else if (data.contains("Gate:")) {
                    Gate = data.split(": ")[1].trim();
                } else if (data.contains("Seats Available:")) {
                    totalSeats = Integer.parseInt(data.split(": ")[1].trim());
                } else if (data.contains("Flight Date:")) {
                    // Assuming date is in format "dd/MM/yyyy"
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    date = formatter.parse(data.split(": ")[1].trim());
                    Flight flight = new Flight(number, departureCity, destinationCity, departureTime, arrivalTime, duration, Gate, totalSeats, date);
                    flights.add(flight);  // Assuming 'this' is a collection of flights
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while importing the flight data.");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("An error occurred while parsing the date.");
            e.printStackTrace();
        }
        
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Collections.sort(flights, new Comparator<Flight>() {
            public int compare(Flight o1, Flight o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        System.out.println("FLIGHT LIST");
        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-15s | %-15s | %-10s | %-10s | %-10s | %-10s |\n",
                "Number", "Departure City", "Destination City", "Departure Time", "Arrival Time", "Duration", "Date");
        System.out.println("-------------------------------------------------------------------------------------------------------");

        for (Flight flight : this) {
            System.out.printf("| %-10s | %-15s | %-15s | %-10s | %-10s | %-10d | %-10s |\n",
                    flight.getNumber(), flight.getDepartureCity().toUpperCase(), flight.getDestinationCity().toUpperCase(),
                    flight.getDepartureTime().format(timeFormat), flight.getArrivalTime().format(timeFormat),
                    flight.getDuration(), dateFormat.format(flight.getDate()));
        }
    }

}

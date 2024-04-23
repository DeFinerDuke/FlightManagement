/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class ReservationList extends ArrayList<Reservation> {

    private static final HashSet<String> ids = new HashSet<>();
    private static final Random rand = new Random();

    private String generateUniqueID() {
        String id;
        do {
            id = "VJ" + String.format("%06d", rand.nextInt(1000000));
        } while (ids.contains(id));
        ids.add(id);
        return id;
    }

    public Date inputDate(Scanner scanner, String prompt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        Date date = null;
        while (date == null) {
            System.out.print(prompt);
            String dateStr = scanner.next();
            try {
                date = dateFormat.parse(dateStr);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format dd/MM/yyyy.");
            }
        }
        return date;
    }

    public void addReservation(FlightList flights) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Reservation new_reservation = new Reservation();
        ArrayList<Passenger> passengers = new ArrayList<>();
        Passenger person = null;
        Flight selectedFlight = null;
        ArrayList<Flight> searchedFlight = new ArrayList<>();
        String choice = "y";
        //search flight
        while (true) {
            if (flights.isEmpty()) {
                System.out.println("Enmpty list!");
                return;
            }
            System.out.println("Enter the departure: ");
            String departure = scanner.nextLine().toLowerCase().trim();
            System.out.println("Enter the destination: ");
            String destination = scanner.nextLine().toLowerCase().trim();
            System.out.println("FLIGHT LIST");
            System.out.println("-------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-10s | %-15s | %-15s | %-10s | %-10s | %-10s | %-10s |\n",
                    "Number", "Departure City", "Destination City", "Departure Time", "Arrival Time", "Duration(Hour)", "Date");
            System.out.println("-------------------------------------------------------------------------------------------------------");

            boolean isFound = false;

            for (Flight flight : flights) {
                if (flight.getDepartureCity().equalsIgnoreCase(departure) && flight.getDestinationCity().equalsIgnoreCase(destination)) {
                    isFound = true;
                    System.out.printf("| %-10s | %-15s | %-15s | %-10s | %-10s | %-10d | %-10s |\n",
                            flight.getNumber(), flight.getDepartureCity(), flight.getDestinationCity(),
                            flight.getDepartureTime().format(timeFormat), flight.getArrivalTime().format(timeFormat),
                            flight.getDuration(), dateFormat.format(flight.getDate()));
                    searchedFlight.add(flight);
                }
            }
            if (!isFound) {
                System.out.println("No flights found. Do you want to search again? (y/n): ");
                String _choice = scanner.nextLine();
                if (!_choice.equalsIgnoreCase("y")) {
                    return; // return to main menu if user doesn't want to search again
                }
            } else {
                break; // exit the loop if a flight is found
            }
        }

        while (selectedFlight == null) {
            System.out.println("Please enter the flight number you want to book: ");
            String flightNumber = scanner.nextLine();
            boolean isFound = false;
            for (Flight flight : searchedFlight) {
                if (flight.getNumber().equals(flightNumber)) {
                    selectedFlight = flight;
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                System.out.println("Flight not found. Do you want to input again? (y/n): ");
                String input = scanner.nextLine();
                if (!input.equalsIgnoreCase("y")) {
                    return; // return to main menu if user doesn't want to input again
                }
            }
        }

        while (choice.equals("y")) {
            System.out.print("Please enter your ID: ");
            String passengerID = scanner.nextLine();
            System.out.print("Please enter your name: ");
            String passengerName = scanner.nextLine();
            Date birthDate = inputDate(scanner, "Please enter your birthday: ");
            scanner.nextLine();
            person = new Passenger(passengerID, passengerName, birthDate);
            passengers.add(person);
            System.out.print("Do you want to add more people (Y/N): ");
            choice = scanner.nextLine().toLowerCase();
        }

        if (!passengers.isEmpty()) {
            String reservationID = generateUniqueID();
            new_reservation.setID(reservationID);
            new_reservation.setSelectedFlight(selectedFlight);
            new_reservation.setTravellers(passengers);
            this.add(new_reservation);
            System.out.println("This is your  reservation ID has just been created: " + reservationID);
            System.out.println("Reservation created successfully!");
        } else {
            System.out.println("No person added to reservation!");
        }
    }

    public void checkIn() {
        Scanner scanner = new Scanner(System.in);
        Reservation reservation = null;

        if (this.isEmpty()) {
            System.out.println("ERROR: Empty list");
            return;
        }

        System.out.print("Enter the reservation ID: ");
        String reservationId = scanner.nextLine().trim();
        for (Reservation r : this) {
            if (r.getID().equals(reservationId)) {
                reservation = r;
                break;
            }
        }

        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        reservation.showInformation();
        Flight selectedFlight = reservation.getSelectedFlight();
        if (selectedFlight.hasAvailableSeats()) {
            System.out.println("Available seats: ");
            System.out.println("-------------------------------");
            System.out.println("| Seat Number | Availability  |");
            System.out.println("-------------------------------");
            for (Map.Entry<Integer, Boolean> entry : selectedFlight.getSeats().entrySet()) {
                if (entry.getValue()) {
                    System.out.printf("| %-11d | %-13s |\n", entry.getKey(), "Available");
                }
            }
            System.out.println("-------------------------------");

            for (Passenger passenger : reservation.getTravellers()) {
                while (true) {
                    System.out.println("Enter the seat number you want to choose for " + passenger.getName() + ": ");
                    int seatNumber = scanner.nextInt();
                    if (selectedFlight.isSeatAvailable(seatNumber)) {
                        selectedFlight.allocateSeat(seatNumber);
                        System.out.println("Seat allocated successfully for " + passenger.getName() + ".");
                        break;
                    } else {
                        System.out.println("The seat is not available. Please choose a different seat.");
                    }
                }
            }
        } else {
            System.out.println("No seats available on this flight.");
        }
    }

    public void saveToFile() {
        try {
            PrintWriter writer = new PrintWriter("reservationdata.txt", "UTF-8");
            for (Reservation reservation : this) {
                writer.println(reservation.toString());
                writer.println(reservation.getSelectedFlight().toString());
                for (Passenger p : reservation.getTravellers()) {
                    writer.println(p.toString());
                }
                writer.println("-------------------------------------------");
            }
            writer.close();
            System.out.println("Reservation data has been saved to reservationdata.txt.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the flight data.");
            e.printStackTrace();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class FlightManagement {

    FlightList flights;
    ReservationList reservations;
    ScheduleList schedules;
    ArrayList<Staff> staffs;

    public FlightManagement() {
        flights = new FlightList();
        reservations = new ReservationList();
        schedules = new ScheduleList();
        staffs = new ArrayList<>();
        //pilots
        staffs.add(new Staff("P01", "John Smith", "pilot"));
        staffs.add(new Staff("P02", "David Johnson", "pilot"));
        staffs.add(new Staff("P03", "Robert Williams", "pilot"));
        staffs.add(new Staff("P04", "Michael Brown", "pilot"));
        staffs.add(new Staff("P05", "William Jones", "pilot"));
        //flightattendants
        staffs.add(new Staff("FA01", "Mary Davis", "flightattendant"));
        staffs.add(new Staff("FA02", "Patricia Miller", "flightattendant"));
        staffs.add(new Staff("FA03", "Jennifer Wilson", "flightattendant"));
        staffs.add(new Staff("FA04", "Jennifer Wilson", "flightattendant"));
        staffs.add(new Staff("FA05", "Jennifer Wilson", "flightattendant"));
        //groundstaffs
        staffs.add(new Staff("GS01", "James Anderson", "groundstaff"));
        staffs.add(new Staff("GS02", "Charles Thomas", "groundstaff"));
        staffs.add(new Staff("GS03", "Charles Thomas", "groundstaff"));
        staffs.add(new Staff("GS04", "Charles Thomas", "groundstaff"));
        staffs.add(new Staff("GS05", "Charles Thomas", "groundstaff"));
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("---------~ControlPanel~---------");
            System.out.println("1. Flight schedule management.");
            System.out.println("2. Passenger reservation and booking.");
            System.out.println("3. Passenger check-in and seat allocation.");
            System.out.println("4. Crew management and assignments.");
            System.out.println("5. Save to file.");
            System.out.println("6. Print all lists from file.");
            System.out.println("7. Quit the application.");
            System.out.println("---------~---------~---------~----");
            System.out.print("Enter your choice: ");
            String choice = sc.next();
            switch (choice) {
                case "1":
                    flights.createFlight();
                    break;
                case "2":
                    reservations.addReservation(flights);
                    break;
                case "3":
                    reservations.checkIn();
                    break;
                case "4":
                    schedules.addSchedule(flights,staffs);
                    break;
                case "5":
                    flights.saveToFile();
                    reservations.saveToFile();
                    schedules.saveToFile();
                    break;
                case "6":
                    flights.importFromFile();
                    break;
                case "7":
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
                    break;

            }
        }
    }
}

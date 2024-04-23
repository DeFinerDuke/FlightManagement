/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class ScheduleList extends ArrayList<Schedule> {

    public ScheduleList() {
    }

    public void addSchedule(FlightList flights, ArrayList<Staff> staffs) {
        Scanner scanner = new Scanner(System.in);
        Schedule schedule = new Schedule();
        ArrayList<Staff> pilots = new ArrayList<>();
        ArrayList<Staff> flightAttendants = new ArrayList<>();
        ArrayList<Staff> groundStaffs = new ArrayList<>();
        Flight forFlight = null;

        if (flights.isEmpty()) {
            System.out.println("Empty list!");
            return;
        }
        for (Flight f : flights){
            System.out.println(f.toString());
        }
        while (forFlight == null) {
            System.out.println("Please enter the flight number you want to manage: ");
            String flightNumber = scanner.nextLine().toUpperCase().trim();
            boolean isFound = false;
            for (Flight flight : flights) {
                if (flight.getNumber().equals(flightNumber)) {
                    forFlight = flight;
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
        while (true) {
            System.out.println("1. Manage pilots");
            System.out.println("2. Manage flight attendants");
            System.out.println("3. Manage ground staffs");
            System.out.println("4. Exit");
            System.out.println("Which role you want to manage: ");
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    addPilot(staffs, pilots, "pilot");
                    break;
                case "2":
                    addFlightAttendant(staffs, flightAttendants, "flightattendant");
                    break;
                case "3":
                    addGroundStaff(staffs, groundStaffs, "groundstaff");
                    break;
                case "4":
                    schedule.setPilots(pilots);
                    schedule.setFlightAttendants(flightAttendants);
                    schedule.setGroundStaffs(groundStaffs);
                    schedule.setForFlight(forFlight);
                    this.add(schedule);
                    System.out.println("Schedule created successfully!");
                    System.out.println("Do you want to add another schedule? (Y/N): ");
                    String input = scanner.nextLine().toLowerCase().trim();
                    if (!input.equals("y")) {
                        return;  // Return to main menu if user doesn't want to add another schedule
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }

    }

    public void addPilot(ArrayList<Staff> staffs, ArrayList<Staff> pilots, String role) {
        Scanner scanner = new Scanner(System.in);
        for (Staff staff : staffs) {
            if (staff.getRole().equals(role)) {
                System.out.println(staff);
            }
        }
        String input = "y";
        while (input.equals("y")) {
            System.out.println("Enter the id: ");
            String id = scanner.nextLine().toUpperCase().trim();
            boolean isDuplicate = false;
            for (Staff pilot : pilots) {
                if (pilot.getId().equals(id)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                for (Staff staff : staffs) {
                    if (staff.getId().equals(id) && staff.getRole().equals(role)) {
                        pilots.add(staff);
                    }
                }
            } else {
                System.out.println("A pilot with this ID already exists. Please enter a different ID.");
                continue;
            }
            System.out.print("Do you want to add more people (Y/N): ");
            input = scanner.nextLine().toLowerCase().trim();
        }
    }

    public void addFlightAttendant(ArrayList<Staff> staffs, ArrayList<Staff> flightAttendants, String role) {
        Scanner scanner = new Scanner(System.in);
        String input = "y";
        for (Staff staff : staffs) {
            if (staff.getRole().equals(role)) {
                System.out.println(staff);
            }
        }
        while (input.equals("y")) {
            System.out.println("Enter the id: ");
            String id = scanner.nextLine().toUpperCase().trim();
            boolean isDuplicate = false;
            for (Staff flightattendant : flightAttendants) {
                if (flightattendant.getId().equals(id)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                for (Staff staff : staffs) {
                    if (staff.getId().equals(id)) {
                        flightAttendants.add(staff);
                    }
                }
            } else {
                System.out.println("A flight attendant with this ID already exists. Please enter a different ID.");
                continue;
            }
            System.out.print("Do you want to add more people (Y/N): ");
            input = scanner.nextLine().toLowerCase().trim();
        }
    }

    public void addGroundStaff(ArrayList<Staff> staffs, ArrayList<Staff> groundStaffs, String role) {
        Scanner scanner = new Scanner(System.in);
        String input = "y";
        for (Staff staff : staffs) {
            if (staff.getRole().equals(role)) {
                System.out.println(staff);
            }
        }
        String choice = "y";

        while (input.equals("y")) {
            System.out.println("Enter the id: ");
            String id = scanner.nextLine().toUpperCase().trim();
            boolean isDuplicate = false;
            for (Staff groundstaff : groundStaffs) {
                if (groundstaff.getId().equals(id)) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                for (Staff staff : staffs) {
                    if (staff.getId().equals(id)) {
                        groundStaffs.add(staff);
                    }
                }
            } else {
                System.out.println("A ground staff with this ID already exists. Please enter a different ID.");
                continue;
            }
            System.out.print("Do you want to add more people (Y/N): ");
            input = scanner.nextLine().toLowerCase().trim();
        }
    }

    public void saveToFile() {
        try {
            PrintWriter writer = new PrintWriter("scheduledata.txt", "UTF-8");
         
            for (Schedule schedule : this) {
                writer.println(schedule.toString());
                for (Staff staff : schedule.getPilots()) {
                    writer.println(staff.toString());
                }

                for (Staff staff : schedule.getFlightAttendants()) {
                    writer.println(staff.toString());
                }
                for (Staff staff : schedule.getGroundStaffs()) {
                    writer.println(staff.toString());
                }
            }
            writer.println("-------------------------------------------");
            writer.close();
            System.out.println("Schedule data has been saved to scheduledata.txt.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the flight data.");
            e.printStackTrace();
        }
    }
}

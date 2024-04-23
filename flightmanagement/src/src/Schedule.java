/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Schedule {
    private ArrayList<Staff> pilots;
    private ArrayList<Staff> flightAttendants;
    private ArrayList<Staff> groundStaffs;
    private Flight forFlight;

    public Schedule() {
    }

    public Schedule(ArrayList<Staff> pilots, ArrayList<Staff> flightAttendants, ArrayList<Staff> groundStaffs, Flight forFlight) {
 
        this.forFlight = forFlight;
        

    }

    public ArrayList<Staff> getPilots() {
        return pilots;
    }

    public void setPilots(ArrayList<Staff> pilots) {
        this.pilots = pilots;
    }

    public ArrayList<Staff> getFlightAttendants() {
        return flightAttendants;
    }

    public void setFlightAttendants(ArrayList<Staff> flightAttendants) {
        this.flightAttendants = flightAttendants;
    }

    public ArrayList<Staff> getGroundStaffs() {
        return groundStaffs;
    }

    public void setGroundStaffs(ArrayList<Staff> groundStaffs) {
        this.groundStaffs = groundStaffs;
    }

    public Flight getForFlight() {
        return forFlight;
    }

    public void setForFlight(Flight forFlight) {
        this.forFlight = forFlight;
    }

    @Override
    public String toString() {
        return "Schedule[" + "forFlight=" + forFlight + ']';
    }   
}

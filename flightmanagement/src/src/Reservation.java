/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.util.ArrayList;


public class Reservation {

    private String ID;
    private Flight selectedFlight;
    ArrayList<Passenger> travellers;
    private String Gate;
    
    public Reservation() {
    }

    public Reservation(String ID, Flight selectedFlight, ArrayList<Passenger> travellers, String Gate) {
        this.ID = ID;
        this.selectedFlight = selectedFlight;
        this.travellers = travellers;
        this.Gate = Gate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Flight getSelectedFlight() {
        return selectedFlight;
    }

    public void setSelectedFlight(Flight selectedFlight) {
        this.selectedFlight = selectedFlight;
    }

    public ArrayList<Passenger> getTravellers() {
        return travellers;
    }

    public void setTravellers(ArrayList<Passenger> travellers) {
        this.travellers = travellers;
    }

    public String getGate() {
        return Gate;
    }

    public void setGate(String Gate) {
        this.Gate = Gate;
    }
    
    public void showInformation(){
        for (Passenger passenger : travellers){
            System.out.println(passenger);
        }
        System.out.println(selectedFlight);
    }

    @Override
    public String toString() {
        return "Reservation{" + "ID=" + ID + ", Gate=" + selectedFlight.getGate() + '}';
    }
    
    
}

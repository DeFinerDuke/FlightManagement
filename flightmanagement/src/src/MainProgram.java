/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

/**
 *
 * @author User
 */
public class MainProgram {

    public static void main(String[] args) {
        FlightManagement flightmanagement = new FlightManagement();
        while (true) {
            flightmanagement.menu();
        }
    }
}

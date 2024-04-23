
package src;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Passenger {
    private String ID;
    private String Name;
    private Date birthDate;

    public Passenger() {
    }

    public Passenger(String ID, String Name, Date birthDate) {
        this.ID = ID;
        this.Name = Name;
        this.birthDate = birthDate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedBirthDate = birthDate != null ? sdf.format(birthDate) : "N/A";

        return "Passenger Details:\n"
                + "ID: " + ID + "\n"
                + "Name: " + Name + "\n"
                + "Birth Date: " + formattedBirthDate;
    }

}

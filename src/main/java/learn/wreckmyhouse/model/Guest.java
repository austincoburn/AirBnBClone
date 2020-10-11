package learn.wreckmyhouse.model;

public class Guest {

    int guest_Id;
    String firstname;
    String lastName;
    String email;
    String phoneNumber;
    String State;

    public Guest(int guest_Id, String firstname, String lastName, String email, String phoneNumber, String state) {
        this.guest_Id = guest_Id;
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        State = state;
    }

    public Guest() {

    }

    public int getGuest_Id() {
        return guest_Id;
    }

    public void setGuest_Id(int guest_Id) {
        this.guest_Id = guest_Id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setState(String state) {
        State = state;
    }
}

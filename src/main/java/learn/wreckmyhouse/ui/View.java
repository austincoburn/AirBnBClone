package learn.wreckmyhouse.ui;

import learn.wreckmyhouse.domain.Result;
import learn.wreckmyhouse.model.Guest;
import learn.wreckmyhouse.model.Host;
import learn.wreckmyhouse.model.Reservation;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

@Component
public class View {
    private final Scanner console = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final String INVALID_DATE = "[INVALID] Enter a date in MM/dd/yyyy format.";

    public MenuOption displayMenuAndSelect() {
        MenuOption[] values = MenuOption.values();
        printHeader("Main Menu");
        for(int i = 0; i < values.length; i++) {
            System.out.printf("%s, %s%n", i, values[i].getTitle());
        }
        int index = readInt("Select [0-4]: ", 0, 4);
        return values[index];
    }

    public String getHostEmail() {
        String hostEmail = readRequiredString("Host Email: ");
        return hostEmail;
    }

    public String getGuestEmail() {
        String guestEmail = readRequiredString("Guest Email: ");
        return guestEmail;
    }

    public void viewReservations(List<Reservation> reservations, Host host) {
        if(reservations == null || reservations.size() == 0) {
            System.out.println("No reservations found for " + host.getEmail());
            return;
        }
        printHeader(host.getLastName() + ": " + host.getCity() + ", " + host.getState());
       for(Reservation reservation : reservations) {
           System.out.printf("ID: %s, %s - %s, Guest: %s, %s, Email: %s%n", reservation.getId(), reservation.getStartDate(), reservation.getEndDate(),
                   reservation.getGuest().getLastName(), reservation.getGuest().getFirstname(), reservation.getGuest().getEmail());
       }
    }

    public Reservation makeReservation(List<Reservation> allReservations, Host host, Guest guest) {
        Reservation reservation = new Reservation();
        viewReservations(allReservations, host);

        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setStartDate(readLocalDate("Start Date (MM/dd/yyyy): "));
        reservation.setEndDate(readLocalDate("End Date: (MM/dd/yyyy)"));
        reservation.setTotalPrice(reservation.calculateTotalPrice());


        printHeader("Summary");
        System.out.printf("Start: %s%n", reservation.getStartDate());
        System.out.printf("End: %s%n", reservation.getEndDate());
        System.out.printf("Total: $%.2f%n", reservation.getTotalPrice());

        boolean answer = readBoolean("Is this okay? [y or n]");
        if(answer == false) {
            return null;
        }
        return reservation;
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            String input = readRequiredString(prompt).toLowerCase();
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            }
            System.out.println("[INVALID] Please enter 'y' or 'n'.");
        }
    }


    public void printHeader(String message) {
        System.out.println();
        System.out.println(message);
        System.out.println("=".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        printHeader("A critical error occurred:");
        System.out.println(ex.getMessage());
    }

    public void displayResult(Result result) {
        if(result.isSuccess()) {
            printHeader("Success!");
        } else {
            printHeader("Err:");
            for(String err : result.getErrorMessages()) {
                System.out.println(err);
            }
        }
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return console.nextLine();
    }

    private String readRequiredString(String prompt) {
        String result = null;
        do {
            result = readString(prompt).trim();
            if(result.length() == 0) {
                System.out.println("Value is required");
            }
        } while(result.length() == 0);
        return result;
    }

    private int readInt(String prompt, int min, int max) {
        int result = 0;
        do {
            result = readInt(prompt);
            if(result < min || result > max) {
                System.out.printf("Value must be between %s and %s%n", min, max);
            }
        } while(result < min || result > max);
        return result;
    }

    private int readInt(String prompt) {
        int result = 0;
        boolean isValid = false;
        do {
            String value = readRequiredString(prompt);
            try {
                result = Integer.parseInt(value);
                isValid = true;
            } catch (NumberFormatException ex) {
                System.out.println("Value must be a number");
            }
        } while (!isValid);
        return result;
    }

    private LocalDate readLocalDate(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException ex) {
                System.out.println(INVALID_DATE);
            }
        }
    }


}

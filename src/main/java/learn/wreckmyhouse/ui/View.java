package learn.wreckmyhouse.ui;

import learn.wreckmyhouse.domain.Result;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

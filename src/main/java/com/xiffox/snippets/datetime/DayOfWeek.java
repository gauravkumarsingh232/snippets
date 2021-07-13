package com.xiffox.snippets.datetime;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class DayOfWeek {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        // define a formatter for parsing input of the expected pattern
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/uuuu");
        System.out.println("Enter Date (MM/dd/yyyy):");
        String dInput = input.nextLine();
        LocalDate localDate = LocalDate.parse(dInput, dtf);
        // and print some sentence as result
        System.out.println(String.format("The date %s was a %s",
                localDate.format(DateTimeFormatter.ISO_DATE), localDate.getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH)));
        // or an alternative (this time just the day of week, but in a different way)
        System.out.println(localDate.format(DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH)));
    }
}

package ui;

import model.Account;
import model.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) throws Exception {
        Scanner inputType = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter transaction type");
        String type = inputType.nextLine();

        Scanner input = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter transaction date, value, 'from' account, and 'to' account: ");
        Date date = new SimpleDateFormat("yyyy-mm-dd").parse(input.nextLine());
        double value = input.nextDouble();
        String from = input.nextLine();
        String to = input.next();


        Transaction transaction = new Transaction(type, date, value, from, to)
         // Read user input
        System.out.println("Username is: " + userName);  // Output user input

    }
}


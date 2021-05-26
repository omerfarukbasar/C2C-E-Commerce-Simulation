/**
 * Author: Omer Basar
 * Filename: EBClientHelper.java
 * Assignment: Homework 2
 * Version: 5/14/21
 * Notes: How to create a unique id
 * https://stackoverflow.com/questions/1389736/how-do-i-create-a-unique-id-in-java
 */

// Import statements
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class EBClientHelper
{
    /**
     * processInput: takes in client information and asks questions based on their
     * buyer/seller status to create a transaction request
     *
     * Precondition: a client has connected to a server
     * Postcondition: the client sends their transaction request
     *
     * @param buyerOrSeller a String, states if the client is a buyer or seller
     * @param clientName a String, the client's name
     *
     * @return an Item representing the client's desired transaction
     */
    public static Item processInput(String buyerOrSeller, String clientName)
    {
        // Initialize variables
        Item theItem = null;
        Scanner Scan = new Scanner(System.in);
        boolean notValid = true;
        String response = "";

        // Keep asking until valid input is given
        while(notValid)
        {
            // Ask user for product ID
            System.out.println("Would you like to make a transaction? Enter yes or no.");
            response = Scan.nextLine();

            // Break loop if valid answer is given
            if(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no"))
                notValid = false;

            // State error if non-valid answer is given
            else
                System.out.println("Incorrect response, try again.");
        }

        // If user wants to quit
        if(response.equalsIgnoreCase("no"))
            theItem = new Item(null, null, "Disconnecting from transaction website.", null, -999, null);

        // If user wants to make transactions
        else if(response.equalsIgnoreCase("yes"))
        {
            // If user is a buyer
            if (buyerOrSeller.equals("buyer"))
            {
                // Initialize variable
                notValid = true;

                // Keep asking until valid input is given
                while (notValid)
                {
                    // Ask user for product ID
                    System.out.println("Enter the item id that you want to purchase.");

                    // Get the response from the user
                    String userInput = Scan.nextLine();

                    // If input is empty
                    if (userInput.equals(""))
                        System.out.println("No input detected, try again.");

                    // If input is valid
                    else
                    {
                        // Create item
                        theItem = new Item(userInput, null, "none", null, -999, buyerOrSeller);

                        // Break loop
                        notValid = false;
                    }
                }
            }

            // If user is a seller
            else
            {
                //Initialize necessary variables
                int price = 0;
                String name = "";
                String description = "";

                // Reset valid boolean
                notValid = true;

                // Generate random product ID
                String id = UUID.randomUUID().toString();

                // Keep asking for price until correct input is provided
                while (notValid)
                {
                    // Get the name from the user
                    System.out.println("Enter the name of your item.");
                    name = Scan.nextLine();

                    // Check if input is empty
                    if(name.equals(""))
                        System.out.println("No input detected, try again.");

                        // If input is not empty
                    else
                        notValid = false;
                }

                // Reset valid boolean
                notValid = true;

                // Keep asking for price until correct input is provided
                while (notValid)
                {
                    // Get the description from the user
                    System.out.println("Enter the item's description.");
                    description = Scan.nextLine();

                    // Check if input is empty
                    if(description.equals(""))
                        System.out.println("No input detected, try again.");

                    // If input is not empty
                    else
                        notValid = false;
                }

                // Reset valid boolean
                notValid = true;

                // Keep asking for price until correct input is provided
                while (notValid)
                {
                    try
                    {
                        // Get the price
                        System.out.println("Enter the item's price in cents format. Ex: 999 means $9.99");
                        price = Scan.nextInt();

                        // If price is below zero
                        if (price < 0)
                            throw new InputMismatchException();

                        // If price is considered good input
                        else
                            notValid = false;
                    }

                    // If user inputs wrong data type for the price
                    catch (InputMismatchException e)
                    {
                        //Print error message
                        System.out.println("Incorrect input for price detected. Price must utilize integers and be 0 or greater.");

                        // Clear buffer to fix scanner
                        Scan.nextLine();
                    }
                }

                // Clear buffer
                Scan.nextLine();

                // Create item
                theItem = new Item(id, name, description, clientName, price, buyerOrSeller);
            }
        }

        // Return statement
        return theItem;
    }
}

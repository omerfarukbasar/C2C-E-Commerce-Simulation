/**
 * Author: Omer Basar
 * Filename: InputManager.java
 * Assignment: Final Project
 * Version: 5/18/21
 */

// Import libraries
import java.util.Scanner;

public class InputManager
{
    /**
     * readOneItemFrom: reads Items from a file
     *
     * Precondition: a file with items exist and a scanner is provided
     * Postcondition: An Item object is returned
     *
     * @param inputSource - Scanner object, already set up
     * to read from a text file or standard input source (keyboard).
     *
     * @return oneItem - an Item with all attributes filled out.
     */
    public static Item readOneItemFrom (Scanner inputSource)
    {
        // Read one line of account data into oneLine
        System.out.println ("Reading: id, name, description, seller, price");
        String oneLine = inputSource.nextLine();

        // Parse line of account data, separated by commas.
        Scanner lineTokenizer = new Scanner (oneLine);
        lineTokenizer.useDelimiter (",");

        // Get account data (i.e. name, accountNum and balance) from oneLine
        String id = lineTokenizer.next();
        String name = lineTokenizer.next();
        String description = lineTokenizer.next();
        String seller = lineTokenizer.next();
        int price = lineTokenizer.nextInt ();

        // Create and return an Account object with the data read for one account.
        Item oneItem = new Item (id, name, description, seller, price, null);
        System.out.println (oneItem);

        // Return statement
        return oneItem;
    }
}
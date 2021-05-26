/**
 * Author: Omer Basar
 * Filename: FileHandling.java
 * Assignment: Final Project
 * Version: 5/18/21
 */

//Import libraries
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

public class FileHandling
{
    /**
     * readFromFile: reads input from a text file and adds the items to
     * the catalog system.
     *
     * Precondition: A text file with items exists and is provided in the same folder/directory
     * Postcondition: the catalog is returned with the items integrated into it.
     *
     * @param inputFileName the text file's name
     *
     * @return CatalogDictionary - the SynchronizedDictionary object with items in them
     */
    public static SynchronizedDictionary<String, Item> readFromFile (String inputFileName) throws IOException
    {
        // Create synchronized dictionary to be used
        SynchronizedDictionary<String, Item> CatalogDictionary = new SynchronizedDictionary<>();

        // Open a file for reading.
        Scanner inputSource = new Scanner (new File(inputFileName));

        // while there are more tokens to read from the input source...
        while (inputSource.hasNext())
        {
            // Read one line of input from the file into an Item object
            Item entry = InputManager.readOneItemFrom(inputSource);

            // Store the account info in the catalog.
            CatalogDictionary.add(entry.getID(), entry);

        }

        // Return statement
        return CatalogDictionary;
    }

    /**
     * outputTextFile: reads items from the catalog and writes them to an output file.
     *
     * Precondition: A text file and dictionary exists and is provided in the same folder/directory
     * Postcondition: All of the items in the catalog are recorded into a file
     *
     * @param sampleDictionary the catalog containing the entries
     * @param outputFileName the output text file's name
     */
    public synchronized static void outputTextFile(SynchronizedDictionary<String, Item> sampleDictionary, String outputFileName)
    {
        // declare an output file stream
        PrintWriter ofStream = null;
        try {

            // Initialize the output file stream, based on the name of the output file
            ofStream = new PrintWriter(new FileWriter(outputFileName));

            // Initialize String and value iterator
            Iterator<Item> ItemIterator = sampleDictionary.getValueIterator();
            Item temp = new Item(null, null, "failsafe",null,-999,null);

            // Iterate through each entry in the dictionary
            while(ItemIterator.hasNext())
            {
                // Temporarily store an item
                temp = ItemIterator.next();

                // Write a line to the output file
                ofStream.println(temp.getID() + "," + temp.getName() + "," + temp.getDescription() + "," + temp.getSeller() + "," + temp.getPrice());
            }

            if (temp.getDescription().equals("failsafe") && temp.getPrice() == -999)
                ofStream.println("");

            // Close the file
            ofStream.close();

        }
        catch (IOException e)
        {
            System.out.println("Error copying catalog to file");
        }
        finally
        {
            if (ofStream != null)
                ofStream.close();
        }
    }
}

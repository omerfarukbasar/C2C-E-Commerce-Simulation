/**
 * Author: Omer Basar
 * Filename: EBMultiServer.java
 * Assignment: Final Project
 * Version: 5/18/21
 */

// Import required libraries
import java.net.*;
import java.io.*;

public class EBMultiServer
{
    public static void main(String[] args) throws IOException
    {

        // Checks from command line if port number was given
        if (args.length != 1)
        {
            System.err.println("Usage: java IMMultiServer <port number>");
            System.exit(1);
        }

        // Assigns port number to be used
        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        // Create file to read and store entries
        String file = "catalog_entries.txt";

        // Create synchronized dictionary to be used
        SynchronizedDictionary<String, Item> CatalogDictionary = FileHandling.readFromFile(file);

        // Listens for connection requests on port and creates a new socket for each one
        try (ServerSocket serverSocket = new ServerSocket(portNumber))
        {
            while (listening)
            {
                new EBMultiServerThread(serverSocket.accept(), CatalogDictionary, file).start();
            }
        }
        catch (IOException e)
        {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
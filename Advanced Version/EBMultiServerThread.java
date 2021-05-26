/**
 * Author: Omer Basar
 * Filename: EBMultiServerThread.java
 * Assignment: Final Project
 * Version: 5/18/21
 */

// Import required libraries
import java.net.*;
import java.io.*;

public class EBMultiServerThread extends Thread
{
    private Socket socket = null;
    private SynchronizedDictionary<String, Item> dictionary = null;
    private String file;

    public EBMultiServerThread(Socket socket, SynchronizedDictionary<String, Item> sampleDictionary, String file)
    {
        super("EBMultiServerThread");
        this.socket = socket;
        this.dictionary = sampleDictionary;
        this.file = file;
    }

    public void run()
    {
        try
                (
                        // Sets up output and input streams for Message objects
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                )
        {
            // Initialize variables
            Item inputLine, outputLine;

            // Initiate conversation with client
            // Create an object of the protocol class
            EBServerProtocol imp = new EBServerProtocol();
            // Determines how the server should reply to the user's input
            // Initially starts off with "Connection Established"
            outputLine = imp.processInput(null, dictionary );
            // Outputs the server's response to the client's screen
            // Only sends strings at the moment, not objects
            System.out.println("Connection established with the client");
            out.writeObject(outputLine);

            // Receive client's response over network
            while ((inputLine = (Item) in.readObject()) != null)
            {
                // Prints what the client sent
                System.out.println("Client Transaction Request: " + System.lineSeparator() + inputLine);

                // Determines how the server should reply to the user's input
                outputLine = imp.processInput(inputLine, dictionary);

                // Update the file records
                FileHandling.outputTextFile(dictionary, file);

                // Breaks the loop if user wants to quit
                if (outputLine.getDescription().equals("Disconnecting from transaction website."))
                {
                    // Prints the bye message to server's screen
                    // Bug occurs if bye message is sent before printing to server screen
                    System.out.print(outputLine);
                    out.writeObject(outputLine);
                    break;
                }

                // Sends server's response over the network to client's screen
                else
                    out.writeObject(outputLine);
            }

            // Closes socket once connection is severed
            socket.close();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
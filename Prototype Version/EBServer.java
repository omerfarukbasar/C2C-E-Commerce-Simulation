/*
 * Copyright (c) 1995, 2014, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * Author: Omer Basar
 * Filename: EBServer.java
 * Assignment: Homework 2
 * Version: 5/14/21
 */

// Import Statements
import java.net.*;
import java.io.*;

public class EBServer
{
    public static void main(String[] args) throws IOException
    {
        // Checks the user input from the command line argument, which is the port number
        // Exits with error message if not given a port number
        if (args.length != 1)
        {
            System.err.println("Usage: java IMServer <port number>");
            System.exit(1);
        }

        // Retrieves the port number from the command line argument
        int portNumber = Integer.parseInt(args[0]);

        try
                (
                        // Server listens on the specified port for a connection request
                        ServerSocket serverSocket = new ServerSocket(portNumber);
                        // Server accepts client's connection request and creates a socket
                        Socket clientSocket = serverSocket.accept();
                        // Sets up output and input streams
                        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                )
        {
            // Initialize variables
            Item inputLine, outputLine;

            // Initialize dictionary and items to store catalog records
            // Will be replaced with file reading in next version
            SortedArrayDictionary<String, Item> CatalogDictionary = new SortedArrayDictionary<>();
            Item one = new Item("123abc","testItem1","none","bob",999,null);
            Item two = new Item("456def","testItem2","none","rob",299,null);
            Item three = new Item("789ghi","testItem3","none","cob",449,null);
            CatalogDictionary.add(one.getID(), one);
            CatalogDictionary.add(two.getID(), two);
            CatalogDictionary.add(three.getID(), three);

            // Initiate conversation with client
            // Create an object of the protocol class
            EBServerProtocol imp = new EBServerProtocol();
            // Determines how the server should reply to the user's input
            // Initially starts off with "Connection Established"
            outputLine = imp.processInput(null, CatalogDictionary );
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
                outputLine = imp.processInput(inputLine, CatalogDictionary);

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
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}

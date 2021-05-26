/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * Filename: EBClient.java
 * Assignment: Final Project
 * Version: 5/18/21
 */

// Import statements
import java.io.*;
import java.net.*;

public class EBClient
{
    public static void main(String[] args) throws IOException
    {
        // Checks the user input from the command line argument, which is the server IP and port number
        // Exits with error message if not given an IP address and port number
        if (args.length != 4)
        {
            System.err.println("Usage: java EchoClient <host name> <port number> <client name> <buyer or seller>");
            System.exit(1);
        }

        // Retrieves the server IP, port number, client name, and buyer/seller status from the command line argument
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String clientName = args[2];
        String buyerOrSeller = args[3].toLowerCase();

        // Checks if user entered buyer or seller status from command line arguments
        if (!buyerOrSeller.equals("buyer") && !buyerOrSeller.equals("seller"))
        {
            System.err.println("Usage: java EchoClient <buyer or seller>");
            System.exit(1);
        }

        try
                (
                        // Client initiates a connection request to the server
                        Socket ebSocket = new Socket(hostName, portNumber);
                        // Sets up output and input streams
                        ObjectOutputStream out = new ObjectOutputStream(ebSocket.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(ebSocket.getInputStream());
                )
        {
            // Initialize variables
            Item fromServer;
            Item fromUser;

            // Receives and stores message from server that came over the network
            while ((fromServer = (Item) in.readObject()) != null)
            {
                // Prints what the server sent
                System.out.println(fromServer.getDescription());

                // Breaks the loop if user wants to quit
                if (fromServer.getDescription().equals("Disconnecting from transaction website."))
                    break;

                // Perform operation based on buyer or seller
                fromUser = EBClientHelper.processInput(buyerOrSeller, clientName);

                // Sends client response to server
                out.writeObject(fromUser);
            }
        }
        catch (UnknownHostException e)
        {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        }
        catch (IOException e)
        {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            e.printStackTrace();
            System.exit(1);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}

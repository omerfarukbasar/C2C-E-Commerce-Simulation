/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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
 * Filename: EBServerProtocol.java
 * Assignment: Final Project
 * Version: 5/18/21
 */

// Import required libraries
import java.util.Iterator;

public class EBServerProtocol
{
    // Initialize constants which represent different stages of conversation
    private static final int WAITING = 0;
    private static final int INCONVERSATION = 1;

    // Initialize variables of the current state
    private int state = WAITING;

    /**
     * processInput: takes an item and performs the appropriate action before returning
     * an updated catalog or error message
     *
     * Precondition: a server, dictionary and item to use, all exist
     * Postcondition: items are added or removed from the catalog accordingly
     *
     * @param theInput a String, what the client last said
     * @param sampleDictionary dictionary used to store catalog
     *
     * @return a string representing the server's intended reply
     */
    public Item processInput(Item theInput, SynchronizedDictionary<String, Item> sampleDictionary)
    {
        // Initialize output variable
        Item theOutput = null;

        // Starts the conversation
        if (state == WAITING)
        {
            theOutput = new Item(null,null,null,null,-999,null);
            theOutput.setDescription(toString(sampleDictionary) + System.lineSeparator() + "Welcome to the transaction website");
            state = INCONVERSATION;
        }

        // Continues the conversation
        else if (state == INCONVERSATION)
        {
            // If the user wants to stop making transactions
            if (theInput.getDescription().equals("Disconnecting from transaction website."))
                theOutput = new Item(null,null, "Disconnecting from transaction website.",null,-999,null);

            // If the user wants to keep making transactions
            else
            {
                // If the transaction request came from a buyer
                if (theInput.getOrigin().equals("buyer"))
                {
                    // Neutralize transaction origin so item can be be stored
                    theInput.setOrigin(null);

                    // If item is in the catalog
                    if(sampleDictionary.contains(theInput.getID()))
                    {
                        // Remove item from the catalog
                        Item bought = sampleDictionary.remove(theInput.getID());

                        // Create the transaction processing status message
                        theOutput = new Item(null,null, toString(sampleDictionary) + System.lineSeparator() + "Your purchase was successful.",null,-999,null);
                    }

                    // If item is not in the catalog
                    else
                        // Create the transaction processing status message
                        theOutput = new Item(null,null, toString(sampleDictionary) + System.lineSeparator() + "Your desired item was either already bought by another user or doesn't exist in the catalog.",null,-999,null);
                }

                // If the transaction request came from a seller
                else if (theInput.getOrigin().equals("seller"))
                {
                    // Neutralize transaction origin so item can be be stored
                    theInput.setOrigin(null);

                    // Add the item to the catalog
                    sampleDictionary.add(theInput.getID(), theInput);

                    // Create the transaction processing status message
                    theOutput = new Item(null,null, toString(sampleDictionary) + System.lineSeparator() + "Your item was uploaded to the catalog.",null,-999,null);
                }
            }
        }

        // Returns the server's intended reply
        return theOutput;
    }

    /**
     * toString: return a String representation of every item object in the catalog
     *
     * Precondition: this EBServerProtocol object is valid and a dictionary is provided
     * Postcondition: a String with the catalog of items is returned
     *
     * @param sampleDictionary the dictionary we are using to store the catalog entries
     *
     * @return a String representation of the catalog and all of its items
     */
    public String toString(SynchronizedDictionary<String, Item> sampleDictionary)
    {
        // Initialize String and value iterator
        String catalog = "";
        Iterator<Item> ItemIterator = sampleDictionary.getValueIterator();

        // Iterate through each entry in the dictionary
        while(ItemIterator.hasNext())
            catalog += System.lineSeparator() + ItemIterator.next();

        if (catalog.equals(""))
            catalog = System.lineSeparator() + "There are no items available to purchase at the current time." + System.lineSeparator();

        // Return the catalog items
        return System.lineSeparator() + "-------------Catalog--------------" + catalog + System.lineSeparator();
    }
}


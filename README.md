# C2C E-Commerce Simulation

Author: Omer Basar

Description: A consumer to consumer ecommerce platform simulation similar to how eBay/Craigslist function. Clients can connect to a server and either upload or purchase items for sale. The overall purpose was to simulate transactions between clients over a network. This was my final project for Computer Networks. The users interface with the program using the terminal.

-----------------------------------------------------------------------------
# Program Details

Welcome to the transaction website program.

To set up the server, provide an open port number and run the program.

To start up a client, the user must provide the hostname, port number, the clients own name, and if they are a buyer or seller, prior to the connection.

Upon connecting to the server, clients will be shown a catalog and will be asked if they would like to make a transaction.

Responding with no will quit the application.

Responding with yes will result in the client protocols in taking the appropriate actions based on the cleint's buyer/seller status.

If the client is a buyer, they will be asked to provide the ID string of an item from the catalog they wish to buy. Then the transaction request is sent. If found in the catalog, it will be removed as an entry and confirmation will be sent. If not, then the failure message will be sent back to the client.

If the client is a seller, they will be asked to provide the name, description and price of the item they wish to sell. If incorrect parameters are provided the program will keep asking until the correct one is given. Upon completion of the details, the transaction request will be sent to the server and the item will be added to the catalog. Confirmation will be sent back to the client.

Upon completion of a transaction, the client will be shown an updated catalog and will be asked if they would like to make another transaction, repeating the previous steps.

Answering no will once again end the application.

-----------------------------------------------------------------------------
# Version Types
The prototype version supports a single client and server.

The advanced version supports multiple clients, contains additional fail-safe programming measures, utilizes synchronization to preserve
data integrity, as well as supports reading and writing to a file save states between server sessions.

-----------------------------------------------------------------------------
# Assumptions/Limitations
Financial based transactions such as transfer of currency exchanges will be assumed to be handled by a third-party service. Ex: PayPal.

Upon connection to the server, users are expected to provide identification such as name. Email login will be added in a future rendition.

In addition, clients indicate that they are a buyer or seller from the command line arguments.


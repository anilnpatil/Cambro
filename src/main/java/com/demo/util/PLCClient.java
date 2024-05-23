package com.demo.util;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PLCClient {
    public static void main(String[] args) {
        // IP Address of the PLC (server) you want to connect to
        String plcAddress = "192.168.12.5"; // Example IP address
        // The port number the PLC server is listening on
        int plcPort = 44818; // Example port number, adjust as needed
        
        try (Socket socket = new Socket(plcAddress, plcPort)) {
            // Input and output streams for sending and receiving data
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Sending a message to the PLC
            out.println("Hello, PLC!");

            // Receiving a response from the PLC
            String response = in.readLine();
            System.out.println("PLC says: " + response);

            // Closing the connection is handled by the try-with-resources statement

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.prog;

import javax.swing.JOptionPane;

/**
 *
 * @author jerem
 */
public class PROG {

    public static void main(String[] args) {
        
    JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        // User Registration
        String firstName = JOptionPane.showInputDialog("Enter First Name:");
        String lastName = JOptionPane.showInputDialog("Enter Last Name:");
        String username = JOptionPane.showInputDialog("Enter Username (must contain _ and be <= 5 characters):");
        String password = JOptionPane.showInputDialog("Enter Password (min 8 chars, 1 capital, 1 number, 1 special char):");
        String cellNumber = JOptionPane.showInputDialog("Enter Cell Number (e.g., +27XXXXXXXXX):");

        Login login = new Login(firstName, lastName, username, password, cellNumber);
        String registrationStatus = login.registerUser();
        JOptionPane.showMessageDialog(null, registrationStatus);

        if (!registrationStatus.contains("successfully")) {
            return;
        }

        // User Login
        String inputUsername = JOptionPane.showInputDialog("Enter Username to Login:");
        String inputPassword = JOptionPane.showInputDialog("Enter Password to Login:");
        String loginStatus = login.returnLoginStatus(inputUsername, inputPassword);
        JOptionPane.showMessageDialog(null, loginStatus);

        if (!loginStatus.contains("Welcome")) {
            return;
        }

        // Number of messages to send
        int numMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages do you wish to enter?"));

        // Main Menu
        while (true) {
            String[] options = {"Send Message", "Show Recent Messages", "Quit"};
            int choice = JOptionPane.showOptionDialog(null, "Select an option:", "QuickChat Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0 -> {
                    // Send Message
                    for (int i = 0; i < numMessages; i++) {
                        String recipient = JOptionPane.showInputDialog("Enter Recipient Cell Number (+27XXXXXXXXX):");
                        String message = JOptionPane.showInputDialog("Enter Message (max 250 characters):");
                        Message msg = new Message(recipient, message);
                        if (msg.checkMessageId()) {
                            JOptionPane.showMessageDialog(null, msg.sendMessage());
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Message ID.");
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Total Messages Sent: " + Message.returnTotalMessages());
            }
                case 1 -> // Show Recent Messages
                    JOptionPane.showMessageDialog(null, "Coming Soon.");
                case 2 -> {
                    // Quit
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    return;
            }
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }


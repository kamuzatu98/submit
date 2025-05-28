/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog;

/**
 *
 * @author jerem
 */


import ca.oson.json.org.JSONObject;
import org.json.JSONObject;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Message {
    private String messageId; 
    private String recipient;
    private String messageContent; 
    private String messageHash; 
    private static int messageCount = 0;
    private static List<String> sentMessages = new ArrayList<>();

    public Message(String recipient, String messageContent) {
        this.messageId = generateMessageId();
        this.recipient = recipient;
        this.messageContent = messageContent;
        this.messageHash = createMessageHash();
        messageCount++;
    }

    private String generateMessageId() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    public boolean checkMessageId() {
        return messageId != null && messageId.length() == 10 && messageId.matches("\\d+");
    }

    public boolean checkRecipientCell() {
        return recipient != null && recipient.length() <= 10 && recipient.matches("^\\+27[0-9]{9}$");
    }

    public boolean checkMessageLength() {
        return messageContent != null && messageContent.length() <= 250;
    }

    public String createMessageHash() {
        if (messageContent == null || messageContent.trim().isEmpty()) {
            return "";
        }
        String[] words = messageContent.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        String hash = String.format("%s:%d%s%s", messageId.substring(0, 2), messageCount, firstWord, lastWord);
        return hash.toUpperCase();
    }

    public String sendMessage() {
        if (!checkMessageId()) {
            return "Invalid Message ID.";
        }
        if (!checkRecipientCell()) {
            return "Recipient cell number is invalid or does not contain international code (+27).";
        }
        if (!checkMessageLength()) {
            return "Please enter a message of less than 250 characters.";
        }
        String messageDetails = String.format("Message ID: %s\nMessage Hash: %s\nRecipient: %s\nMessage: %s",
                messageId, messageHash, recipient, messageContent);
        sentMessages.add(messageDetails);
        JOptionPane.showMessageDialog(null, messageDetails, "Message Sent", JOptionPane.INFORMATION_MESSAGE);
        return "Message sent.";
    }

    public String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages sent.";
        }
        StringBuilder messages = new StringBuilder("Sent Messages:\n");
        for (String msg : sentMessages) {
            messages.append(msg).append("\n\n");
        }
        return messages.toString();
    }

    public int returnTotalMessages() {
        return messageCount;
    }

    public String storeMessage() {
        JSONObject json = new JSONObject();
        json.put("messageId", messageId);
        json.put("messageHash", messageHash);
        json.put("recipient", recipient);
        json.put("messageContent", messageContent);

        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(json.toString() + "\n");
            return "Message stored in JSON file.";
        } catch (IOException e) {
            return "Error storing message: " + e.getMessage();
        }
    }

    public String processMessage() {
        String[] options = {"Send Message", "Disregard Message", "Store Message"};
        int choice = JOptionPane.showOptionDialog(null, "Choose an action for the message:", "Message Options",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        return switch (choice) {
            case 0 -> sendMessage();
            case 1 -> "Message disregarded.";
            case 2 -> storeMessage();
            default -> "No action selected.";
        };
    }
}
    


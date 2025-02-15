/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.management.system;

/**
 *
 * @author Elfouly
 */

import java.io.BufferedReader;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Librarian extends User {
    String line;

    public Librarian(String username, String password, int id) {
        super(username, password, id, "Librarian");
    }

    public Librarian() {
        super();
    }

    public void editBookQuantity(String title, int newQuantity) {
        ArrayList<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("book.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("tempBook.txt"))) {

            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 3 && parts[1].trim().equalsIgnoreCase(title)) {
                    found = true;
                    System.out.println("Book Found:");
                    System.out.println("Before Edit: " + line);

                    parts[3] = String.valueOf(newQuantity);
                    line = String.join(",", parts);
                }

                updatedLines.add(line);
            }

            if (!found) {
                System.out.println("Book with title '" + title + "' not found.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tempBook.txt"))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File originalFile = new File("book.txt");
        File tempFile = new File("tempBook.txt");

        if (tempFile.renameTo(originalFile)) {
            System.out.println("Book information updated successfully.");
        }
        if (originalFile.delete() && tempFile.renameTo(originalFile)) {
            System.out.println("Book information updated successfully.");
        } else {
            System.out.println("Error updating book information. Manual intervention required.");
        }
    }

    public String Requests() {
        StringBuilder details = new StringBuilder();
    
        File file = new File("borrowing.txt");
        if (!file.exists()) {
            System.out.println("File does not exist");
            return ""; // Return empty string if file doesn't exist
        }
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
    
            while ((line = reader.readLine()) != null) {
                System.out.println("Line read: " + line); // Debug print
                String[] parts = line.split(",");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim(); // Trim each part
                }
                //System.out.println("Parts: " + Arrays.toString(parts));/ // Debug print
                if (parts.length > 1) {
                    String username = parts[0];
                    String bookTitle = parts[1];
                    String date = parts[2];
    
                    details.append("Username: ").append(username).append("\n");
                    details.append("Book Title: ").append(bookTitle).append("\n");
                    details.append("Date: ").append(date).append("\n\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Return the details as a string
                return details.toString();
    }


    @Override
    public String toString() {
        return ID + "," + userName + "," + password + "," + "L\n";
    }

//    public void adduser(Supplier v) throws IOException {
//
//        FileWriter file = new FileWriter("user.txt", true);
//        file.write(v.toString());
//        System.out.println("supplier  signed up successfully.");
//        file.close();
//    }

    public void deleteBookRequest(String bookTitle, String borrowerName) {
        boolean deleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("borrowing.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("tempBorrowing.txt"))) {


            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String currentBorrowerName = parts[0].trim();
                String currentBookTitle = parts[1].trim();

                if (!currentBorrowerName.equalsIgnoreCase(borrowerName) || !currentBookTitle.equalsIgnoreCase(bookTitle)) {
                    // If the current line does not match the specified borrower and book title, keep it in the temp file
                    writer.write(line);
                    writer.newLine();  // Add newline for the next line
                } else {
                    // Mark that a deletion occurred
                    deleted = true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error updating borrowing file.");
        }

        if (deleted) {
            File originalFile = new File("borrowing.txt");
            File tempFile = new File("tempBorrowing.txt");

            if (originalFile.delete() && tempFile.renameTo(originalFile)) {
                System.out.println("Book request deleted successfully.");
            } else {
                System.out.println("Error deleting book request. Manual intervention required.");
            }
        } else {
            System.out.println("No matching book request found for deletion.");
        }
    }
    public void confirmRequest(String bookTitle, String borrowerName, String returnDate, String librarianUsername) {
        boolean isBookAvailable = isBookAvailableForBorrowing(bookTitle);

        if (isBookAvailable) {
            createBorrowing(bookTitle, borrowerName, returnDate, librarianUsername);
        } else {
            System.out.println("Book '" + bookTitle + "' is either already borrowed or does not exist in the library.");
        }
    }

    private boolean isBookAvailableForBorrowing(String bookTitle) {
        boolean isBookInBooksFile = false;
        boolean isBookInBorrowingsFile = false;

        try (BufferedReader booksReader = new BufferedReader(new FileReader("book.txt"));
             BufferedReader borrowingsReader = new BufferedReader(new FileReader("borrowing.txt"))) {

            String booksLine;
            while ((booksLine = booksReader.readLine()) != null) {
                String[] bookParts = booksLine.split(",");
                if (bookParts.length >= 2 && bookParts[1].trim().equalsIgnoreCase(bookTitle)) {
                    isBookInBooksFile = true;
                    break;
                }
            }

            String borrowingsLine;
            while ((borrowingsLine = borrowingsReader.readLine()) != null) {
                String[] borrowingParts = borrowingsLine.split(",");
                if (borrowingParts.length >= 2 && borrowingParts[1].trim().equalsIgnoreCase(bookTitle)) {
                    isBookInBorrowingsFile = true;
                    break;
                }
            }

            return isBookInBooksFile && !isBookInBorrowingsFile;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

   public void createBorrowing(String bookTitle, String borrowerName, String returnDate, String librarianUsername) {
        try (BufferedReader booksReader = new BufferedReader(new FileReader("book.txt"));
             BufferedWriter libconfirmingWriter = new BufferedWriter(new FileWriter("libconfirming.txt", true))) {

            String bookLine;
            String bookPrice = null;

            while ((bookLine = booksReader.readLine()) != null) {
                String[] bookParts = bookLine.split(",");
                if (bookParts.length >= 2 && bookParts[1].trim().equalsIgnoreCase(bookTitle)) {
                    bookPrice = bookParts[3].trim();
                    break;
                }
            }

            if (bookPrice != null) {
                int orderId = generateOrderId();
                libconfirmingWriter.write(orderId + "," + bookTitle + "," + borrowerName + "," + returnDate + "," + bookPrice + "," + librarianUsername);
                libconfirmingWriter.newLine();
                System.out.println("Borrowing confirmed and written to 'libconfirming.txt' successfully.");
            } else {
                System.out.println("Book price not found in 'books.txt'. Unable to confirm borrowing.");
            }
        } catch (IOException e) {
            System.out.println("Error creating borrowing in 'libconfirming.txt'.");
            e.printStackTrace();
        }
    }

    private int generateOrderId() {
        return (int) (Math.random() * (9999 - 1000 + 1) + 1000);
    }

}



    
    

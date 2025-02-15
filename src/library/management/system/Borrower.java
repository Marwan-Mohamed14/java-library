/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.management.system;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
    
public class Borrower extends User {


    public Borrower(String userName, String password, int ID) {
        super(userName, password, ID, "Borrwer");
    }

    @Override
    public String toString() {
        return ID + "," + userName + "," + password + "," + "B\n";
    }


    public static void Bookrequest(String title ,String date,String Currentusername) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("borrowing.txt", true));
            writer.write( Currentusername + "," + title+","+date);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean SignUp(Borrower u) throws IOException {
        for (User userz : User.getAllUsers()) {
            if (userz.userName.equals(u.userName) && userz.getUserType().equals(u.getUserType())) {

                return false;
            }

        }
        FileWriter file = new FileWriter("user.txt", true);
        file.write(this.toString());
        file.close();
        return true;

    }

    public static String displaybook() {
        StringBuilder result = new StringBuilder();

        String filePath = "book.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print the error for debugging purposes
            result.append("Error: Unable to read book data."); // Provide an error message
        }

        System.out.println("Contents of result: " + result); // Debug print
        return result.toString();
    }


    public static void rateBook(String name,int bookId, int rating) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ratings.txt", true))) {
            writer.write(name + "," + bookId + "," + rating);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String viewBorrowingsDetails(String currentUser) {
        StringBuilder details = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("borrowing.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    String borrowerName = parts[0].trim();  

                    if (borrowerName.equals(currentUser)) {
                        details.append("Borrower: ").append(borrowerName).append("\n");
                        details.append("Book: ").append(parts[1].trim()).append("\n");
                        details.append("\n"); 
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return details.toString();
    }

}



 
    


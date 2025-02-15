/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.management.system;

import java.io.BufferedReader;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Serializable;
import java.util.ArrayList;
import java.nio.file.*;
public class Supplier extends User {
    Supplier(User u){
        super(u.userName, u.password, u.ID,"supplier");

    }
    public String toString() {
        return this.ID + "," + this.userName + "," + this.password+","+"S\n";
    }
    public Supplier(String username, String pass, int id) {
        this.userName = username;
        this.password = pass;
        this.ID = id;
    }



    public static void confirmOrder(int orderId, String supplierName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("LibConfirming.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("order.txt", true))) {

            String line;
            boolean orderConfirmed = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int orderID = Integer.parseInt(parts[0].trim());
                double price = Double.parseDouble(parts[4].trim());

                if (orderID == orderId) {
                    double totalPrice = price + 50;
                    writer.write(orderId + "," + totalPrice + "," + supplierName + "\n");
                    orderConfirmed = true;
                    break;
                }
            }

            if (orderConfirmed) {
                System.out.println("Order confirmed and written to Order file.");
            } else {
                System.out.println("Order not found.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error confirming order.");
        }
    }


//    public static void supMostOrder() {
//        String supplierWithMostOrders = null;
//        int maxOrders = 0;
//        try (BufferedReader reader = new BufferedReader(new FileReader("orders.txt"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length > 1) {
//                    String supplierName = parts[parts.length - 1].trim();  // Extracting the last element
//                    int orderCount = countOrdersForSupplier(supplierName);
//                    if (orderCount > maxOrders) {
//                        maxOrders = orderCount;
//                        supplierWithMostOrders = supplierName;
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (supplierWithMostOrders != null) {
//            System.out.println("Supplier with Most Orders: " + supplierWithMostOrders);
//            System.out.println("Number of Orders: " + maxOrders);
//        } else {
//            System.out.println("No orders found.");
//        }
//    }
//
//    public static int countOrdersForSupplier(String supplierName) {
//        int orderCount = 0;
//        try (BufferedReader reader = new BufferedReader(new FileReader("orders.txt"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length > 0) {
//                    String currentSupplier = parts[parts.length - 1].trim();
//                    if (currentSupplier.equalsIgnoreCase(supplierName)) {
//                        orderCount++;
//                    }
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return orderCount;
//    }



}










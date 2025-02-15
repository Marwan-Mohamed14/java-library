/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.management.system;



import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Admin extends User implements Serializable{
    private  boolean isAdmin;
    private int userIdToRemove;
    int orderCount = 0;
    User user;
    String line;
    int borrowingsCount;
    double totalRevenue = 0;
    public Admin(String username, String password, int id) {
        super(username, password, id,"Admin");
    }
    public Admin() {
        isAdmin=true;
    }
    public void adduser(Supplier v) throws IOException {

        FileWriter file = new FileWriter("user.txt", true);
        file.write(v.toString());
        System.out.println("supplier  signed up successfully.");
        file.close();
    }

    /*public void addUser(Librarian u) {
        try (FileWriter fw = new FileWriter("user.txt", true)) {
            fw.write(username + "," + password + "," + id+ "," + userType + "\n");
            System.out.println("User added successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    public void addLibrian(Librarian u) throws IOException{
        FileWriter file=new FileWriter("user.txt",true);
        file.write(u.toString());
        file.close();
    }

    @Override
    public String toString() {
        return ID + "," + userName + "," + password+","+"A\n";
    }






    public void removeUser(String username,boolean edit){
        try (
                BufferedReader reader = new BufferedReader(new FileReader("user.txt"));
                FileWriter writer = new FileWriter("tempusers.txt")) {

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String currentUsername = parts[1];
                if ( !(username.equals (currentUsername))) {
                    writer.write(line + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File("user.txt");
        File tempFile = new File("tempusers.txt");

        if (file.delete() && tempFile.renameTo(file)) {
            if(edit)
                System.out.println("user removed successfully.");
            else System.out.println("User edited successfully");
        } else {
            System.out.println("Error removing the user.");
        }
    }
    public void editUser(String currentUsername, String currentPassword, int newID) {
        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("tempusers.txt"))) {


            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[1].trim();
                String password = parts[2].trim();

                if (username.equals(currentUsername) && password.equals(currentPassword)) {

                    parts[0] = Integer.toString(newID);

                    writer.write(String.join(",", parts) + "\n");
                } else {

                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error editing the user.");
        }


        File originalFile = new File("user.txt");
        File tempFile = new File("tempusers.txt");

        try {
            if (originalFile.exists() && !originalFile.delete()) {
                throw new IOException("Could not delete original user file.");
            }
            if (!tempFile.renameTo(originalFile)) {
                throw new IOException("Could not rename temporary file to replace original user file.");
            }
            System.out.println("User edited successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error replacing the original user file.");
        }
    }


    public void addbooks(Book b){
        ArrayList<Book>BookList=new ArrayList<>();
        BookList.add(b);
        try  {
            FileWriter fw = new FileWriter("book.txt", true);
            fw.write(b.toString());
            System.out.println("\n");
            System.out.println(b.toString());

            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void removeBook(String booknametoremove, boolean edit) {
        try (
                BufferedReader reader = new BufferedReader(new FileReader("book.txt"));
                FileWriter writer = new FileWriter("temp.txt")) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String book = parts[1].trim();

                if (!(booknametoremove.equals(book))) {
                    writer.write(line + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.delete(Paths.get("book.txt"));
            Files.move(Paths.get("temp.txt"), Paths.get("book.txt"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error deleting or renaming files.");
        }

        if (!edit) {
            System.out.println("Book removed successfully.");
        }
    }
    public int countBorrow() {
        int count = 0;

        try (BufferedReader b = new BufferedReader(new FileReader("borrowing.txt"))) {
            String line;
            while ((line = b.readLine()) != null) {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Total borrow count: " + count);
        return count;
    }
    public void maxRevBook() {
        String mostRevenueBook = null;
        double maxRevenue = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader("orders.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                String bookName = null;
                double orderPrice = 0.0;

                for (String part : parts) {
                    if (part.trim().startsWith("Book: ")) {
                        bookName = part.trim().substring("Book: ".length());
                    }
                    else if (part.trim().startsWith("Price: ")) {
                        orderPrice = Double.parseDouble(part.trim().substring("Price: ".length()));
                    }
                }

                if (bookName != null) {
                    if (orderPrice > maxRevenue) {
                        mostRevenueBook = bookName;
                        maxRevenue = orderPrice;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File 'orders.txt' not found.", e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mostRevenueBook != null) {
            System.out.println("Book with most revenue: " + mostRevenueBook);
            System.out.println("Total revenue: " + maxRevenue);
        } else {
            System.out.println("No orders found.");
        }
    }

    private String extractBookName(String[] orderParts) {
        for (String part : orderParts) {
            if (part.trim().startsWith("Book: ")) {
                return part.trim().substring("Book: ".length());
            }
        }
        return "";
    }

    private double extractOrderPrice(String[] orderParts) {
        for (String part : orderParts) {
            if (part.trim().startsWith("Price: ")) {
                return Double.parseDouble(part.trim().substring("Price: ".length()));
            }
        }
        return 0.0;
    }


    private int countOrdersForBook(String bookName) {

        try (BufferedReader reader = new BufferedReader(new FileReader("order.txt"))) {

            while ((line = reader.readLine()) != null) {
                if (line.contains("Book: " + bookName)) {
                    orderCount++;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File 'orders.txt' not found.", e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orderCount;
    }


    public String getBorrowerWithMaxBorrowings() {
        String borrowerWithMaxBorrowings = null;
        int maxBorrowings = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("borrowing.txt"))) {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    String borrowerName = parts[0].trim();
                    int borrowingsCount = countBorrowingsForBorrower(borrowerName);

                    if (borrowingsCount > maxBorrowings) {
                        maxBorrowings = borrowingsCount;
                        borrowerWithMaxBorrowings = borrowerName;
                    }
                }
            }

            if (borrowerWithMaxBorrowings != null) {
                System.out.println("Borrower with Maximum Borrowings: " + borrowerWithMaxBorrowings);
                System.out.println("Number of Borrowings: " + maxBorrowings);
            } else {
                System.out.println("No borrowings found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return borrowerWithMaxBorrowings;
    }

    /*public String getBorrowerWithMaxBorrowings() {
        String borrowerWithMaxBorrowings = null;
        int maxBorrowings = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("borrowing.txt"))) {
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    String borrowerName = parts[0].trim();
                     borrowingsCount = countBorrowingsForBorrower(borrowerName);

                    if (borrowingsCount > maxBorrowings) {
                        maxBorrowings = borrowingsCount;
                        borrowerWithMaxBorrowings = borrowerName;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (borrowerWithMaxBorrowings != null) {
            System.out.println("Borrower with Maximum Borrowings: " + borrowerWithMaxBorrowings);
            System.out.println("Number of Borrowings: " + maxBorrowings);
        } else {
            System.out.println("No borrowings found.");
        }


        return borrowerWithMaxBorrowings;
    }*/


    /*  public static String getBorrowerWithMaxRevenue() {
         String borrowerWithMaxRevenue = null;
         double maxRevenue = 0;

         try (BufferedReader reader = new BufferedReader(new FileReader("orders.txt"))) {
             String line;

             while ((line = reader.readLine()) != null) {
                 String[] parts = line.split(",");
                 if (parts.length > 1) {
                     String borrowerName = parts[1].trim();

                     double orderRevenue = calculateOrderRevenueForBorrower(parts);

                     if (orderRevenue > maxRevenue) {
                         borrowerWithMaxRevenue = borrowerName;
                     }
                 }
             }
         } catch (IOException e) {
             e.printStackTrace();
         }

         if (borrowerWithMaxRevenue != null) {
             System.out.println("Borrower with Maximum Revenue: " + borrowerWithMaxRevenue);

             return borrowerWithMaxRevenue;
         } else {
             System.out.println("No borrowings found.");
             return null;
         }
     }*/
    public static String getBorrowerWithMaxRevenue() {
        String borrowerWithMaxRevenue = null;
        double maxRevenue = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("order.txt"))) {
            String line;

            while ((line = reader.readLine())!= null) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    String borrowerName = parts[1].trim();

                    double orderRevenue = calculateOrderRevenueForBorrower(parts);

                    if (orderRevenue > maxRevenue) {
                        borrowerWithMaxRevenue = borrowerName;
                    }
                }
            }
        } catch (IOException e) {
            // e.printStackTrace();
        }

        if (borrowerWithMaxRevenue!= null) {
            System.out.println("Borrower with Maximum Revenue: " + borrowerWithMaxRevenue);

            return borrowerWithMaxRevenue;
        } else {
            System.out.println("No borrowings found.");
            return null;
        }
    }

    private static double calculateOrderRevenueForBorrower(String[] orderDetails) {
        double orderRevenue = 0.0;

        for (String detail : orderDetails) {
            if (detail.trim().startsWith("Price:")) {
                String[] priceParts = detail.trim().split(":");
                if (priceParts.length > 1) {
                    orderRevenue = Double.parseDouble(priceParts[1].trim());
                    break;
                }
            }
        }

        return orderRevenue;
    }

   public String getBorrowingsPerBorrower() {
    StringBuilder result = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader("borrowing.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length > 1) {
                String borrowerName = parts[1].trim();
                int borrowings = countBorrowingsForBorrower(borrowerName);
                result.append("Borrower: ").append(borrowerName).append(", Borrowings: ").append(borrowings).append("\n");
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return result.toString();
}



    public int countBorrowingsForBorrower(String borrowerName) {
        borrowingsCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("borrowing.txt"))) {

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    String currentBorrower = parts[0].trim();

                    if (currentBorrower.equalsIgnoreCase(borrowerName.trim())) {
                        borrowingsCount++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Borrowings Count: " + borrowingsCount);
        return borrowingsCount;
    }
    /*  System.out.println("Current Borrower: '" + currentBorrower + "'");*/

    public void viewBorrowingsDetails() {
        try (BufferedReader reader = new BufferedReader(new FileReader("borrowing.txt"))) {

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public double calculateTotalRevenue() {


        try (BufferedReader reader = new BufferedReader(new FileReader("order.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 3) {
                    try {
                        double orderRevenue = Double.parseDouble(parts[3].trim().split(":")[1].trim());
                        totalRevenue += orderRevenue;
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return totalRevenue;
    }
    private static final Logger logger = Logger.getLogger(Admin.class.getName());
    
public static String getOrderDetailsBySupplier() {
    StringBuilder result = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new FileReader("order.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length > 1) {
                String supplierName = parts[parts.length - 2].trim();
                int orderCount = countOrdersForSupplier(supplierName);

                result.append("Order Details:\n");
                result.append(line).append("\n");
                result.append("Number of Orders: ").append(orderCount).append("\n");
                result.append("-------------------------------\n");
            }
        }
    } catch (IOException e) {
        logger.log(Level.SEVERE, "Error reading orders file", e);
    }
    return result.toString();
}
   
    public static String getSupplierWithMaxOrders() {
        String supplierWithMaxOrders = null;
        int maxOrders = 0;
        Map<String, Integer> supplierOrderCount = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("order.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println("Processing line: " + line); // Debug statement
                String[] parts = line.split(",");
                if (parts.length >= 3) { // Ensure each line has at least 3 elements
                    String supplierName = parts[2].trim(); // Get the supplier name
                    System.out.println("Supplier Name: " + supplierName); // Debug statement

                    supplierOrderCount.put(supplierName, supplierOrderCount.getOrDefault(supplierName, 0) + 1);
                    System.out.println("Current count for " + supplierName + ": " + supplierOrderCount.get(supplierName)); // Debug statement

                    if (supplierOrderCount.get(supplierName) > maxOrders) {
                        maxOrders = supplierOrderCount.get(supplierName);
                        supplierWithMaxOrders = supplierName;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (supplierWithMaxOrders != null) {
            System.out.println("Supplier with Most Orders: " + supplierWithMaxOrders);
            System.out.println("Number of Orders: " + maxOrders);
            return supplierWithMaxOrders;
        } else {
            System.out.println("No orders found.");
            return null;
        }
    }

    private static int countOrdersForSupplier(String supplierName) {
        int orderCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("order.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 2) {
                    String currentSupplier = parts[2].trim();
                    if (currentSupplier.equalsIgnoreCase(supplierName)) {
                        orderCount++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderCount;
    }
    public static String getSupplierWithMaxRevenue() {
        String supplierWithMaxRevenue = null;
        double maxRevenue = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("order.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 2) {
                    String supplierName = parts[2].trim();
                    double orderRevenue = Double.parseDouble(parts[1].trim());

                    if (orderRevenue > maxRevenue) {
                        maxRevenue = orderRevenue;
                        supplierWithMaxRevenue = supplierName;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (supplierWithMaxRevenue != null) {
            System.out.println("Supplier with Maximum Revenue: " + supplierWithMaxRevenue);
            System.out.println("Revenue: " + maxRevenue);
            return supplierWithMaxRevenue;
        } else {
            System.out.println("No orders found.");
            return null;
        }
    }
    public static String mostBook() {
        try (BufferedReader reader = new BufferedReader(new FileReader("borrowing.txt"))) {
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            String mostRequestedBook = null;
            int maxRequestCount = 0;
            for (String storedLine : lines) {
                String[] parts = storedLine.split(",");
                if (parts.length > 2) {
                    String requestedBook = parts[1].trim();
                    int count = countOccurrences(lines, requestedBook);
                    if (count > maxRequestCount) {
                        maxRequestCount = count;
                        mostRequestedBook = requestedBook;
                    }
                }
            }

            if (mostRequestedBook != null) {
                return "Most Requested Book: " + mostRequestedBook + "\nNumber of Requests: " + maxRequestCount;
            } else {
                return "No book requests found.";
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int countOccurrences(ArrayList<String> lines, String bookTitle) {
        int count = 0;
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length > 2 && parts[1].trim().equalsIgnoreCase(bookTitle)) {
                count++;
            }
        }
        return count;}

}

//    public boolean searchUser(String username) {
//    try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
//        String line;
//
//        while ((line = reader.readLine()) != null) {
//            String[] parts = line.split(",");
//
//            if (parts.length >= 2) {
//                String currentUsername = parts[1].trim();
//
//                System.out.println("Current line: " + line);
//                System.out.println("Current username: " + currentUsername);
//                System.out.println("Searching for: " + username);
//
//                if (currentUsername.equalsIgnoreCase(username)) {
//                    System.out.println("User with username '" + username + "' found.");
//                    return true;
//                }
//            }
//        }
//
//        System.out.println("User with username '" + username + "' not found.");
//        return false;
//    } catch (IOException e) {
//        e.printStackTrace();
//        return false;
//    }
//}

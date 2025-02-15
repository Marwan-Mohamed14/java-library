package library.management.system;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class User {
    String userName;
    String password;
    private String userType;
    int ID;
    private static ArrayList<User> users = new ArrayList<>(); // Initialize here

    public User() {
        userName = "";
        password = "";
        ID = 0;
        loadUsersFromFile(); 
    }

    public User(String userName, String password, int ID, String type) {
        this.userName = userName;
        this.password = password;
        this.ID = ID;
        this.userType = type;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getID() {
        return ID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserType() {
        return userType;
    }

    public static ArrayList<User> getAllUsers() {
        return users;
    }

    public static String login(String username, String password) {
        for (User user : users) {
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                if ("B".equals(user.getUserType())) {
                    return "Borrower";
                } else if ("L".equals(user.getUserType())) {
                    return "Librarian";
                } else if ("S".equals(user.getUserType())) {
                    return "Supplier";
                } else {
                    return "Admin";
                }
            }
        }

        System.out.println("Invalid username or password");
        return "Invalid username or password";
    }

    static void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && !line.trim().isEmpty()) {
                    int userId = Integer.parseInt(parts[0].trim());
                    String userName = parts[1].trim();
                    String password = parts[2].trim();
                    String userType = parts[3].trim();
                    User user = new User(userName, password, userId, userType) {
                    };
                    users.add(user); // Add to the static ArrayList
                } else {
                    // Handle invalid user data
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static Book searchBook(String title) {
        try (BufferedReader reader = new BufferedReader(new FileReader("book.txt"))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Check if parts array has at least 4 elements
                if (parts.length >= 4) {
                    String bookTitle = parts[1].trim(); // Ensure that parts array has at least 2 elements

                    if (bookTitle.equalsIgnoreCase(title)) {
                        found = true;
                        System.out.println("Book Found:");
                        System.out.println(line);
                        Book book = new Book(Integer.parseInt(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]));
                        return book;
                    }
                }
            }

            if (!found) {
                System.out.println("Book with title '" + title + "' not found.");
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

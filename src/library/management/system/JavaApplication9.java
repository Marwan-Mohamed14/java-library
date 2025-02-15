/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package library.management.system;

/**
 *
 * @author roros
 */

    /**
     * @param args the command line arguments
     */

import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class JavaApplication9 {
    private static void displayMenu() {
        System.out.println("Welcome to Iconic library");
        System.out.println("----------------------------");
        System.out.println("Choose 1 if you are admin");
        System.out.println("Choose 2 if you are borrower");
        System.out.println("Choose 3 if you are librarian");
        System.out.println("Choose 4 if you are supplier");
    }
    public static void edit(String username,String Passwrod,int id) throws IOException{
        System.out.println("choose 1 if you want to remove user");
        System.out.println("choose 2 if you want to edit user id");
        System.out.println("press 3 if you want to add a book");
        System.out.println("press 4 to remove book");
        System.out.println("press 5 to edit a book");
        System.out.println("press 6 to search a certain book");
        System.out.println("choose 7 if you want to add librian");
        System.out.println("choose 0 to exit");
            Admin admin = new Admin(username, Passwrod, id);
            Scanner s=new Scanner(System.in);
                        int choiceadmin;
                        choiceadmin = s.nextInt();
                        switch (choiceadmin) {
                            case 1:
                                System.out.println("Enter username of the user you want to remove");
                                String usertoremove = s.next();
                                admin.removeUser(usertoremove,true);
                                edit(username,Passwrod,id);
                            case 2:
                                System.out.println("Enter user username");
                                String user = s.next();
                                System.out.println("Enter user paswwrod");
                                String password = s.next();
                                System.out.println("Enter user id");
                                int userId = s.nextInt();
                                admin.editUser(new Librarian(user, password, userId));
                                edit(username,Passwrod,id);
                            case 3:
                                System.out.println("Enter book title ");
                                String bookTitle = s.next();
                                System.out.println("enter book id");
                                int bookid = s.nextInt();
                                System.out.println("enter book category");
                                String category = s.next();
                                System.out.println("enter book price");
                                int price = s.nextInt();
                                Book h = new Book(bookid, bookTitle, category, price);
                                admin.addbooks(h);
                                edit(username,Passwrod,id);
                            case 4:
                                System.out.println("enter book name to remove");
                                String booknameToRemove = s.next();
                                admin.removeBook(booknameToRemove,false);
                                edit(username,Passwrod,id);
                            case 5:
                                System.out.println("enter title of the book you want to edit");
                                booknameToRemove=s.next();
                                admin.removeBook(booknameToRemove,false);
                                 System.out.println("Enter book title ");
                                 bookTitle = s.next();
                                System.out.println("enter book id");
                                 bookid = s.nextInt();
                                System.out.println("enter book category");
                                 category = s.next();
                                System.out.println("enter book price");
                                 price = s.nextInt();
                               Book w= new Book(bookid, bookTitle, category, price);

                                admin.addbooks(w);
                                edit(username,Passwrod,id);
                                
                            case 6:
                                System.out.println("enter the book id you  want to search for");
                                bookid = s.nextInt();
                                
                                edit(username,Passwrod,id);
                            case 7:
                                System.out.println("Enter librian user name");
                                String UserName=s.next();
                                System.out.println("Enter librian passwrod");
                                String PASS=s.next();
                                System.out.println("Enter librian id");
                                int ID=s.nextInt();
                                Librarian l=new Librarian(UserName,PASS,ID);
                                admin.addLibrian(l);
                                edit(username,Passwrod,id);
                            case 0:
                                System.exit(0);
                                break;
                        }
    }
    public static void editt(){
        System.out.println("choose 1 to sign up");
        System.out.println("choose 2 to login ");
        System.out.println("choose 0 to exit");
    }
    public static void edit2(String username,String pass,int id){
        System.out.println("choose 1 to request a book");
        System.out.println("choose 2 to give a ratig to a book");
        System.out.println("choose 0 to exit");
        Borrower bo = new Borrower(username, pass, id);
         Scanner s=new Scanner(System.in);
        int choicebor = s.nextInt();
                    switch (choicebor) {
                        case 1:
                            System.out.println("enter the title of the book you want to request");
                            String title = s.next();
                            bo.Bookrequest(title);
                            edit2(username,pass,id);
                        case 2:
                            System.out.println("enter the id of the book you want to give a rating to");
                            int bookid = s.nextInt();
                            System.out.println("enter the rating");
                            int rating = s.nextInt();
                            bo.rateBook(bookid, rating);
                            edit2(username,pass,id);
                        case 0:
                            break;
                        default:
                            System.out.println("Invalid choice");
                            break;
                            
                    }
    }
    public static void edit3(){
        System.out.println("choose 1 to show requests");
//        System.out.println("choose 2 to add user");
        System.out.println("choose 3 edit book quantity");
        System.out.println("choose 0 to exit");
    }
    public static void main(String[] args) throws IOException {   
        while(true){
int choice = 0;
        editt();
        Scanner s = new Scanner(System.in);
        choice = s.nextInt();
        Borrower b;
        User u = new User() {};
        switch (choice) {

            case 1:
                System.out.println("Sign up");
                System.out.println("username");
                String username;
                username = s.next();
                System.out.println("password");
                                String pass;
pass = s.next();
                System.out.println("id");
                int id;
                id = s.nextInt();    
                b = new Borrower(username, pass, id);
                if(b.SignUp(b)){
                    System.out.println("Signed up successfully");
                }
                else System.out.println("Username exists!!!!");
                break;
                        
                
            case 2:
        System.out.println("Login");
        System.out.println("username");
        username = s.next();
        System.out.println("password");
        pass = s.next();
        System.out.println("id");
        id = s.nextInt();
        if (null == u.login(username, pass)) {
            System.out.println("Invalid user type");
        } else {
            String userType = u.login(username, pass);
            switch (userType) {
                case "Borrower":
                    edit2(username,pass,id);
                    
                    case "Librarian":
                        edit3();
                        int choicelib = 0;
                        choicelib = s.nextInt();
                        Librarian v = new Librarian(username, pass, id);
                        switch (choicelib) {
                            case 1:
                                System.out.println("press 1 to show requests");
                                v.Requests();
                                break;
//                            case 2:
//                                System.out.println("press 2 add user");
//                                Borrower n = new Borrower(username, pass, id);
//                                v.adduser(n);
//                                break;
                            case 3:
                                System.out.println("enter the title to edit book quantity");
                                String bookTitle = s.next();
                                System.out.println("enter the quantity");
                                int quantity = s.nextInt();
                                v.editBookQuantity(bookTitle, quantity);
                                break;
                            case 0:
                                System.exit(0);
                                break;
                        }
                        break;
                    case "Admin":
                        edit(username,pass,id);

                        break;
                    default:
                        System.out.println("Invalid user type");
                        break;
                }
              
        }
            case 0:
                System.exit(0);
        }
        }
    }
}
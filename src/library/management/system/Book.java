/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.management.system;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable {
    String title;
    int bookCounter = 0;
    String category;
    int price;
    int bookId;
    public Book(int id, String title, String category, int price) {
        this.title = title;
        this.category = category;
        this.price = price;
        this.bookId = id;
    }

    public Book() {

    }

    public int getBookId() {
        return bookId;
    }


    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return bookId + "," + title + "," + category + "," + price +  "\n";
    }
}







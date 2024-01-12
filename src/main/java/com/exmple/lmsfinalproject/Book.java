package com.exmple.lmsfinalproject;

public class Book {
    private int id;
    private String name;
    private String author;
    private int quantity;
    private String location;

    public Book(int id, String name, String author, int quantity, String location) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.quantity = quantity;
        this.location = location;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

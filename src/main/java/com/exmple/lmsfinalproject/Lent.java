package com.exmple.lmsfinalproject;

public class Lent {
         private int studentID;
         private String name;
         private String bookName;
         private int bookid;
            private String lendDate;
         private String returnDate;

         private long fines;

    public Lent(int studentID, String name, String bookName, int bookid, String lendDate, String returnDate, long fines) {
        this.studentID = studentID;
        this.name = name;
        this.bookName = bookName;
        this.bookid = bookid;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
        this.fines = fines;
    }

    public Lent() {
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getLendDate() {
        return lendDate;
    }

    public void setLendDate(String lendDate) {
        this.lendDate = lendDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public long getFines() {
        return fines;
    }

    public void setFines(int fines) {
        this.fines = fines;
    }
}

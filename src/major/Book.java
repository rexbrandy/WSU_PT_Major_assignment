package major;

public class Book {
  /*
    o a unique 6-digit numeric ID (barcode).
    o the book’s title
    o the book’s author
    o the book’s aisle
    o the book’s shelf
   */
  String barcode;
  String title;
  String author;
  String aisle;
  String shelf;
  
  public Book(String barcode, String title, String author, String aisle, String shelf) {
    this.barcode = barcode;
    this.title = title;
    this.author = author;
    this.aisle = aisle;
    this.shelf = shelf;
  }
  
  public String toCSV() {
    return this.barcode+","+this.title+","+this.author+","+this.aisle+","+this.shelf+"\r\n";
  }
  
  
}

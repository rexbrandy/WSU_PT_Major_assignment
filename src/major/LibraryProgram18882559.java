/*
 * Student ID: 18882559
 * Name: Bailey Yates Armitage
 * Campus: City
 * Tutor Name: Chris
 * Class Day: Wed
 * Class Time: 5:30
 */
package major;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Scanner;


public class LibraryProgram18882559 {
  private static ArrayList<Book> allBooks = new ArrayList<Book>();
  private static ArrayList<Member> allMembers = new ArrayList<Member>();
  private static ArrayList<Loan> allLoans = new ArrayList<Loan>();
  
  public static void main(String[] args) {
    Scanner kb = new Scanner(System.in);

    boolean exitFile = false;
    
    // Read files on start up
    String[] files = consumeFiles(kb);
    
    while(!exitFile) {
      int userChoice = printMenu(kb);
      
      switch(userChoice) {
        case(1):
          addBook(kb);
          break;
        case(2):
          addMember(kb);
          break;
        case(3):
          findBook(kb);
          break;
        case(4):
          overdueBookReport();
          break;
        case(5):
          borrowBook(kb);
          break;
        case(6):
          returnBook(kb);
          break;
        case(7):
          saveData(files);
          break;
        case(8):
          System.exit(0);
      }
      
      pressEnterToContinue(kb);
    }
    
    kb.close();
  }
  
  /* printMenu()
   * 
   * @param: Scanner(System.in); object
   * 
   * @return: Users choice as int
   * 
   * this will print the menu and 
   */
  static int printMenu(Scanner kb) {
    int userChoice = 0;
    
    System.out.println("1. Add a Book");
    System.out.println("2. Add a Member");
    System.out.println("3. Find Book Information");
    System.out.println("4. Overdue Book Report");
    System.out.println("5. Borrow a Book");
    System.out.println("6. Return a Book");
    System.out.println("7. Save");
    System.out.println("8. Exit");
    
    do {
      while (!kb.hasNextInt()) {
        System.out.println("That's not a number!");
        kb.next(); 
      }

      userChoice = kb.nextInt();
      kb.nextLine();
      
      if(userChoice < 1 || userChoice > 8) {
        System.out.println("That's not a valid choice");
      }
      
    } while(userChoice < 1 || userChoice > 8);
        
    return userChoice;
  }
  
  /*
   * addBook(kb)
   * 
   * @param Scanner(System.in);
   * 
   * this sub will prompt the user to input book information
   * it will then add the Book to allBooks
   */
  static void addBook(Scanner kb) {
    String barcode;
    String title;
    String author;
    String aisle;
    String shelf;
    
    do {
      System.out.println("Enter Books Barcode:");
      barcode = kb.nextLine();
    } while(validateBarcode(barcode)); 
    
    System.out.println("Enter Books Title:");
    title = kb.nextLine();
    
    System.out.println("Enter Books Author:");
    author = kb.nextLine();
    
    System.out.println("Enter Books Aisle:");
    aisle = kb.nextLine();
    
    System.out.println("Enter Books Shelf:");
    shelf = kb.nextLine();
        
    Book newBook = new Book(barcode, title, author, aisle, shelf);
   
    addToAllBooks(newBook);
  }
  
  /*
   * addMember(kb)
   * 
   * @param Scanner(System.in);
   * 
   * this sub will prompt the user to input member information
   * it will then add the Member to allMember
   */
  static void addMember(Scanner kb) {
    String barcode;
    String name;
    String address;
    String phone;
    String email;
    
    do {
      System.out.println("Enter Member Barcode:");
      barcode = kb.nextLine();
    } while(validateBarcode(barcode));
    
    System.out.println("Enter Member Full Name:");
    name = kb.nextLine();
    
    System.out.println("Enter Member Address:");
    address = kb.nextLine();
    
    System.out.println("Enter Member Phone Number:");
    phone = kb.nextLine();
    
    do {
    System.out.println("Enter Member Email Address:");
    email = kb.nextLine();
    } while(!email.contains("@"));
    
    Member newMember = new Member(barcode, name, address, phone, email);
    addToAllMembers(newMember);
    
  }
  
  /*
   * validateBarcode(barcode)
   * 
   * @param: barcode - this is a book barcode or member barcode
   * 
   * this will look through each book and member and see if this barcode
   * is taken or not
   */
  static boolean validateBarcode(String barcode) {
    if (barcode .length() != 6) {
      System.out.println("Barocde must be 6 digits");
      return true;
    }
    
    ArrayList<Book> allBooks = getAllBooks();
    ArrayList<Member> allMembers = getAllMembers();

    
    for (Book book: allBooks) {
      if (barcode.equals(book.barcode)) {
        System.out.println("Barcode already exists");
        return true;
      }
    }
    
    for (Member member: allMembers) {
      if (barcode.equals(member.barcode)) {
        System.out.println("Barcode already exists");
        return true;
      }
    }
    
    return false;
  }
  
  
  
  
  
  /*
   * findBook(kb)
   * 
   * @param: Scanner(System.in)
   * 
   * will promot user for book title and then search if there are any books that match
   * if they match they are added to a new ArrayList and handed to printBookInfo()
   */
  static void findBook(Scanner kb) {
    ArrayList<Book> allBooks = getAllBooks();
    ArrayList<Book> matchedBooks = new ArrayList<Book>();
    String bookTitle;
    
    System.out.println("Enter Book Title");
    
    bookTitle = kb.nextLine();
    

    for (Book eachBook: allBooks) {
      if (eachBook.title.toLowerCase().contains(bookTitle.toLowerCase())) {
        matchedBooks.add(eachBook);
      }
    }
    
    printBookInfo(matchedBooks);
  }
  
  /*
   * printBookInfo(matchedBooks)
   * 
   * @param: ArrayList<Book> books - this is an ArrayList of Books
   * 
   * this will print the header and the format the data in each book to be printed in line
   */
  static void printBookInfo(ArrayList<Book> books) {
    //                 |--------9-------------------------------34--------------------------28--------10--------10-----6
    System.out.println("ID       Title                            Author                      Aisle     Shelf     Status    ");
    System.out.println("----------------------------------------------------------------------------------------------------");
    for (Book eachBook: books) {
      System.out.print(eachBook.barcode+" ".repeat(9 - eachBook.barcode.length()));
      System.out.print(eachBook.title+" ".repeat(33 - eachBook.title.length()));
      System.out.print(eachBook.author+" ".repeat(28 - eachBook.author.length()));
      System.out.print(eachBook.aisle+" ".repeat(10 - eachBook.aisle.length()));
      System.out.print(eachBook.shelf+" ".repeat(10 - eachBook.shelf.length()));
      System.out.print(getBookStatus(eachBook.barcode));
      System.out.print("\r\n");
    }
  }
  
  /*
   * getBookStatus(barcode)
   * 
   * @param: barcode - this is a book barcode
   * 
   * @return Strong
   * 
   * returns books status duh
   */
  static String getBookStatus(String barcode) {
    ArrayList<Loan> allLoans = getAllLoans();
    
    for (Loan loan: allLoans) {
      if (loan.bookBarcode.equals(barcode)) {
        return "rented";
      }
    }
    
    return "avaliable";
  }
  
  
  
  
  
/*
 * overdueBookReport()
 * 
 * this will loop over all the loans and look if they're overdue
 * if they are they're added to a new ArrayList and given to printLoanInfo()
 */
  static void overdueBookReport() {
    ArrayList<Loan> allLoans = getAllLoans();
    ArrayList<Loan> overdueLoans = new ArrayList<Loan>();
    
    for (Loan eachLoan: allLoans) {
      if (eachLoan.isOverDue()) {
        overdueLoans.add(eachLoan);
      }
    }
    
    printLoanInfo(overdueLoans);
  }
  
  /*
   * printLoanInfo(overdueLoans)
   * 
   * @param: ArrayList<Loan> loans - this is an ArrayList of Loans
   * 
   * this will print the header and the format the data in each loan to be printed in line
   */
  static void printLoanInfo(ArrayList<Loan> loans) {
    // find length of each "column"
    //                 |--------------------------28-------------------------28----------------------25---------------17
    System.out.println("Book Title                  Book Author                Member Name             Member Phone     Days Overdue");
    System.out.println("------------------------------------------------------------------------------------------------------------");
    for (Loan loan: loans) {
      String memberName = getMembersName(loan.memberBarcode);
      String memberPhone = getMembersPhone(loan.memberBarcode);
      String bookAuthor = getBookAuthor(loan.bookBarcode);
      
      // minus length of variable from size of column
      System.out.print(loan.bookBarcode+" ".repeat(28 - loan.bookBarcode.length()));
      System.out.print(bookAuthor+" ".repeat(27 - bookAuthor.length()));
      System.out.print(memberName+" ".repeat(24 - memberName.length()));
      System.out.print(memberPhone+" ".repeat(17 - memberPhone.length()));
      System.out.print(loan.daysOverdue);
      System.out.print("\r\n");
    }
  }
  
  /*
   * getMembersName(barcode)
   * 
   * @param: String barcode - this is a members barcode
   * 
   * @return: String - members name or black string
   * 
   * will look up a members name using their barcode
   */
  static String getMembersName(String barcode) {
    ArrayList<Member> allMembers = getAllMembers();
    
    for (Member member: allMembers) {
      if (member.barcode.equals(barcode)) {
        return member.name;
      }
    }
    
    return "";
  }
  
  /*
   * getMembersPhone(barcode)
   * 
   * @param: String barcode - this is a members barcode
   * 
   * @return: String - members phone or black string
   * 
   * will look up a members phone using their barcode
   */
  static String getMembersPhone(String barcode) {
    ArrayList<Member> allMembers = getAllMembers();
    
    for (Member member: allMembers) {
      if (member.barcode.equals(barcode)) {
        return member.phone;
      }
    }
    
    return "";
  }
  
  /*
   * getBookAuthor(barcode)
   * 
   * @param: String barcode - this is a books barcode
   * 
   * @return: String - authors name or black string
   * 
   * will look up a books author using their barcode
   */
  static String getBookAuthor(String barcode) {
    ArrayList<Book> allBooks = getAllBooks();
    
    for (Book book: allBooks) {
      if (book.barcode.equals(barcode)) {
        return book.author;
      }
    }
    
    return "";
  }
  
  
  
  
  
  /*
   * borrowBook(kb)
   * 
   * @param: Scanner(System.in)
   * 
   * this will prompt a user for their member barcode and the book barcode
   * if they are allowed to borrow and the book is available it will create a new loan
   * and add to all loans
   */
  static void borrowBook(Scanner kb) {
    String bookBarcode;
    String memberBarcode;
    
    System.out.println("Enter Member barcode");
    memberBarcode = kb.nextLine();
    
    if (!isAllowedToBorrow(memberBarcode)) {
      return;
    }
    
    System.out.println("Enter Book Barcode");
    bookBarcode = kb.nextLine();
    
    if (!isBookAvaliable(bookBarcode)) {
      return;
    }
    
    
    LocalDate borrowDate = LocalDate.now();
    LocalDate dueDate = borrowDate.plusDays(14);
    
    Loan newLoan = new Loan(bookBarcode, memberBarcode, borrowDate, dueDate, "no");

    addToAllLoans(newLoan);
  }
  
  /*
   * isBookAllowedToBorrow(barcode)
   * 
   * @param: String barcode - this is the members barcode
   * 
   * @return: boolean
   * 
   * looks through all loans to see 
   * if member has 3 books already loaned
   * if they have any overdue books
   */
  static boolean isAllowedToBorrow(String barcode) {
    ArrayList<Loan> allLoans = getAllLoans();

    int count = 0;
    
    for (Loan loan: allLoans) {
      if (loan.memberBarcode.equals(barcode)) {
        count++;
        
        if (loan.isOverDue()) {
          System.out.print("Sorry, you must return your overdue book/s first");
          return false;
        }
      }
    }
    
    if (count >= 3) {
      System.out.print("Sorry, you have rented too many books");
      return false;
    }
    
    return true;
  }
  
  /*
   * isBookAvaliable(barcode)
   * 
   * @param: barcode - this is the books barcode
   * 
   * @return: boolean
   * 
   * looks through all loans to see if book is already rented
   */
  static boolean isBookAvaliable(String barcode) {
    ArrayList<Loan> allLoans = getAllLoans();
    
    for (Loan loan: allLoans) {
      if (loan.bookBarcode.equals(barcode)) {
        System.out.print("Sorry this book has already been rented");
        return false;
      }
    }
    
    return true;
  }
  
  
  
 
  
  /*
   * returnBook()
   * 
   * @param: Scanner(System.in);
   * 
   * This will ask the user for a barcode then see if the book has been loaned
   * if the book hasn't been loaned it will return to the main menu.
   */
  static void returnBook(Scanner kb) {
    ArrayList<Loan> allLoans = getAllLoans();
    String bookBarcode;
    
    System.out.println("Enter Book Barcode");
    bookBarcode = kb.nextLine();
    
    if (!isBookLoaned(bookBarcode)) {
      return;
    }
    
    for(Loan eachLoan: allLoans) {
      if (eachLoan.bookBarcode.equals(bookBarcode) && eachLoan.returned.equals("no")) {
        eachLoan.returned = "yes";
        eachLoan.returnedDate = LocalDate.now();
        
        System.out.println("You have returned: "+getBookName(eachLoan.bookBarcode));
        System.out.println("Thank you");        
      }
    }
    
    setAllLoans(allLoans);
  }
  
  /*
   * getBookStatus(barcode)
   * 
   * @param: barcode - this is a book barcode
   * 
   * @return Strong
   * 
   * returns books name duh
   */
  static String getBookName(String barcode) {
    ArrayList<Book> allBooks = getAllBooks();
    
    for (Book book: allBooks) {
      if (book.barcode.equals(barcode)) {
        return book.title;
      }
    }
    
    return "";
  }
  
  /*
   * isBookLoaned(barcode);
   * 
   * @param: String barcode - this should be the barcode of a book
   * 
   * @return: boolean
   * 
   * this will loop through all the loans and see if there are any loans with this bookBarcode
   * if the book isn't found it will display an error message and return false
   */
  static boolean isBookLoaned(String barcode) {  
    for(Loan eachLoan: allLoans) {
      if (eachLoan.bookBarcode.equals(barcode)) {
        return true;
      }
    }
    
    System.out.print("Sorry this book hasn't been rented");
    return false;
  }
  
  
  
 
  
  /*
   * saveData()
   * 
   * TODO
   * save all files!!!!!
   */
  static void saveData(String[] files) {
    for (int i = 0; i < files.length; i++) {
    
      File fileToSave = new File(files[i]);
      FileWriter bookWriter;


      try {
        bookWriter = new FileWriter(fileToSave, false);
        
        switch(i) {
          case(0):
            ArrayList<Book> allBooks = getAllBooks();
            for(Book eachBook: allBooks) {
              bookWriter.write(eachBook.toCSV());
            }
            break;
          case(1):
            ArrayList<Member> allMembers = getAllMembers();
            for(Member eachMember: allMembers) {
              bookWriter.write(eachMember.toCSV());
            }
            break;
          case(2):
            ArrayList<Loan> allLoans = getAllLoans();
            for(Loan eachLoan: allLoans) {
              bookWriter.write(eachLoan.toCSV());
            }
            break;
        }
        

        
        bookWriter.close();
      } catch (IOException e) {
        System.out.print("Sorry there was an error saving: "+e);
      }
      
      System.out.println("Done saving files");
    }
  }
  
  /*
   * consumeFiles()
   * 
   * looks for "books.txt", "members.txt", "loans.txt" files
   * 
   * TODO
   * Prompt user if file not found
   */
  static String[] consumeFiles() {
    String[] files = {"books.txt", "members.txt", "loans.txt"};
    
    for (int i = 0; i < files.length; i++) {
      String fileName = files[i];

      File dataFile = new File(fileName);
      Scanner fileReader = null;
      
      if(!dataFile.exists()) {
        System.out.println("Sorry, the File: "+fileName+" was not found");
        System.out.println("Please enter a new File:");


      }
      
      try {
        fileReader = new Scanner(dataFile);
      } catch (FileNotFoundException e) {
        System.out.println("Sorry, the File: "+fileName+" was not found");
        System.exit(0);
      }
  
      while (fileReader.hasNextLine()) {
        String line = fileReader.nextLine();
        String[] data = line.split(",");
        
        
        // Create new instance of Model (Book/Member/Loan) based on i from for loop        
        switch(i) {
          case(0):
            Book newBook = new Book(data[0], data[1], data[2], data[3], data[4]);
            
            addToAllBooks(newBook);
            break;
          case(1):
            Member newMember = new Member(data[0], data[1], data[2], data[3], data[4]);
            
            addToAllMembers(newMember);
            break;
          case(2):
            Loan newLoan = new Loan(
              data[0],
              data[1],
              LocalDate.parse(data[2]),
              LocalDate.parse(data[3]),
              data[4]
            );
            
            if (data[4] == "yes") {
              newLoan.setReturnedDate(data[5]);
            }

            addToAllLoans(newLoan);
            break;
        }

      }
    }
    
    return files;
  }
  
  
  

  
  /*
   * pressEnterToContinue(kb)
   * 
   * @param: Scanner(System.in);
   * 
   * This is used as a prompt to show the main menu.
   */
  static void pressEnterToContinue(Scanner kb) {
    System.out.println("");
    System.out.println("Press Enter to Continue");
    kb.nextLine();
  }
  
  
  
  /*
   *  Setters and getters
   */

  // Books
  public static ArrayList<Book> getAllBooks() {
    return allBooks;
  }

  public static void setAllBooks(ArrayList<Book> allBooks) {
    LibraryProgram18882559.allBooks = allBooks;
  }
  
  public static void addToAllBooks(Book newBook) {
    ArrayList<Book> allBooks = getAllBooks();
    allBooks.add(newBook);
    setAllBooks(allBooks);
  }
  
  
  // Members
  public static ArrayList<Member> getAllMembers() {
    return allMembers;
  }

  public static void setAllMembers(ArrayList<Member> allMembers) {
    LibraryProgram18882559.allMembers = allMembers;
  }
  
  public static void addToAllMembers(Member newMember) {
    ArrayList<Member> allMembers = getAllMembers();
    allMembers.add(newMember);
    setAllMembers(allMembers);
  }
  
  // Loans
  public static ArrayList<Loan> getAllLoans() {
    return allLoans;
  }

  public static void setAllLoans(ArrayList<Loan> allLoans) {
    LibraryProgram18882559.allLoans = allLoans;
  }
  
  public static void addToAllLoans(Loan newLoan) {
    ArrayList<Loan> allLoans = getAllLoans();
    allLoans.add(newLoan);
    setAllLoans(allLoans);
  }
}

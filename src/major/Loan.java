package major;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class Loan {
  String bookBarcode;
  String memberBarcode;
  LocalDate checkoutDate;
  LocalDate dueDate;
  String returned;
  LocalDate returnedDate;
  long daysOverdue;
  
  public Loan(
      String bookBarcode, 
      String memberBarcode, 
      LocalDate checkoutDate,
      LocalDate dueDate,
      String returned
  ) {
    this.bookBarcode = bookBarcode;
    this.memberBarcode = memberBarcode;
    this.checkoutDate = checkoutDate;
    this.dueDate = dueDate;
    this.returned = returned;
    
    if (this.isOverDue()) {
      this.setDaysOverDue();
    }
  }
  
  public String toCSV() {
    return this.bookBarcode+","+this.memberBarcode+","+this.checkoutDate+","+this.dueDate+","+this.returned+","+this.returnedDate+"\r\n";
  }
  
  public void setReturnedDate(String returnedDate) {
    this.returnedDate = LocalDate.parse(returnedDate);
  }
  
  public void setDaysOverDue() {
    LocalDate todaysDate = LocalDate.now();

    this.daysOverdue = ChronoUnit.DAYS.between(this.dueDate, todaysDate);
  }
  
  public boolean isOverDue() {
    LocalDate todaysDate = LocalDate.now();

    if (this.dueDate.compareTo(todaysDate) < 0 && this.returned.equals("no")) {
      return true;
    }
    
    return false;
  }
}

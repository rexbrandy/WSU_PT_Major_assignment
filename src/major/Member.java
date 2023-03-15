package major;

public class Member {
  /*
    o a unique 6-digit numeric identifier (barcode) for the individual
    o the member’s full name (first and last name stored together as one field)
    o the member’s address (street number, street, and suburb stored together as one
    field)
    o the member’s phone number
    o the member’s email address
  */
  String barcode;
  String name;
  String address;
  String phone;
  String email;
  
  public Member(String barcode, String name, String address, String phone, String email) {
    this.barcode = barcode;
    this.name = name;
    this.address = address;
    this.phone = phone;
    this.email = email;
  }
  
  public String toCSV() {
    return this.barcode+","+this.name+","+this.address+","+this.phone+","+this.email+"\r\n";
  }
}

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Address {
  private String street;
  private String city;
  private String state;
  private int zip;
  private int id;
  private int personId;

  public Address(String street, String city, String state, int zip, int personId) {
    this.street = street;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.personId = personId;
  }

  public int getPersonId() {
    return personId;
  }

  public String getStreet() {
    return street;
  }

  public String getState() {
    return state;
  }

  public String getCity() {
    return city;
  }

  public int getZip() {
    return zip;
  }

  public int getId() {
    return id;
  }

  public static Address find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM address where id=:id";
      Address Address = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Address.class);
      return Address;
    }
  }

  public static List<Address> all() {
    String sql = "SELECT id, street, city, state, zip, personId FROM address";
    try(Connection con = DB.sql2o.open()) {
     return con.createQuery(sql).executeAndFetch(Address.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO address(street, city, state, zip, personId) VALUES (:street, :city, :state, :zip, :personId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("street", this.street)
        .addParameter("city", this.city)
        .addParameter("state", this.state)
        .addParameter("zip", this.zip)
        .addParameter("personId", this.personId)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherAddress){
    if (!(otherAddress instanceof Address)) {
      return false;
    } else {
      Address newAddress = (Address) otherAddress;
      return this.getStreet().equals(newAddress.getStreet()) &&
             this.getId() == newAddress.getId() &&
             this.getCity().equals(newAddress.getCity()) &&
             this.getState().equals(newAddress.getState()) &&
             this.getZip() == newAddress.getZip() &&
             this.getPersonId() == newAddress.getPersonId();
    }
  }
}

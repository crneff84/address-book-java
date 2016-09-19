import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Person {
  private String name;
  private int id;

  public Person(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static List<Person> all() {
    String sql = "SELECT id, name FROM person";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Person.class);
    }
  }

  public int getId() {
    return id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO person(name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Person find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM person where id=:id";
      Person Person = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Person.class);
      return Person;
    }
  }

  @Override
   public boolean equals(Object otherPerson) {
     if (!(otherPerson instanceof Person)) {
       return false;
     } else {
       Person newPerson = (Person) otherPerson;
       return this.getName().equals(newPerson.getName()) &&
              this.getId() == newPerson.getId();
     }
   }

  public List<Address> getAddress() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM address where personId=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Address.class);
    }
  }
}

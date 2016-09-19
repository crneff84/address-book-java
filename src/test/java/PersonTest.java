import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class PersonTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/add_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteAddressQuery = "DELETE FROM address *;";
      String deletePersonQuery = "DELETE FROM person *;";
      con.createQuery(deleteAddressQuery).executeUpdate();
      con.createQuery(deletePersonQuery).executeUpdate();
    }
  }

  @Test
  public void getAddress_retrievesAllAddressesFromDatabase_addressesList() {
    Person myPerson = new Person("Bob Test");
    myPerson.save();
    Address firstAddress = new Address("122 one st", "Paso Robles", "CA", 93446, myPerson.getId());
    firstAddress.save();
    Address secondAddress = new Address("122 Two st", "Paso Robles", "CA", 93446, myPerson.getId());
    secondAddress.save();
    Address[] addresses = new Address[] { firstAddress, secondAddress };
    assertTrue(myPerson.getAddress().containsAll(Arrays.asList(addresses)));
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
   Person firstPerson = new Person("Bob Test");
   Person secondPerson = new Person("Bob Test");
   assertTrue(firstPerson.equals(secondPerson));
 }

  @Test
  public void person_instantiatesCorrectly_true() {
    Person testPerson = new Person("bob");
    assertEquals(true, testPerson instanceof Person);
  }

  @Test
  public void getName_personInstantiatesWithName_Home() {
    Person testPerson = new Person("bob");
    assertEquals("bob", testPerson.getName());
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Person myPerson = new Person("Bob Test");
    myPerson.save();
    assertTrue(Person.all().get(0).equals(myPerson));
  }

  @Test
  public void all_returnsAllInstancesOfPerson_true() {
    Person firstPerson = new Person("Home");
    firstPerson.save();
    Person secondPerson = new Person("Work");
    secondPerson.save();
    assertEquals(true, Person.all().get(0).equals(firstPerson));
    assertEquals(true, Person.all().get(1).equals(secondPerson));
  }

  @Test
  public void save_assignsIdToObject() {
    Person myPerson = new Person("Bob Test");
    myPerson.save();
    Person savedPerson = Person.all().get(0);
    assertEquals(myPerson.getId(), savedPerson.getId());
  }

  @Test
  public void getId_peopleInstantiateWithAnId_1() {
    Person testPerson = new Person("Home");
    testPerson.save();
    assertTrue(testPerson.getId() > 0);
  }

  @Test
  public void find_returnsPersonWithSameId_secondPerson() {
    Person firstPerson = new Person("Home");
    firstPerson.save();
    Person secondPerson = new Person("Work");
    secondPerson.save();
    assertEquals(Person.find(secondPerson.getId()), secondPerson);
  }
}

import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class AddressTest {

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
  public void Address_instantiatesCorrectly_true() {
    Address myAddress = new Address("122 one st", "Paso Robles", "CA", 93446, 1);
    assertEquals(true, myAddress instanceof Address);
  }

  @Test
  public void Address_instantiatesWithStreet_String() {
    Address myAddress = new Address("122 one st", "Paso Robles", "CA", 93446, 1);
    assertEquals("122 one st", myAddress.getStreet());
  }

  @Test
  public void Address_instantiatesWithCity_String() {
    Address myAddress = new Address("122 one st", "Paso Robles", "CA", 93446, 1);
    assertEquals("Paso Robles", myAddress.getCity());
  }

  @Test
  public void Address_instantiatesWithState_String() {
    Address myAddress = new Address("122 one st", "Paso Robles", "CA", 93446, 1);
    assertEquals("CA", myAddress.getState());
  }

  @Test
  public void Address_instantiatesWithZip_String() {
    Address myAddress = new Address("122 one st", "Paso Robles", "CA", 93446, 1);
    assertEquals(93446, myAddress.getZip());
  }

  @Test
  public void all_returnsAllInstancesOfAddress_true() {
    Address firstAddress = new Address("122 one st", "Paso Robles", "CA", 93446, 1);
    firstAddress.save();
    Address secondAddress = new Address("122 one st", "Paso Robles", "CA", 93446, 2);
    secondAddress.save();
    assertEquals(true, Address.all().get(0).equals(firstAddress));
    assertEquals(true, Address.all().get(1).equals(secondAddress));
  }

  // @Test
  // public void clear_emptiesAllAddresssFromArrayList_0() {
  //   Address myAddress = new Address("122 one st", "Paso Robles", "CA", 93446, 1);
  //   assertEquals(Address.all().size(), 0);
  // }

  @Test
  public void getId_AddresssInstantiateWithAnID_1() {
    Address myAddress = new Address("122 one st", "Paso Robles", "CA", 93446, 1);
    myAddress.save();
    assertTrue(myAddress.getId() > 0);
  }

  @Test
  public void find_returnsAddressWithSameId_secondAddress() {
    Address firstAddress = new Address("122 one st", "Paso Robles", "CA", 93446, 1);
    firstAddress.save();
    Address secondAddress = new Address("122 one st", "Paso Robles", "CA", 93446, 2);
    secondAddress.save();
    assertEquals(Address.find(secondAddress.getId()), secondAddress);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Address firstAddress = new Address("122 one st", "Paso Robles", "CA", 93446, 1);
    Address secondAddress = new Address("122 one st", "Paso Robles", "CA", 93446, 1);
    assertTrue(firstAddress.equals(secondAddress));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Address myAddress = new Address("122 one st", "Paso Robles", "CA", 93446, 1);
    myAddress.save();
    assertTrue(Address.all().get(0).equals(myAddress));
  }

  @Test
  public void save_assignsIdToObject() {
    Address myAddress = new Address("122 one st", "Paso Robles", "CA", 93446, 1);
    myAddress.save();
    Address savedAddress = Address.all().get(0);
    assertEquals(myAddress.getId(), savedAddress.getId());
  }

  @Test
  public void save_savesPersonIdIntoDB_true() {
    Person myPerson = new Person("bob");
    myPerson.save();
    Address myAddress = new Address("122 one st", "Paso Robles", "CA", 93446, myPerson.getId());
    myAddress.save();
    Address savedAddress = Address.find(myAddress.getId());
    assertEquals(savedAddress.getPersonId(), myPerson.getId());
  }
}

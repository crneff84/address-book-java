import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("persons/:id/addresses/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Person person = Person.find(Integer.parseInt(request.params(":id")));
      model.put("person", person);
      model.put("template", "templates/person-addresses-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/addresses", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("addresses", Address.all());
      model.put("template", "templates/addresses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/addresses", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Person person = Person.find(Integer.parseInt(request.queryParams("personId")));
      String street = request.queryParams("street");
      String city = request.queryParams("city");
      String state = request.queryParams("state");
      int zip = Integer.parseInt(request.queryParams("zip"));
      Address newAddress = new Address(street, city, state, zip, person.getId());
      newAddress.save();
      model.put("person", person);
      model.put("template", "templates/person-addresses-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/addresses/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Address address = Address.find(Integer.parseInt(request.params(":id")));
      model.put("addresses", address);
      model.put("template", "templates/address.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/persons/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/person-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/persons", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Person newPerson = new Person(name);
      newPerson.save();
      model.put("template", "templates/person-success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/persons", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("persons", Person.all());
      model.put("template", "templates/persons.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/persons/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Person person = Person.find(Integer.parseInt(request.params(":id")));
      model.put("person", person);
      model.put("template", "templates/person.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}

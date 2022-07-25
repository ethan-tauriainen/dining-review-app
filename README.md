# dining-review-app
Small project to demonstrate RESTful APIs with Spring Boot

## Project Objectives
  Construct a RESTful web API with data persistence using Spring and Spring Data JPA\
  Use Spring Initializr to generate the initial Java project\
  Configure application properties for certain dependencies, including the H2 embedded database\
  Define the entities that comprise this application scenario\
  Define the repositories that enable creating, updating, and querying these different entities\
  Define the API contracts that will enable this application scenario\
  Leverage the convenience of Lombok

## Summary
This API consists of three tables: users, restaurants, and dining reviews. A user may be added/updated. A restaurant may be added
assuming one does not currently exist with the same name and zipcode. A dining review may be submitted. A review consists of
several scores by which the restaurant it's associated with may be judged. Reviews must be approved by an admin (through the admin
endpoint). Upon approval, the restaurants overall scores will be computed.

Restaurants can be queried based on id, zipcode and allergy type.

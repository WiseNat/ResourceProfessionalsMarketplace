# Prerequisites

1. Java 8 JDK
2. Apache Maven
3. MySQL Database Server
4. Windows 10

# Set up

1. Make a database schema in your MySQL database server
2. Navigate to `./src/main/resources/application.properties`
3. Set `spring.datasource.url` to your JDBC connection string without any URL parameters except the database schema name
    1. E.g. "jdbc:mysql://localhost:3306/myschema"
   2. **DO NOT** include a username in your JDBC connection string
4. Set up a database account with the DBA role in your MySQL database
    1. Set `spring.datasource.username` to the database account username
    2. Set `spring.datasource.password` to the database account password
5. Set `spring.jpa.properties.hibernate.default_schema` to the same database schema name as your JDBC URL
6. Run `mvn clean install spring-boot:run` to...
    1. Install all necessary Maven dependencies
    2. Build and run the project
    3. Set up your database schema with the relevant tables and data

# Additional Configuration for application.properties

| Property                                             | Description                                                                                              | Values                                      |
|------------------------------------------------------|----------------------------------------------------------------------------------------------------------|---------------------------------------------|
| spring.jpa.properties.hibernate.hbm2ddl.auto         | Hibernate database generation type.                                                                      | none, create, create-drop, validate, update |
| database-populator.create-fake-data                  | Whether to populate the database with fake data or not                                                   | true, false                                 |
| database-populator.fake-unapproved-accounts-quantity | The amount of fake unapproved accounts to create; only valid if database-populator.create-fake-data=true | Any int                                     |
| database-populator.fake-available-resources-quantity | The amount of fake available resources to create; only valid if database-populator.create-fake-data=true | Any int                                     |
| database-populator.fake-loaned-resources-quantity    | The amount of fake loaned resources to create; only valid if database-populator.create-fake-data=true    | Any int                                     |


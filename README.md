# Mini Wrangler
Mini-wrangler system to massage a csv file as specified by given DSL based on ANTLR4.

## Usage

- Run `mvn package` to generate the ANTLR4 parser
- Import the MySQL schema in `main/java/resources/schema.sql`
- Update the MySQL connection details in `main/java/resources/configuraiton.ini`
- Start on App.java for a demonstration on how to use the library

## Transformations DSL

- A sample DSL for transformations is provided in `main/java/resources/transformations.dsl`
- The grammar and lexer describing the DSL rules and syntax can be founded in `src\main\antlr4\org\luisa\miniwrangler`

## Regex grammar

- Java Regex patterns are supported, which can be used to skip data
- In addition, sugar patterns are supported through a mapped pattern util

## CSV parser

- ANTLR4 is also used for parsing CSV
- A grammar and lexer descriving CSV parsing rules can be found in `src\main\antlr4\org\luisa\miniwrangler`

## CSV Data

- CSV data sample is in `main/java/resources/orders.csv`
- No orders are created from this sample
- Data values that do not match the provided pattern (if one) are skipped

## Assumptions

- CSV data has a header row with field names

## Tests

- App.java runs and saves orders for the given data and dsl samples.

## Dependencies

- MariaDB as JBDC driver
- ANTLR4 for DSL and CSV processing and parsing support
- JUnit for unit tests

## Javadoc

- Javadoc is under folder 'doc', containing additional usage, assumptions and implementation notes

## Future Work

- Refactor so as to abstract the type order (details given in the source code marked with // TODO comments), making it possible to easily reuse for other type of data
- Create a utility to support database schema creation from the dsl or import schema from database (most likelly to bring a dependency such as QueryDSL in and extend it to fit the purpose) 
- Extend the DSL grammar/lexer to allow setting up formatters for representation of the target object in stdout
- Extend the DSL grammar/lexer to allow a filter regex construct that allows a user to parse only the objects that match that filter
- Extend to support other DB languages, for example, it would be quite interesting to support Redis
- Extend to support Map<>Reduce processor in order to allow parallel working with BigData
- Extend to support Reactive streams and Publisher/Subscribers
- Add proper JUnit Test suite with rich set of examples
- Provide proper technical documentation, such as class diagram and interaction diagrams

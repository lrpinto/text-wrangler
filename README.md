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

- No coverage

## Dependencies

- MariaDB as JBDC driver
- ANTLR4 for DSL and CSV processing and parsing support
- JUnit for unit tests

# Javadoc

- Javadoc is under folder 'docs', containing additional usage, assumptions and implementation notes



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

- Only a limited set of patterns is being supported
- A regex grammar to support the use of regular expressions is on the way, which should allow the use of any RegEx

## CSV parser

- ANTLR4 is also used for parsing CSV
- A grammar and lexer descriving CSV parsing rules can be found in `src\main\antlr4\org\luisa\miniwrangler`

## CSV Data

- CSV data sample is in `main/java/resources/orders.csv`
- No orders are created from this sample, as none of the lines fully matches the expected patterns, e.g. the given pattern for 'Product Number' was `[A-Z0-9]+` does not match any of the product number values as it doesn't allow the `-`, as such all lines are ignored
- Datasamples that match the given patterns need to be created for testing, or the patterns need to be extended (to be solved with RegEx support)

## Assumptions

- CSV data has a header row with field names
- more...

## Tests

- No coverage

## Dependencies

- MariaDB as JBDC driver
- ANTLR4 for DSL and CSV processing and parsing support
- JUnit for unit tests
- Hibernator was added to support validation and in order to avoid writing own ValidateUtil, but it is to slow and it will be replaced by own ValidateUtil.



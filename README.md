# mini-wrangler
Mini-wrangler system to massage a csv file as specified by given DSL base on ANTLR4.

# usage

- Run mvn package to generate the ANTLR4 parser
- Import the MySQL schema in main/java/resources/schema.sql
- Update the MySQl connection details in main/java/resources/configuraiton.ini
- Start on App.java for a demonstration on how to use the library

# transformations

- A sample DSL for transformations is provided in main/java/resources/transformations.dsl
- The grammar and lexer describing the DSL rules and syntax can be founded in src\main\antlr4\org\luisa\miniwrangler

# csv parser

- ANTLR4 is also used for parsing CSV
- A grammar and lexer descriving CSV parsing rules can be found in src\main\antlr4\org\luisa\miniwrangler

# regex grammar

- A regex grammar to support the use of regular expressions is on the way

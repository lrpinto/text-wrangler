parser grammar TextTransformParser;

options {
	tokenVocab = TextTransformLexer;
}

transformations: transformation+ EOF;
transformation: line NEWLINE;
line: source target format?;
srcField: STRING_LITERAL;
properCase: PROPERCASE;
rename: RENAME srcField;
concat: CONCAT srcField+;
value: STRING_LITERAL;
fixedValue: FIXEDVALUE value;
source: (properCase? rename | concat | fixedValue);
orderField: (ORDERID | ORDERDATE | PRODUCTID | PRODUCTNAME | QUANTITY | UNIT);
target: AS orderField;
pattern: REGEX;
format: MATCH pattern+;
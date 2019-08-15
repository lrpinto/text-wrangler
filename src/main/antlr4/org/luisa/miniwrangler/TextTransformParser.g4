parser grammar TextTransformParser;

options {
	tokenVocab = TextTransformLexer;
}

transformations: transformation+ EOF;
transformation: line NEWLINE;
line: source target format?;
srcField: STRING_LITERAL;
properCase: PROPER_CASE;
rename: RENAME srcField;
delimeter: STRING_LITERAL;
concatenator: WITH delimeter;
concat: CONCAT concatenator? srcField+;
value: STRING_LITERAL;
fixedValue: FIXED_VALUE value;
source: (properCase? rename | concat | fixedValue);
orderField: (ORDER_ID | ORDER_DATE | PRODUCT_ID | PRODUCT_NAME | QUANTITY | UNIT);
targetField: orderField;
target: AS targetField;
expr	: TERM # TermNode
        | expr QUESTION_MARK # OptionalNode
        | OPEN_BRACKET expr CLOSE_BRACKET # OrdinaryNode
        | expr expr # ConcatNode
        | expr VERTICAL_BAR expr # OrNode
        ;
pattern: (expr | MAPPED_PATTERN );
format: MATCH pattern+;

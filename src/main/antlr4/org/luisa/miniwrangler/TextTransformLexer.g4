lexer grammar TextTransformLexer;

FIXED_VALUE: 'fixedValue';
PROPER_CASE: 'properCase';
CONCAT: 'concat';
RENAME: 'rename';
AS: 'as';
MATCH: 'match';

ORDER_ID: 'OrderID';
ORDER_DATE: 'OrderDate';
PRODUCT_ID: 'ProductId';
PRODUCT_NAME: 'ProductName';
QUANTITY: 'Quantity';
UNIT: 'Unit';

MAPPED_PATTERN: ('d+' | 'YYYY' | 'MM' | 'dd' | '[A-Z0-9]+' | '[A-Z]+' | '#,##0.0#');
STRING_LITERAL: [A-Za-z0-9._$]+ | DQUOTA_STRING | SQUOTA_STRING | BQUOTA_STRING;
NEWLINE: '\r'? '\n';
TERM: ([a-zA-Z0-9,.*^+\-&'":><#![\]] | ESC)+;
WS: (' '|'\t')+ {skip();};
OPEN_BRACKET: '(';
CLOSE_BRACKET: ')';
VERTICAL_BAR: '|';
QUESTION_MARK: '?';

fragment DQUOTA_STRING: '"' ( '\\'. | '""' | ~('"'| '\\') )* '"';
fragment SQUOTA_STRING: '\'' ('\\'. | '\'\'' | ~('\'' | '\\'))* '\'';
fragment BQUOTA_STRING: '`' ( '\\'. | '``' | ~('`'|'\\'))* '`';
fragment ESC: '\\' . ;


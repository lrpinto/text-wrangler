lexer grammar TextTransformLexer;

FIXEDVALUE: 'fixedValue';
PROPERCASE: 'properCase';
CONCAT: 'concat';
RENAME: 'rename';
AS: 'as';
MATCH: 'match';

ORDERID: 'OrderID';
ORDERDATE: 'OrderDate';
PRODUCTID: 'ProductId';
PRODUCTNAME: 'ProductName';
QUANTITY: 'Quantity';
UNIT: 'Unit';

REGEX: 'd+' | 'YYYY' | 'MM' | 'dd' | '[A-Z0-9]+' | '[A-Z]+' | '#,##0.0#';
STRING_LITERAL: [A-Za-z0-9._$]+ | DQUOTA_STRING | SQUOTA_STRING | BQUOTA_STRING;
NEWLINE: '\r'? '\n';
WS: (' '|'\t')+ {skip();};

fragment DQUOTA_STRING: '"' ( '\\'. | '""' | ~('"'| '\\') )* '"';
fragment SQUOTA_STRING: '\'' ('\\'. | '\'\'' | ~('\'' | '\\'))* '\'';
fragment BQUOTA_STRING: '`' ( '\\'. | '``' | ~('`'|'\\'))* '`';
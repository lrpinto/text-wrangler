rename 'Order Number' as OrderID match \d+
concat with - Year Month Day as OrderDate match YYYY MM dd
rename 'Product Number' as ProductId match [A-Z0-9-]+
properCase rename 'Product Name' as ProductName match [A-Za-z\s]+
rename Count as Quantity match #,##0.0#
fixedValue kg as Unit

Current schema looks like this
https://docs.google.com/document/d/1zPGFsNi-GPrjQQ9hLQ4oLz6wipABa9NiLzcpNVBdLQk/edit?usp=sharing
Feel free to update

Please also keep in mind that the ugrad oracle database has a lock that prevents insertion/update from multiple connections, so when running the client make sure you log off and reconnect whenever you made any changes.
I will probably also add in a reconnect button for this purpose.

There is a new set of todos in Listing/Resource/Update frame and their associated methods in EstateTransactionHandler. Whilethe inferface definition for those apis are provided, feel free to update the method arguments to better adapt your connectors. 

A side note for property search is that there could be more than one type of conditions specified, and whoever responsible for property might need to implement a query builder in their connector for this purpose.

Usages:
To login as admin, use ADMIN as username and cs304 as password.
To login as agent, use an existing agent name in your agent table as username and his agent id as password.
To login as guest, simply input anything.

Database uid is ora_<yourcwl>, password is a<your student#>

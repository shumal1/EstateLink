# EstateLink

This project requires access to the oracle database in UBC undergraduate linux server.

## Setup

* After cloning the project, you should be able to import it into Intellij
* Main class is ***src/Estatelink***
* For this project we use lib/ojdbc8.jar, make sure this is added to the dependency properly
* To setup your oracle database properly, please use the script ***data/datatable.sql*** to populate the tables

## Usage
* If project is setup correctly, when running you should see an authentication dialog. 
	* You can choose to sign in as one of: *ADMIN, AGENT, or GUEST*
		* To login as admin , use **ADMIN** as username and **cs304** as password. 
		* To login as agent, use an existing agent name the agency_employee table as username and the agent id as password.
		* To login as guest, simply input anything.
		* Database uid is ora_**<your_cwl>**, password is a **<your student#>**
* Once connected to the undergraduate server, a dialog should pop-up indicating whether the log-on was successful, and the main menu will be displayed with the following options:
	* ***Resources***: Allows the user to search for public resources, and associated properties
	* ***Listings***: Allows the user to navigate through listings, filter them by condition, or search a property that's listed.
	* ***Agents***: Allows the user to check the information of an agent or agency. ADMIN user can also register agent here.
	* ***Update*** (Only avaliable to ADMIN and AGENT): Allows insert, update, or delete operations to be performed on the actual database.
* The user can nativage between different panels using the *menu* button, to quit simply close the window.

## Remarks
* All of the sql queries could be found in the /connector directory, with different connectors representing access to different tables
* Handlers are responsible for connecting the user interface to the connectors
* Database connection is the singleton class *EstateDatabaseManager*. It contains the only connection to the remote database.
* Models provide ways to abstract different result sets and provide common interface for displaying.

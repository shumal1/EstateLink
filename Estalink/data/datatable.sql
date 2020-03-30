DROP TABLE agency_employee;
DROP TABLE agency;
DROP TABLE listing;
DROP TABLE community;
DROP TABLE property;
DROP TABLE public_resources;
DROP TABLE property_management;
DROP TABLE neighbour;
DROP TABLE property_in_community;
DROP TABLE has_property_and_resources;


CREATE TABLE agency_employee
	(agent_id INT,
	agent_name VARCHAR(255),
	agency_name VARCHAR(255),
	agent_phone_number VARCHAR(255),
	PRIMARY KEY (agent_id),
	FOREIGN KEY (agency_name) REFERENCES agency 
		ON DELETE CASCADE 
		ON UPDATE CASCADE
	);
grant select on agency_employee to public;
 

CREATE TABLE agency
	(agency_name VARCHAR(255),
	agency_address VARCHAR(255),
	agency_description VARCHAR(255),
	agency_rating DOUBLE,
	PRIMARY KEY (agency_name)
	);
grant select on agency_employee to public;
 

CREATE TABLE listing
	( listing_id INT,
	listing_price INT,
	historical_price INT,
	agent_id INT,
	listing_type VARCHAR(10),
	PRIMARY KEY (listing_id),
	FOREIGN KEY (agent_id) REFERENCES agency_employee
		ON DELETE CASCADE
		ON UPDATE CASCADE
	);
grant select on listing to public;

CREATE TABLE community
	(community_name VARCHAR(255),
	community_population INT,
	community_city VARCHAR(255),
	PRIMARY KEY (community_name, community_city)
	);
 
grant select on community to public;

CREATE TABLE property
	(property_address VARCHAR(255),
	listing_id INT NOT NULL,
	property_type VARCHAR(20),
	dimension VARCHAR(255),
	postal_code VARCHAR(20),
	is_duplex BOOL,
	apartment_number INT,
	capacity INT,
	PRIMARY KEY (property_address, listing_id),
	FOREIGN KEY (listing_id) REFERENCES listing
		ON DELETE SET NULL 
		ON UPDATE CASCADE
	);
grant select on property to public;

 
CREATE TABLE public_resources
	(resource_id INT,
	resource_name VARCHAR(255),
	resource_type INT,
	transit_type INT,
	park_description VARCHAR(255),
	hospital_type INT,
	PRIMARY KEY (resource_id));
grant select on public_resources to public;

CREATE TABLE property_management
	(agency_name VARCHAR(255),
	property_address VARCHAR(255),
	FOREIGN KEY (agency_name) REFERENCES agency,
	FOREIGN KEY (property_address) REFERENCES property
		ON DELETE CASCADE
		ON UPDATE CASCADE
	);
grant select on property_management to public;

CREATE TABLE neighbour
	(building_address VARCHAR(255),
	neighbour_address VARCHAR(255),
	PRIMARY KEY (building_address, neighbour_address),
	FOREIGN KEY (building_address, neighbour_address) REFERENCES property
		ON DELETE CASCADE
		ON UPDATE RESTRICT
	);
grant select on neighbour to public;

CREATE TABLE property_in_community
	(property_address VARCHAR(255),
	community_name VARCHAR(255),
	community_city VARCHAR(255),
	PRIMARY KEY(property_address, community_name, community_city),
	FOREIGN KEY(property_address) REFERENCES property
		ON DELETE CASCADE
		ON UPDATE RESTRICT,
	FOREIGN KEY(community_name, community_city) REFERENCES community
	);
grant select on property_in_community to public;

CREATE TABLE has_property_and_resources
	(resource_id INT,
	property_address VARCHAR(255),
	PRIMARY KEY (resource_id, property_id),
	FOREIGN KEY (resource_id) REFERENCES public_resources,
	FOREIGN KEY (property_address) REFERENCES property
		ON DELETE CASCADE
		ON UPDATE CASCADE
	);
grant select on has_property_and_resources to public;


 

INSERT INTO agency_employee
VALUES(7, 'James Bond', 'Realtor', 0070070007);
 
INSERT INTO agency_employee
VALUES(1, 'Thomas Lin', 'NuStream', 6041234567);

INSERT INTO agency_employee
VALUES(2, 'Catherine Due', 'Realex', 7781234567);

INSERT INTO agency_employee
VALUES(3, 'Judy Xu', 'BuildAWall', 6049251835);

INSERT INTO agency_employee
VALUES(4, 'Ben liu', 'GreatAgain', 6042258275);



INSERT INTO agency
VALUES('Realtor', '6335 Thunderbird Crescent, Vancouver', 'Find your next residential or commercial property with Canada largest real estate website', 4.3);

INSERT INTO agency
VALUES('NuStream', '2211 Wesbrook Mall, Vancouver', 'Our commercial real estate division. consistently outperforms. in asset management, brokerage,. and investment services. for our institutional and private clients.', 3.6);

INSERT INTO agency
VALUES('Realex', '6200 University Blvd, Vancouver', 'Find real estate agents in Vancouver, BC. provides real estate agents information including their contact info and listings for sale.', 4.1);

INSERT INTO agency
VALUES('BuildAWall', '3308 W Broadway, Vancouver', 'Looking to buy or sell a home? We have got you covered! Check out our property listings and find an experienced RE/MAX agent in your market.', 2.1);

INSERT INTO agency
VALUES('GreatAgain', '2095 W 41st Ave, Vancouver', 'Operating since 1978, The GreatAgain Real Estate Company Ltd is the number one real estate company in Vancouver.' , 4.8);



INSERT INTO listing
VALUES(1, 400000, 3500000, 1, 'SELL');

INSERT INTO listing
VALUES(2, 1200, null, 1, 'RENT');

INSERT INTO listing
VALUES(3, 1000, null, 3, 'RENT');

INSERT INTO listing
VALUES(4, 2500, 2300, 7, 'RENT');

INSERT INTO listing
VALUES(5, 1200000, 900000, 2, 'SELL');

INSERT INTO listing
VALUES(6, 800000, 700000, 4, 'SELL');

INSERT INTO community
VALUES('Kerrisdale', 20000, 'Vancouver');

INSERT INTO community
VALUES('Point Grey', 15000, 'Vancouver');

INSERT INTO community
VALUES('Wellingdon', 13250, 'Burnaby');

INSERT INTO community
VALUES('Lougheed', 50000, 'Burnaby');

INSERT INTO community
VALUES('Downtown', 45000, 'Vancouver');

INSERT INTO property
VALUES('5025 Yew Street, Vancouver', 1, 'Apartment', '1477 ft', 'V6S 1W7',false,205, null);

INSERT INTO property
VALUES('6218 King Edward, Vancouver', 2, 'Office', '10000 ft', 'V3M 3M3',false,null, 8);

INSERT INTO property
VALUES('5020 Yew Street, Vancouver', 3, 'Apartment', '980 ft', 'V6M 1S1',false,101, null);

INSERT INTO property
VALUES('2118 Great Lake Way, Vancouver', 4, 'Office', '9700 ft', 'V3S 1Q7',false,null, 12);

INSERT INTO property
VALUES('4040 Howe Street, Vancouver', 5, 'House', '3652 ft', 'V2S 1S3',false,null, null);

INSERT INTO property
VALUES('1250 Maple Street, Vancouver', 6, 'Apartment', '2500 ft', 'V6M 1L8',true,333, null);



INSERT INTO neighbour
VALUES ('5025 Yew Street, Vancouver', '5020 Yew Street, Vancouver');



INSERT INTO public_resources
VALUES (1, 'R4');

INSERT INTO public_resources
VALUES (2, 'Deer Lake Park');

INSERT INTO public_resources
VALUES (3, 'Canada Line');

INSERT INTO public_resources
VALUES (4, 'Vancouver General Hospital');

INSERT INTO public_resources
VALUES (5, 'Arbutus Greenway', );

INSERT INTO property_in_community
VALUES ('6218 King Edward, Vancouver', 'Wellingdon', 'Burnaby');

INSERT INTO property_in_community
VALUES ('1250 Maple Street, Vancouver', 'Kerrisdale', 'Vancouver');

INSERT INTO property_in_community
VALUES ('2118 Great Lake Way, Vancouver', 'Lougheed', 'Burnaby');

INSERT INTO property_in_community
VALUES ('4040 Howe Street, Vancouver', 'Downtown', 'Vancouver');

INSERT INTO property_in_community
VALUES ('5020 Yew Street, Vancouver', 'Kerrisdale', 'Vancouver');

INSERT INTO property_in_community
VALUES ('5025 Yew Street, Vancouver', 'Kerrisdale', 'Vancouver');


INSERT INTO has_property_and_resources
VALUES (1, '5025 Yew Street, Vancouver');

INSERT INTO has_property_and_resources
VALUES (1, '6218 King Edward, Vancouver');

INSERT INTO has_property_and_resources
VALUES (1, '1250 Maple Street, Vancouver');

INSERT INTO has_property_and_resources
VALUES (3, '5025 Yew Street, Vancouver');

INSERT INTO has_property_and_resources
VALUES (2, '2118 Great Lake Way, Vancouver');

INSERT INTO has_property_and_resources
VALUES (3, '4040 Howe Street, Vancouver');

INSERT INTO has_property_and_resources
VALUES (4, '1250 Maple Street, Vancouver');

INSERT INTO has_property_and_resources
VALUES (5, '5025 Yew Street, Vancouver');

INSERT INTO has_property_and_resources
VALUES (5, '5020 Yew Street, Vancouver');

INSERT INTO has_property_and_resources
VALUES (5, '1250 Maple Street, Vacouver');
drop table agency_employee;
drop table agency;
drop table listing;
/*drop table community;*/
drop table property;
drop table neighbour;
drop table public_resources;
drop table bus;
drop table park;
drop table skytrain;
drop table hospital;
drop table greenwalk;
drop table has_property_and_resources;

create table agency_employee
	(agency_address varchar(255),
	agent_id int,
	agent_name varchar(255),
	phone_number int,
	primary key (agency_address, agent_id),
	foreign key (agency_address) references agency 
		on delete CASCADE 
		/*on update CASCADE*/
	);
grant select on agency_employee to public;
 

create table agency
	(agency_name varchar(255),
	agency_address varchar(255),
	primary key (agency_address)
	);
grant select on agency_employee to public;
 

create table listing
	( listing_id int,
	rent int,
	price int,
	historical_price int,
	agent_id int not null,
	is_rental bit,
	primary key (listing_id)
	foreign key (agent_id) references agency_employee
	);
grant select on listing to public;

/*
create table community
	(community_name varchar(255)
	community_population int,
	city varchar(255),
	primary key (community_name, city)
	);
 
grant select on community to public;
 */

create table property
	(property_address varchar(255),
	listing_id int not null,
	property_type varchar(20),
	dimension varchar(255),
	postal_code varchar(20),
	is_duplex bit,
	apartment_number int
	capacity int,
	primary key (property_address, listing_id),
	foreign key (listing_id) references listing
		on delete CASCADE 
		/*on update CASCADE*/
	);
grant select on property to public;
 

create table neighbour
	(building_address varchar(255),
	neighbour_address varchar(255),
	primary key (building_address, neighbour_address)
	foreign key (building_address, neighbour_address) references property
		on delete Set null
		/*on update CASCADE*/
	);
grant select on neighbour to public;
 
create table public_resources
	(resource_id int,
	primary key (resource_id));
grant select on public_resources to public;
 
create table bus
	(resource_id int,
	transit_number varchar(20),
	primary key (resource_id)
	foreign key (resource_id) references public_resources
		on delete CASCADE
		/*on update CASCADE*/
	);
grant select on bus to public;

create table park
	(resource_id int,
	park_name varchar(255),
	primary key (resource_id),
	foreign key (resource_id) references public_resources
		on delete CASCADE
		/*on update CASCADE*/
	);
grant select on park to public;

create table skytrain
	(resource_id int,
	skytrain_line varchar(30),
	primary key (resource_id),
	foreign key (resource_id) references public_resources
		on delete CASCADE
		/*on update CASCADE*/
	);
grant select on skytrain to public;

create table hospital
	(resource_id int,
	hospital_name varchar(255),
	primary key (resource_id),
	foreign key (resource_id) references public_resources
		on delete CASCADE
		/*on update CASCADE*/
	);
grant select on hospital to public;

create table greenwalk
	(resource_id int,
	greenwalk_name varchar(255),
	foreign key (resource_id) references public_resources
		on delete CASCADE
		/*on update CASCADE*/
	);
grant select on greenwalk to public;

create table has_property_and_resources
	(resource_id int,
	property_address varchar(255),
	primary key (resource_id, property_id)
	foreign key (resource_id) references public_resources
		on delete CASCADE
		/*on update CASCADE*/
	foreign key (property_address) references property
		on delete CASCADE
		/*on update CASCADE*/
	);
grant select on has_property_and_resources to public;


 

insert into agency_employee
values('6335 Thunderbird Crescent, Vancouver', 7, 'James Bond', 0070070007);
 
insert into agency_employee
values('2211 Wesbrook Mall, Vancouver', 1, 'Thomas Lin', 6041234567);

insert into agency_employee
values('6200 University Blvd, Vancouver', 2, 'Catherine Due', 7781234567);

insert into agency_employee
values('3308 W Broadway, Vancouver', 3, 'Judy Xu', 6049251835);

insert into agency_employee
values('2095 W 41st Ave, Vancouver', 4, 'Ben liu', 6042258275);



insert into agency
values('Realtor', '6335 Thunderbird Crescent, Vancouver');

insert into agency
values('NuStream', '2211 Wesbrook Mall, Vancouver');

insert into agency
values('Realex', '6200 University Blvd, Vancouver');

insert into agency
values('BuildAWall', '3308 W Broadway, Vancouver');

insert into agency
values('GreatAgain', '2095 W 41st Ave, Vancouver');



insert into listing
values(1, null, 400000, 3500000, 1, 0);

insert into listing
values(2, 1200, null, null, 1, 1);

insert into listing
values(3, 1000, null, null, 3, 1);

insert into listing
values(4, 2500, null, 2300, 7, 1);

insert into listing
values(5, null, 1200000, 900000, 2, 0);

insert into listing
values(6, null, 800000, 700000, 4, 0);



insert into property
values('5025 Yew Street, Vancouver', 1, 'Apartment', '1477 ft', 'V6S 1W7',0,205, null);

insert into property
values('6218 King Edward, Vancouver', 2, 'Office Unit', '10000 ft', 'V3M 3M3',0,null, 8);

insert into property
values('5020 Yew Street, Vancouver', 3, 'Apartment', '980 ft', 'V6M 1S1',0,101, null);

insert into property
values('2118 Great Lake Way, Vancouver', 4, 'Office Unit', '9700 ft', 'V3S 1Q7',0,null, 12);

insert into property
values('4040 Howe Street, Vancouver', 5, 'House', '3652 ft', 'V2S 1S3',0,null, null);

insert into property
values('1250 Maple Street, Vancouver', 6, 'Apartment', '2500 ft', 'V6M 1L8',1,333, null);



insert into neighbour
values ('5025 Yew Street, Vancouver', '5020 Yew Street, Vancouver');



insert into public_resources
values (1);

insert into public_resources
values (2);

insert into public_resources
values (3);

insert into public_resources
values (4);

insert into public_resources
values (5);



insert into bus
values (1, 'R4');

insert into park
values (2, 'Deer Lake Park');

insert into skytrain
values (3, 'Canada Line');

insert into hospital
values (4, 'Vancouver General Hospital');

insert into greenwalk
values (5, 'Arbutus Greenway');



insert into has_property_and_resources
values (1, '5025 Yew Street, Vancouver');

insert into has_property_and_resources
values (1, '6218 King Edward, Vancouver');

insert into has_property_and_resources
values (1, '1250 Maple Street, Vancouver');

insert into has_property_and_resources
values (3, '5025 Yew Street, Vancouver');

insert into has_property_and_resources
values (2, '2118 Great Lake Way, Vancouver');

insert into has_property_and_resources
values (3, '4040 Howe Street, Vancouver');

insert into has_property_and_resources
values (4, '1250 Maple Street, Vancouver');

insert into has_property_and_resources
values (5, '5025 Yew Street, Vancouver');

insert into has_property_and_resources
values (5, '5020 Yew Street, Vancouver');

insert into has_property_and_resources
values (5, '1250 Maple Street, Vacouver');
create table if not exists user (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(20),
  last_name VARCHAR(20),
  email VARCHAR(20),
  date_of_birth VARCHAR(20)
);
select * from user;
delete from user;
select count(*) from user;

create table if not exists auditorium (
  auditorium_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(20),
  numberOfSeats long,
);
select * from auditorium;
delete from auditorium;

create table if not exists rating (
  rating_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(20)
);
drop table rating;
INSERT INTO rating (name) VALUES ('LOW');
INSERT INTO rating (name) VALUES ('MID');
INSERT INTO rating (name) VALUES ('HIGH');

create table if not exists event (
  event_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(20),
  basePrice double,
  rating VARCHAR(20),
);
select * from event;
delete from event;
drop table event;

create table if not exists ticket (
  ticket_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id int,
  event_id int,
  dateTime datetime,
  seat long,
  FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE SET NULL,
  FOREIGN KEY (event_id) REFERENCES event (event_id) ON DELETE SET NULL
);
select * from ticket ;
delete from ticket;
drop table ticket;

create table if not exists booked_tickets (
  boocked_tickets_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id long,
  ticket_id long,
  event_id int,
  FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE SET NULL,
  FOREIGN KEY (ticket_id) REFERENCES ticket (ticket_id) ON DELETE SET NULL,
  FOREIGN KEY (event_id) REFERENCES event (event_id) ON DELETE SET NULL
);
drop table booked_tickets;
delete from booked_tickets;


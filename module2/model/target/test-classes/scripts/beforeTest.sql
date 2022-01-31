CREATE TABLE certificate (
    id IDENTITY NOT NULL PRIMARY KEY,
    name varchar(45) ,
    description varchar(45) ,
    price double ,
    duration int ,
    create_date varchar(45) ,
    last_update_date varchar(45)
  );

CREATE TABLE tag (
  id IDENTITY NOT NULL PRIMARY KEY,
  name varchar(45) NOT NULL
  );

CREATE TABLE certificate_tag (
  id IDENTITY NOT NULL PRIMARY KEY,
  certificate_id int NOT NULL,
  tag_id int NOT NULL,
  foreign key (certificate_id) references certificate(id),
  foreign key (tag_id) references tag(id)
  );

INSERT INTO certificate VALUES (1,'name','descr',2,3,'data','data');
INSERT INTO certificate VALUES (2,'name2','descr2',4,5,'data','data');
INSERT INTO certificate VALUES (3,'name3','descr3',6,7,'data','data');
INSERT INTO certificate VALUES (4,'name4','descr4',8,9,'data','data');

INSERT INTO tag VALUES (1,'tag1');
INSERT INTO tag VALUES (2,'tag2');
INSERT INTO tag VALUES (3,'tag3');
INSERT INTO tag VALUES (4,'tag4');



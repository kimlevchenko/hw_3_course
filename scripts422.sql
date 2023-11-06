create table persons
(
    id         serial primary key,
    name       varchar,
    age        int,
    carLicense boolean
);

create table cars
(
    id        serial primary key,
    brand     varchar,
    model     varchar,
    price     int,
    person_id int references persons (id)
);


select s.name, s.age, f.name
from student s
         join faculty f on f.id = s.faculty_id;

select s.name, s.id
from avatar a
         join student s on s.id = a.student_id;
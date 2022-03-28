
drop table if exists students;
create table students (
    id bigint primary key auto_increment,
    first_name varchar(255),
    second_name varchar(255),
    age int,
    email varchar(255),
    field varchar(255)
);

drop table if exists teachers;
create table teachers (
    id bigint primary key auto_increment,
    first_name varchar(255),
    second_name varchar(255),
    age int,
    email varchar(255),
    subject varchar(255)
);

create table teacher_student_relationship (
    teacher_id bigint not null,
    student_id bigint not null,
    primary key (teacher_id, student_id)
);

alter table teacher_student_relationship
    add constraint fk_teacher
        foreign key (teacher_id) references teachers(id);

alter table teacher_student_relationship
    add constraint fk_student
        foreign key (student_id) references students(id);



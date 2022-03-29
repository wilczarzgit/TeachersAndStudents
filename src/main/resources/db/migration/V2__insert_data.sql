insert into students(id, first_name, second_name, age, email, field) values(1, "Tomasz", "Przygoda", 20, "przygoda@wp.pl", "informatyka");
insert into students(id, first_name, second_name, age, email, field) values(2, "Mariusz", "Kowal", 21, "mariuszkow@onet.pl", "ekonomia");
insert into students(id, first_name, second_name, age, email, field) values(3, "Agata", "Damięcka", 21, "adamiecka@onet.pl", "robotyka");
insert into students(id, first_name, second_name, age, email, field) values(4, "Miłosz", "Wilk", 22, "wilk.milosz@gmail.com", "ekonomia");
insert into students(id, first_name, second_name, age, email, field) values(5, "Monika", "Stec", 24, "stec@onet.pl", "robotyka");
insert into students(id, first_name, second_name, age, email, field) values(6, "Zygmunt", "Zarzycki", 25, "zz1234@gmail.com", "informatyka");
insert into students(id, first_name, second_name, age, email, field) values(7, "Joanna", "Opos", 28, "joanna.opos@onet.pl", "ekonomia");
insert into students(id, first_name, second_name, age, email, field) values(8, "Anna", "Piątek", 30, "apiatek@gmail.com", "informatyka");
insert into students(id, first_name, second_name, age, email, field) values(9, "Tobiasz", "Kozień", 29, "tk1983@onet.pl", "robotyka");
insert into students(id, first_name, second_name, age, email, field) values(10, "Józef", "Marszałek", 21, "jozef1234@gmail.com", "ekonomia");
insert into students(id, first_name, second_name, age, email, field) values(11, "Aleksandra", "Pyla", 22, "alepyla@onet.pl", "robotyka");
insert into students(id, first_name, second_name, age, email, field) values(12, "Marcin", "Ligęza", 23, "marlig@wp.pl", "informatyka");
insert into students(id, first_name, second_name, age, email, field) values(13, "Piotr", "Boroń", 25, "p.boron@onet.pl", "ekonomia");
insert into students(id, first_name, second_name, age, email, field) values(14, "Agnieszka", "Lichecka", 23, "a.lichecka@gmail.com", "informatyka");

insert into teachers(id, first_name, second_name, age, email, subject) values(1, "Tomasz", "Kozioł", 46, "tomasz.koziol@gmail.com", "Algebra");
insert into teachers(id, first_name, second_name, age, email, subject) values(2, "Mikołaj", "Białecki", 42, "mbialecki@yahoo.com", "Metody obliczeniowe");
insert into teachers(id, first_name, second_name, age, email, subject) values(3, "Aleksandra", "Peszel", 46, "peszel.ola@gmail.com", "Teoria kompilacji");
insert into teachers(id, first_name, second_name, age, email, subject) values(4, "Krzysztof", "Kuc", 46, "krzysztof.kuc@hotmail.com", "Elektronika");
insert into teachers(id, first_name, second_name, age, email, subject) values(5, "Maciej", "Marecki", 46, "mmar77@wp.pl", "Języki obiektowe");
insert into teachers(id, first_name, second_name, age, email, subject) values(6, "Filip", "Chodak", 46, "filip625@gmail.com", "Logika");
insert into teachers(id, first_name, second_name, age, email, subject) values(7, "Anna", "Wielowiejska", 46, "wielowiej@onet.pl", "Grafika 3D");

insert into teachers_students(teacher_id, student_id) values(1, 1);
insert into teachers_students(teacher_id, student_id) values(1, 3);
insert into teachers_students(teacher_id, student_id) values(1, 4);
insert into teachers_students(teacher_id, student_id) values(1, 7);
insert into teachers_students(teacher_id, student_id) values(1, 11);
insert into teachers_students(teacher_id, student_id) values(1, 12);
insert into teachers_students(teacher_id, student_id) values(1, 14);

insert into teachers_students(teacher_id, student_id) values(2, 1);
insert into teachers_students(teacher_id, student_id) values(2, 2);
insert into teachers_students(teacher_id, student_id) values(2, 3);
insert into teachers_students(teacher_id, student_id) values(2, 5);
insert into teachers_students(teacher_id, student_id) values(2, 10);
insert into teachers_students(teacher_id, student_id) values(2, 11);
insert into teachers_students(teacher_id, student_id) values(2, 12);

insert into teachers_students(teacher_id, student_id) values(3, 3);
insert into teachers_students(teacher_id, student_id) values(3, 4);
insert into teachers_students(teacher_id, student_id) values(3, 6);
insert into teachers_students(teacher_id, student_id) values(3, 10);
insert into teachers_students(teacher_id, student_id) values(3, 11);
insert into teachers_students(teacher_id, student_id) values(3, 13);
insert into teachers_students(teacher_id, student_id) values(3, 14);

insert into teachers_students(teacher_id, student_id) values(4, 1);
insert into teachers_students(teacher_id, student_id) values(4, 2);
insert into teachers_students(teacher_id, student_id) values(4, 3);
insert into teachers_students(teacher_id, student_id) values(4, 4);

insert into teachers_students(teacher_id, student_id) values(5, 1);
insert into teachers_students(teacher_id, student_id) values(5, 2);
insert into teachers_students(teacher_id, student_id) values(5, 3);
insert into teachers_students(teacher_id, student_id) values(5, 4);
insert into teachers_students(teacher_id, student_id) values(5, 5);
insert into teachers_students(teacher_id, student_id) values(5, 6);
insert into teachers_students(teacher_id, student_id) values(5, 7);
insert into teachers_students(teacher_id, student_id) values(5, 8);
insert into teachers_students(teacher_id, student_id) values(5, 9);
insert into teachers_students(teacher_id, student_id) values(5, 10);
insert into teachers_students(teacher_id, student_id) values(5, 11);
insert into teachers_students(teacher_id, student_id) values(5, 12);
insert into teachers_students(teacher_id, student_id) values(5, 13);
insert into teachers_students(teacher_id, student_id) values(5, 14);

insert into teachers_students(teacher_id, student_id) values(6, 3);
insert into teachers_students(teacher_id, student_id) values(6, 6);
insert into teachers_students(teacher_id, student_id) values(6, 7);
insert into teachers_students(teacher_id, student_id) values(6, 8);
insert into teachers_students(teacher_id, student_id) values(6, 9);
insert into teachers_students(teacher_id, student_id) values(6, 11);
insert into teachers_students(teacher_id, student_id) values(6, 12);
insert into teachers_students(teacher_id, student_id) values(6, 14);

insert into teachers_students(teacher_id, student_id) values(7, 1);
insert into teachers_students(teacher_id, student_id) values(7, 2);
insert into teachers_students(teacher_id, student_id) values(7, 4);
insert into teachers_students(teacher_id, student_id) values(7, 8);
insert into teachers_students(teacher_id, student_id) values(7, 9);
insert into teachers_students(teacher_id, student_id) values(7, 11);
insert into teachers_students(teacher_id, student_id) values(7, 13);
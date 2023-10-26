package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {

    Student add(Student student);

    Student get(long id);

    Student update(Student student);

    void remove(long id);

    Collection<Student> getAllStudents();

    Collection<Student> findByAge(int age);

}

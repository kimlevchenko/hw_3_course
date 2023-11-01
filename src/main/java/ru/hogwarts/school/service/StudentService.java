package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {

    Student add(Student student);

    Student get(Long id);

    Student update(Student student);

    void remove(Long id);

    Collection<Student> getAllStudents();

    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Faculty getFacultyOfStudent(Long id);

}

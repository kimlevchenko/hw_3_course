package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    Student add(Student student);

    Student get(Long id);

    Student update(Student student);

    Student remove(Long id);

    Collection<Student> getAllStudents();

    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Faculty getFacultyOfStudent(Long id);

    long studentsCount();

    double averageAge();

    Collection<Student> lastFiveStudents();

    Collection<Student> findByName(String name);

    double getMiddleAgeOfStudents();

    List<String> getByAlphabeticOrder();

    void printStudentsThroughThreads();

    void printStudentsThroughSyncThreads();
}

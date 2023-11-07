package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student add(Student student) {
        return repository.save(student);
    }

    @Override
    public Student get(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Student remove(Long id) {
        var entity = repository.findById(id).orElse(null);
        if (entity != null) {
            repository.delete(entity);
        }
        return entity;
    }

    @Override
    public Student update(Student student) {
        return repository.findById(student.getId())
                .map(entity -> repository.save(student))
                .orElse(null);
    }

    @Override
    public Collection<Student> findByAge(int age) {
        return repository.findByAge(age);
    }

    @Override
    public Collection<Student> getAllStudents() {
        return repository.findAll();
    }

    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        return repository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getFacultyOfStudent(Long id) {
        return repository.findById(id).map(Student::getFaculty).orElse(null);
    }

    public long studentsCount() {
        return repository.getStudentCount();
    }

    public double averageAge() {
        return repository.getAverageAge();
    }

    public Collection<Student> lastFiveStudents() {
        return repository.getLastFiveStudents();
    }

    public Collection<Student> findByName(String name) {
        return repository.findByName(name);
    }
}

package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    public Student add(Student student) {
        return repository.save(student);
    }

    public Student get(long id) {
        return repository.findById(id).orElse(null);
    }

    public void remove(long id) {
        repository.deleteById(id);
    }

    public Student update(Student student) {
        return repository.save(student);
    }

    public Collection<Student> findByAge(int age) {
        return repository.findByAge(age);
    }

    public Collection<Student> getAllStudents() {
        return repository.findAll();
    }
}

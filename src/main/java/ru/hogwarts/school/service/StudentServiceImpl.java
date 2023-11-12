package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;


@Service
public class StudentServiceImpl implements StudentService {

    private final static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student add(Student student) {
        logger.info("Add method was invoked");
        return repository.save(student);
    }

    @Override
    public Student get(Long id) {
        logger.info("Get method was invoked with argument {}", id);
        return repository.findById(id).orElse(null);
    }

    @Override
    public Student remove(Long id) {
        logger.info("Remove method was invoked with argument {}", id);
        var entity = repository.findById(id).orElse(null);
        if (entity != null) {
            repository.delete(entity);
        }
        return entity;
    }

    @Override
    public Student update(Student student) {
        logger.info("Update method was invoked with new argument {}", student);
        return repository.findById(student.getId())
                .map(entity -> repository.save(student))
                .orElse(null);
    }

    @Override
    public Collection<Student> findByAge(int age) {
        logger.info("FindByAge method was invoked with argument {}", age);
        return repository.findByAge(age);
    }

    @Override
    public Collection<Student> getAllStudents() {
        logger.info("GetAllStudents method was invoked");
        return repository.findAll();
    }

    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("FindByAgeBetween method was invoked with arguments minAge {} maxAge {}", min, max);
        return repository.findByAgeBetween(min, max);
    }

    @Override
    public Faculty getFacultyOfStudent(Long id) {
        logger.info("GetFacultyOfStudent method was invoked with argument {}", id);
        return repository.findById(id).map(Student::getFaculty).orElse(null);
    }

    @Override
    public long studentsCount() {
        logger.error("StudentsCount method was invoked");
        return repository.getStudentCount();
    }

    @Override
    public double averageAge() {
        logger.info("AverageAge method was invoked");
        return repository.getAverageAge();
    }

    @Override
    public Collection<Student> lastFiveStudents() {
        logger.info("LastFiveStudents method was invoked");
        return repository.getLastFiveStudents();
    }

    @Override
    public Collection<Student> findByName(String name) {
        logger.info("FindByName method was invoked with argument {}", name);
        return repository.findByName(name);
    }
}

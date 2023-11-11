package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final static Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        logger.info("Add method was invoked");
        return repository.save(faculty);
    }

    @Override
    public Faculty get(Long id) {
        logger.info("Get method was invoked with argument {}", id);
        return repository.findById(id).orElse(null);
    }

    @Override
    public Collection<Faculty> findByNameOrColor(String name, String color) {
        logger.info("FindByNameOrColor method was invoked");
        return repository.findByNameOrColorIgnoreCase(name, color);
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("Update method was invoked with new argument {}", faculty);
        return repository.findById(faculty.getId())
                .map(entity -> repository.save(faculty))
                .orElse(null);
    }

    @Override
    public Faculty remove(Long id) {
        logger.info("Remove method was invoked with argument {}", id);
        var entity = repository.findById(id).orElse(null);
        if (entity != null) {
            repository.delete(entity);
        }
        return entity;
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        logger.info("GetAllFaculties method was invoked");
        return repository.findAll();
    }

    @Override
    public Collection<Student> getStudentsOfFaculty(Long id) {
        logger.info("GetStudentsOfFaculty method was invoked with argument {}", id);
        return repository.findById(id).map(Faculty::getStudents).orElse(Collections.emptyList());
    }

    @Override
    public Collection<Faculty> findByNameAndColor(String name, String color) {
        logger.info("FindByNameAndColor method was invoked with arguments name {} color {}", name, color);
        return repository.findByNameAndColor(name, color);
    }
}

package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        return repository.save(faculty);
    }

    @Override
    public Faculty get(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Collection<Faculty> findByNameOrColor(String name, String color) {
        return repository.findByNameOrColorIgnoreCase(name, color);
    }

    @Override
    public Faculty update(Faculty faculty) {
        return repository.findById(faculty.getId())
                .map(entity -> repository.save(faculty))
                .orElse(null);
    }

    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        return repository.findAll();
    }

    @Override
    public Collection<Student> getStudentsOfFaculty(Long id) {
        return repository.findById(id).map(Faculty::getStudents).orElse(Collections.emptyList());
    }
}

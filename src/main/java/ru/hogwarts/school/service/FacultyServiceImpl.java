package ru.hogwarts.school.service;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty add(Faculty faculty) {
        return repository.save(faculty);
    }

    public Faculty get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Collection<Faculty> findByNameOrColor(String name, String color) {
        return repository.findByNameOrColorIgnoreCase(name, color);
    }

    public Faculty update(Faculty faculty) {
        return repository.findById(faculty.getId())
                .map(entity -> repository.save(faculty))
                .orElse(null);
    }

    public void remove(Long id) {
        repository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {
        return repository.findAll();
    }

}

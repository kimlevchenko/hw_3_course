package ru.hogwarts.school.service;
import ru.hogwarts.school.model.Faculty;
import java.util.Collection;
public interface FacultyService {

    Faculty add(Faculty faculty);

    Faculty get(Long id);

    Faculty update(Faculty faculty);

    void remove(Long id);

    Collection<Faculty> getAllFaculties();

    Collection<Faculty> findByNameOrColor(String name, String color);

}

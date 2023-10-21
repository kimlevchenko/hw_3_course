package ru.hogwards.school.service;

import ru.hogwards.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {

    Faculty addFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    Faculty editFaculty(long id, Faculty faculty);

    void deleteFaculty(long id);

    Collection<Faculty> getAllFaculties();

    Collection<Faculty> findByColor(String color);
}

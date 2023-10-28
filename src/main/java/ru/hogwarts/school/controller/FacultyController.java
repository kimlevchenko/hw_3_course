package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return service.add(faculty);
    }

    @GetMapping("/{id}")
    public Faculty get(@PathVariable Long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public Faculty update(@RequestBody Faculty faculty, @PathVariable Long id) {
        return service.update(faculty);
    }

    @GetMapping
    public Collection<Faculty> getAllFaculties() {
        return service.getAllFaculties();
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        service.remove(id);
    }

    @GetMapping("/byNameOrColor")
    public Collection<Faculty> findByNameOrColor(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String color) {
        return service.findByNameOrColor(name, color);
    }

    @GetMapping("/{facultyId}/students")
    public Collection<Student> findByFaculty(@PathVariable Long facultyId) {
        return service.get(facultyId).getStudents();
    }
}

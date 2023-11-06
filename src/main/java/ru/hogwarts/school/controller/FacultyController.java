package ru.hogwarts.school.controller;

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

    @GetMapping("/{id}/students")
    public Collection<Student> getStudentsOfFaculty(@PathVariable Long id) {
        return service.getStudentsOfFaculty(id);
    }

    @GetMapping("/byNameAndColor")
    public Collection<Faculty> findByNameAndColor(@RequestParam String name,
                                                  @RequestParam String color) {
        return service.findByNameAndColor(name, color);
    }
}

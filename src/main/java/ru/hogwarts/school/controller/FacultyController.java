package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
    public Faculty get(@PathVariable long id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public Faculty update(@RequestBody Faculty faculty, @PathVariable long id) {
        return service.update(faculty);
    }

    @GetMapping
    public Collection<Faculty> getAllFaculties() {
        return service.getAllFaculties();
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable long id) {
        service.remove(id);
    }

    @GetMapping("/byColor")
    public Collection<Faculty> findFByColor(@RequestParam String color) {
        return service.findByColor(color);
    }
}

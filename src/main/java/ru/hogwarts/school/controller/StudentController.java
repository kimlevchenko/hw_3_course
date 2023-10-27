package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentServiceImpl service;

    public StudentController(StudentServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public Student add(@RequestBody Student student) {
        return service.add(student);
    }

    @GetMapping("/{id}")
    public Student get(@PathVariable long id) {
        return service.get(id);
    }

    @GetMapping
    public Collection<Student> getAllStudents() {
        return service.getAllStudents();
    }

    @PutMapping("/{id}")
    public Student update(@RequestBody Student student, @PathVariable long id) {
        return service.update(student);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        service.remove(id);
    }

    @GetMapping("/byAge")
    public Collection<Student> findByAge(@RequestParam int age) {
        return service.findByAge(age);
    }
}

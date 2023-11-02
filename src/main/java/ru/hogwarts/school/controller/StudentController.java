package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
    public Student get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public Collection<Student> getAllStudents() {
        return service.getAllStudents();
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return service.update(student);
    }

    @DeleteMapping("/{id}")
    public Student remove(@PathVariable Long id) {
        return service.remove(id);
    }

    @GetMapping("/byAge")
    public Collection<Student> findByAge(@RequestParam int age) {
        return service.findByAge(age);
    }

    @GetMapping("/byAgeBetween")
    public Collection<Student> byAgeBetween(@RequestParam int min, @RequestParam int max) {
        return service.findByAgeBetween(min, max);
    }

    @GetMapping("/{studentId}/faculty")
    public Faculty getFacultyOfStudent(@RequestParam Long id) {
        return service.getFacultyOfStudent(id);
    }

}

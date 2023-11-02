package ru.hogwarts.school.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    public void testAddStudent() {
        var request = student("Ron", 20);
        var result = restTemplate.postForObject("/student", request, Student.class);
        Assertions.assertThat(result.getAge()).isEqualTo(20);
        Assertions.assertThat(result.getName()).isEqualTo("Ron");
        Assertions.assertThat(result.getId()).isNotNull();
    }

    @Test
    public void testGetStudents() {
        var s = student("Harry", 18);
        var saved = restTemplate.postForObject("/student", s, Student.class);

        var result = restTemplate.getForObject("/student/" + saved.getId(), Student.class);
        Assertions.assertThat(result.getName()).isEqualTo("Harry");
        Assertions.assertThat(result.getAge()).isEqualTo(18);
    }

    @Test
    public void testRemoveStudent() {
        var s = student("DeletedHarry", 18);
        var saved = restTemplate.postForObject("/student", s, Student.class);

        ResponseEntity<Student> studentEntity = restTemplate.exchange(
                "/student/" + saved.getId(),
                HttpMethod.DELETE,
                null,
                Student.class);

        Assertions.assertThat(studentEntity.getBody().getName()).isEqualTo("DeletedHarry");
        Assertions.assertThat(studentEntity.getBody().getAge()).isEqualTo(18);

        var deletedHarry = restTemplate.getForObject("/student/" + saved.getId(), Student.class);
        Assertions.assertThat(deletedHarry).isNull();
    }

    @Test
    public void testUpdateStudent() {
        var s = student("Harry", 18);
        var saved = restTemplate.postForObject("/student", s, Student.class);
        saved.setName("Germiona");

        ResponseEntity<Student> studentEntity = restTemplate.exchange(
                "/student",
                HttpMethod.PUT,
                new HttpEntity<>(saved),
                Student.class);

        Assertions.assertThat(studentEntity.getBody().getName()).isEqualTo("Germiona");
        Assertions.assertThat(studentEntity.getBody().getAge()).isEqualTo(18);
    }

    @Test
    void testFindByAge() {
        var s1 = restTemplate.postForObject("/student", student("test1", 16), Student.class);
        var s2 = restTemplate.postForObject("/student", student("test2", 17), Student.class);
        var s3 = restTemplate.postForObject("/student", student("test3", 18), Student.class);
        var s4 = restTemplate.postForObject("/student", student("test4", 19), Student.class);
        var s5 = restTemplate.postForObject("/student", student("test5", 18), Student.class);

        var result = restTemplate.exchange("/student/byAge?age=18",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<Student>>() {
                });

        var students = result.getBody();

        Assertions.assertThat(students).isNotNull();
        Assertions.assertThat(students.size()).isEqualTo(2);
        Assertions.assertThat(students).containsExactly(s3, s5);
    }

    @Test
    void testGetAllStudents() {
        var s1 = restTemplate.postForObject("/student", student("test1", 16), Student.class);
        var s2 = restTemplate.postForObject("/student", student("test2", 17), Student.class);
        var s3 = restTemplate.postForObject("/student", student("test3", 18), Student.class);

        var result = restTemplate.exchange("/student",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<Student>>() {
                });

        var students = result.getBody();

        Assertions.assertThat(students).isNotNull();
        Assertions.assertThat(students.size()).isEqualTo(3);
        Assertions.assertThat(students).containsExactly(s1, s2, s3);
    }

    @Test
    void testFindByAgeBetween() {
        var s1 = restTemplate.postForObject("/student", student("test1", 16), Student.class);
        var s2 = restTemplate.postForObject("/student", student("test2", 17), Student.class);
        var s3 = restTemplate.postForObject("/student", student("test3", 18), Student.class);
        var s4 = restTemplate.postForObject("/student", student("test4", 19), Student.class);
        var s5 = restTemplate.postForObject("/student", student("test5", 18), Student.class);

        var result = restTemplate.exchange("/student/byAgeBetween?min=17&max=19",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<Student>>() {
                });

        var students = result.getBody();

        Assertions.assertThat(students).isNotNull();
        Assertions.assertThat(students.size()).isEqualTo(4);
        Assertions.assertThat(students).containsExactly(s2, s3, s4, s5);
    }

//    @Test
//    void testGetFacultyOfStudent() {
//        var savedFaculty = restTemplate.postForObject("/faculty", faculty("f1", "green"), Faculty.class);
//        var s = student("Ron", 19);
//        s.setFaculty(savedFaculty);
//        var savedStudent = restTemplate.postForObject("/student", s, Student.class);
//
//        var result = restTemplate.getForObject("/student/" + savedStudent.getId() + "/faculty", Faculty.class);
//        Assertions.assertThat(result).isNotNull();
//        Assertions.assertThat(result.getName()).isEqualTo("f1");
//        Assertions.assertThat(result.getColor()).isEqualTo("green");
//    }


    private static Faculty faculty(String name, String color) {
        var f = new Faculty();
        f.setName(name);
        f.setColor(color);
        return f;
    }


    private static Student student(String name, int age) {
        var s = new Student();
        s.setName(name);
        s.setAge(age);
        return s;
    }
}
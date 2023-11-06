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
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private FacultyRepository repository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    public void testAddFaculty() {
        var request = faculty("f1", "anyColor");
        var result = restTemplate.postForObject("/faculty", request, Faculty.class);
        Assertions.assertThat(result.getName()).isEqualTo("f1");
        Assertions.assertThat(result.getColor()).isEqualTo("anyColor");
        Assertions.assertThat(result.getId()).isNotNull();
    }

    @Test
    public void testGetFaculty() {
        var f = faculty("f1", "anyColor");
        var saved = restTemplate.postForObject("/faculty", f, Faculty.class);

        var result = restTemplate.getForObject("/faculty/" + saved.getId(), Faculty.class);
        Assertions.assertThat(result.getName()).isEqualTo("f1");
        Assertions.assertThat(result.getColor()).isEqualTo("anyColor");
    }

    @Test
    public void testFindByNameOrColor() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty" + "/byNameOrColor", String.class))
                .isNotNull();
    }

    @Test
    public void testUpdateFaculty() {
        var f = faculty("f1", "anyColor");
        var saved = restTemplate.postForObject("/faculty", f, Faculty.class);
        saved.setName("f2");

        ResponseEntity<Faculty> facultyEntity = restTemplate.exchange(
                "/faculty/" + saved.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(saved),
                Faculty.class);

        Assertions.assertThat(facultyEntity.getBody().getName()).isEqualTo("f2");
        Assertions.assertThat(facultyEntity.getBody().getColor()).isEqualTo("anyColor");
    }

    @Test
    public void testRemoveFaculty() {
        var f = faculty("DeletedF1", "anyColor");
        var saved = restTemplate.postForObject("/faculty", f, Faculty.class);

        ResponseEntity<Faculty> facultyEntity = restTemplate.exchange(
                "/faculty/" + saved.getId(),
                HttpMethod.DELETE,
                null,
                Faculty.class);

        Assertions.assertThat(facultyEntity.getBody().getName()).isEqualTo("DeletedF1");
        Assertions.assertThat(facultyEntity.getBody().getColor()).isEqualTo("anyColor");

        var deletedF1 = restTemplate.getForObject("/faculty/" + saved.getId(), Faculty.class);
        Assertions.assertThat(deletedF1).isNull();
    }

    @Test
    void testGetAllFaculties() {
        var f1 = restTemplate.postForObject("/faculty", faculty("test1", "red"), Faculty.class);
        var f2 = restTemplate.postForObject("/faculty", faculty("test2", "green"), Faculty.class);
        var f3 = restTemplate.postForObject("/faculty", faculty("test3", "blue"), Faculty.class);

        var result = restTemplate.exchange("/faculty",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<Faculty>>() {
                });

        var faculties = result.getBody();

        Assertions.assertThat(faculties).isNotNull();
        Assertions.assertThat(faculties.size()).isEqualTo(3);
        Assertions.assertThat(faculties).containsExactly(f1, f2, f3);
    }

    @Test
    public void testGetStudentsOfFaculty() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/1" + "/students", String.class))
                .isNotNull();
    }


    static Faculty faculty(String name, String color) {
        var f = new Faculty();
        f.setName(name);
        f.setColor(color);
        return f;
    }
}
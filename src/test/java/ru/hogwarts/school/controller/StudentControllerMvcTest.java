package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class StudentControllerMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentRepository studentRepository;
    @MockBean
    AvatarRepository avatarRepository;
    @MockBean
    FacultyRepository facultyRepository;

    @SpyBean(classes = StudentServiceImpl.class)
    StudentService studentService;
    @SpyBean(classes = AvatarServiceImpl.class)
    AvatarService avatarService;
    @SpyBean(classes = FacultyServiceImpl.class)
    FacultyService facultyService;

    @InjectMocks
    StudentController studentController;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testAddStudent() throws Exception {
        Long id = 1L;
        String name = "Harry";
        int age = 182;

        JSONObject studentObject = jsonStudent(name, age);
        Student student = student(id, name, age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        studentObject.put("id", id);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void testGetStudents() throws Exception {
        Long id = 1L;
        String name = "Harry";
        int age = 182;
        Student student = student(id, name, age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void testRemoveStudent() throws Exception {
        Long id = 2L;
        String name = "Ron";
        int age = 190;

        Student student = student(id, name, age);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Long id = 2L;
        String name = "Ron";
        int age = 190;
        Student student = student(id, name, age);
        when(studentRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(student));

        student.setName("Garry");

        JSONObject updateStudentRq = new JSONObject();
        updateStudentRq.put("id", student.getId());
        updateStudentRq.put("name", student.getName());
        updateStudentRq.put("age", student.getAge());

        when(studentRepository.save(ArgumentMatchers.any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(updateStudentRq.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(student.getName()))
                .andExpect(jsonPath("$.age").value(student.getAge()));
    }

    @Test
    public void testFindByAgeBetween() throws Exception {
        Long id = 2L;
        String name = "Ron";
        int age = 190;
        Student student = student(id, name, age);
        List<Student> students = Collections.singletonList(student);
        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/byAgeBetween?min=10&max=20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(students)));
    }

    @Test
    public void testGetFacultyOfStudent() throws Exception {
        Long id = 2L;
        String name = "Ron";
        int age = 190;
        Student student = student(id, name, age);
        List<Student> students = Collections.singletonList(student);
        when(studentRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id + "/faculty")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByAge() throws Exception {
        Long id = 2L;
        String name = "Ron";
        int age = 20;
        Student student = student(id, name, age);
        when(studentRepository.findByAge(age)).thenReturn(Collections.singletonList(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/byAge?age=" + age)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static Student student(Long id, String name, int age) {
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        return student;
    }

    private static JSONObject jsonStudent(String name, int age) {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("age", age);
        return obj;
    }

}
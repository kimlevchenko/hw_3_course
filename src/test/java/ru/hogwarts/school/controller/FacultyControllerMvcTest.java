package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.hogwarts.school.controller.ConstantForTest.*;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyRepository repository;
    @SpyBean
    private FacultyServiceImpl facultyServiceImpl;
    @InjectMocks
    private FacultyController facultyController;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testAddFaculty() throws Exception {
        when(repository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(MOCK_FACULTY);

        JSONObject createFacultyRq = new JSONObject();
        createFacultyRq.put("name", MOCK_FACULTY.getName());
        createFacultyRq.put("color", MOCK_FACULTY.getColor());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(createFacultyRq.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY.getName()))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY.getColor()));
    }

    @Test
    public void testGetFaculty() throws Exception {
        when(repository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + MOCK_FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));
    }

    @Test
    public void testRemoveFaculty() throws Exception {
        when(repository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + MOCK_FACULTY_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(MOCK_FACULTY));

        MOCK_FACULTY.setName(MOCK_FACULTY_NEW_NAME);

        JSONObject updateFacultyRq = new JSONObject();
        updateFacultyRq.put("id", MOCK_FACULTY.getId());
        updateFacultyRq.put("name", MOCK_FACULTY.getName());
        updateFacultyRq.put("color", MOCK_FACULTY.getColor());

        when(repository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(MOCK_FACULTY);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty/" + id)
                        .content(updateFacultyRq.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_FACULTY.getName()))
                .andExpect(jsonPath("$.color").value(MOCK_FACULTY.getColor()));
    }

    @Test
    public void testFindByNameOrColor() throws Exception {
        when(repository.findByNameOrColorIgnoreCase(anyString(), anyString())).thenReturn(MOCK_FACULTIES);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/byNameOrColor?name=" + MOCK_FACULTY_NAME + "&color=" + MOCK_FACULTY_COLOR)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(MOCK_FACULTIES)));
    }

    @Test
    public void testGetStudentsOfFaculty() throws Exception {
        when(repository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + 1 + "/students/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllFaculties() throws Exception {
        when(repository.findAll()).thenReturn(MOCK_FACULTIES);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

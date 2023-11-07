package ru.hogwarts.school.controller;

import ru.hogwarts.school.model.Faculty;

import java.util.Collections;
import java.util.List;

public class ConstantForTest {
    public static final Long MOCK_FACULTY_ID = 1L;
    public static final String MOCK_FACULTY_NAME = "Faculty name";
    public static final String MOCK_FACULTY_NEW_NAME = "Faculty new name";
    public static final String MOCK_FACULTY_COLOR = "RED";
    public static final Faculty MOCK_FACULTY = new Faculty(MOCK_FACULTY_ID, MOCK_FACULTY_NAME, MOCK_FACULTY_COLOR);
    public static final List<Faculty> MOCK_FACULTIES = Collections.singletonList(MOCK_FACULTY);
}

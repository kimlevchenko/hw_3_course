package ru.hogwards.school.service;

import org.springframework.stereotype.Service;
import ru.hogwards.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Service
public class StudentServiceImpl implements StudentService {

    private final HashMap<Long, Student> students = new HashMap<>();

    private long countID = 0;

    public Student addStudent(Student student) {
        student.setId(++countID);
        students.put(student.getId(), student);
        return student;
    }

    public Student findStudent(long id) {
        return students.get(id);
    }

    @Override
    public Collection<Student> findByAge(int age) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getAge() == age) {
                result.add(student);
            }
        }
        return result;
    }

    public Student editStudent(long id, Student student) {
        if (!students.containsKey(student.getId())) {
            return null;
        }
        students.put(student.getId(), student);
        return student;
    }

    public void deleteStudent(long id) {
        students.remove(id);
    }

    public Collection<Student> getAllStudents() {
        return students.values();
    }
}

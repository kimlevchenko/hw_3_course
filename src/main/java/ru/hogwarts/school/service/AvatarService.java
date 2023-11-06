package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;

import java.io.IOException;
import java.util.Collection;

public interface AvatarService {


    void upload(Long studentId, MultipartFile file) throws IOException;

    Avatar find(Long studentId);

    String saveFile(MultipartFile file, Student student);

    Collection<Avatar> find(int page, int pageSize);

}

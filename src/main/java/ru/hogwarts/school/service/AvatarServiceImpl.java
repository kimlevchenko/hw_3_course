package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;


    public AvatarServiceImpl(StudentRepository studentRepository,
                             AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public void upload(Long studentId, MultipartFile file) throws IOException {
        var student = studentRepository.findById(studentId)
                .orElseThrow(StudentNotFoundException::new);

        var dir = Path.of(avatarsDir);
        if (!dir.toFile().exists()) {
            Files.createDirectories(dir);
        }
        var path = saveFile(file, student);

        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        avatar.setFilePath(path);
        avatar.setData(file.getBytes());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setStudent(student);

        avatarRepository.save(avatar);
    }

    @Override
    public String saveFile(MultipartFile file, Student student) {
        var dotIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf('.');
        var ext = file.getOriginalFilename().substring(dotIndex + 1);
        var path = avatarsDir + "/" + student.getId() + "_" + student.getName() + "." + ext;
        try (var in = file.getInputStream();
             var out = new FileOutputStream(path)) {
            in.transferTo(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    @Override
    public Avatar find(Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(null);
    }
}

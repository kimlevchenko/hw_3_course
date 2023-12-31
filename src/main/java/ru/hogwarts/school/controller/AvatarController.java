package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    public final AvatarService service;

    public AvatarController(AvatarService service) {
        this.service = service;
    }

    @PostMapping(value = "/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void upload(@PathVariable Long studentId, @RequestParam MultipartFile file) throws IOException {
        service.upload(studentId, file);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<byte[]> findByIdStudent(@PathVariable Long studentId) {
        var avatar = service.findByIdStudent(studentId);
        if (avatar != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
            headers.setContentLength(avatar.getData().length);
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/file/{studentId}")
    public void findFile(@PathVariable Long studentId, HttpServletResponse response) throws IOException {
        var avatar = service.findByIdStudent(studentId);
        if (avatar == null) {
            response.setStatus(404);
            return;
        }
        File file = new File(avatar.getFilePath());
        if (!file.exists()) {
            response.setStatus(500);
            return;
        }
        response.setContentType(avatar.getMediaType());
        response.setContentLength((int) avatar.getFileSize());
        try (var out = response.getOutputStream();
             var in = new FileInputStream(avatar.getFilePath())) {
            in.transferTo(out);
        }
    }

    @GetMapping
    public Collection<Avatar> findAvatars(@RequestParam int page,
                                          @RequestParam int pageSize) {
        return service.find(page - 1, pageSize);
    }
}

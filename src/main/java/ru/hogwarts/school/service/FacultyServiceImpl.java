package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;
@Service
public class FacultyServiceImpl implements FacultyService {

    private final static Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty add(Faculty faculty) {
        logger.info("Add method was invoked");
        return repository.save(faculty);
    }

    @Override
    public Faculty get(Long id) {
        logger.info("Get method was invoked with argument {}", id);
        return repository.findById(id).orElse(null);
    }

    @Override
    public Collection<Faculty> findByNameOrColor(String name, String color) {
        logger.info("FindByNameOrColor method was invoked");
        return repository.findByNameOrColorIgnoreCase(name, color);
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("Update method was invoked with new argument {}", faculty);
        return repository.findById(faculty.getId())
                .map(entity -> repository.save(faculty))
                .orElse(null);
    }

    @Override
    public Faculty remove(Long id) {
        logger.info("Remove method was invoked with argument {}", id);
        var entity = repository.findById(id).orElse(null);
        if (entity != null) {
            repository.delete(entity);
        }
        return entity;
    }

    @Override
    public Collection<Faculty> getAllFaculties() {
        logger.info("GetAllFaculties method was invoked");
        return repository.findAll();
    }

    @Override
    public Collection<Student> getStudentsOfFaculty(Long id) {
        logger.info("GetStudentsOfFaculty method was invoked with argument {}", id);
        return repository.findById(id).map(Faculty::getStudents).orElse(Collections.emptyList());
    }

    @Override
    public Collection<Faculty> findByNameAndColor(String name, String color) {
        logger.info("FindByNameAndColor method was invoked with arguments name {} color {}", name, color);
        return repository.findByNameAndColor(name, color);
    }

    @Override
    public Faculty getLongestFacultyName() {
        logger.info("GetLongestFacultyName method was invoked");
        return repository.findAll().stream()
                .max(Comparator.comparing(faculty -> faculty.getName().length()))
                .orElseThrow(() -> new RuntimeException(String.valueOf(HttpStatus.NOT_FOUND)));
    }

    @Override
    public int getIntegerNumber() {
        long startTime = System.currentTimeMillis();
        logger.info("GetIntegerNumber method was invoked");
        Integer reduce = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, Integer::sum);
        long finishTime = System.currentTimeMillis() - startTime;
        logger.info("Время выполнения = " + finishTime);

        long startTime2 = System.currentTimeMillis();
        logger.info("GetIntegerNumber optimize method was invoked");
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .parallel()
                .reduce(0, Integer::sum);
        long finishTime2 = System.currentTimeMillis() - startTime2;
        logger.info("Время выполнения после оптимизации = " + finishTime2);
        return sum;
    }
}

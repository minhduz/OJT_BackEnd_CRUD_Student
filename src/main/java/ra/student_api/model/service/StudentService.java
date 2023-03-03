package ra.student_api.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.student_api.model.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    Student findById(int studentId);
    Student saveOrUpdate(Student student);
    void deleteById(int id);
    List<Student> findByStudentNameContaining(String studentName);
    Page<Student> getPagination(Pageable pageable);
    List<Student> sortByStudentName(String direction);
    List<Student> sortByStudentAge(String direction);
    List<Student> sortByAgeAndName(String directionAge,String directionName);
}

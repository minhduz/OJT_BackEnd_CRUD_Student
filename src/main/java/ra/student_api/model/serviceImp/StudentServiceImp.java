package ra.student_api.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.student_api.model.entity.Student;
import ra.student_api.model.repository.StudentRepository;
import ra.student_api.model.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImp implements StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(int studentId) {
        return studentRepository.findById(studentId).get();
    }

    @Override
    public Student saveOrUpdate(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteById(int id) {
          studentRepository.deleteById(id);
    }

    @Override
    public List<Student> findByStudentNameContaining(String studentName) {
        return studentRepository.findByStudentNameContaining(studentName);
    }

    @Override
    public Page<Student> getPagination(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    @Override
    public List<Student> sortByStudentName(String direction) {
        if(direction.equals("asc")){
            return studentRepository.findAll(Sort.by("studentName").ascending());
        }else {
            return studentRepository.findAll(Sort.by("studentName").descending());
        }
    }

    @Override
    public List<Student> sortByStudentAge(String direction) {
        if(direction.equals("asc")){
            return studentRepository.findAll(Sort.by("studentAge").ascending());
        }else {
            return studentRepository.findAll(Sort.by("studentAge").descending());
        }
    }

    @Override
    public List<Student> sortByAgeAndName(String directionAge, String directionName) {
        if(directionAge.equals("asc")){
            if(directionName.equals("asc")){
                return studentRepository.findAll(Sort.by("studentAge").and(Sort.by("studentName")));
            }else {
                return studentRepository.findAll(Sort.by("studentAge").and(Sort.by("studentName")).descending());
            }
        }else {
            if(directionName.equals("asc")){
                return studentRepository.findAll(Sort.by("studentAge").descending().and(Sort.by("studentName")));
            }else {
                return studentRepository.findAll(Sort.by("studentAge").descending().and(Sort.by("studentName")).descending());
            }

        }
    }
}

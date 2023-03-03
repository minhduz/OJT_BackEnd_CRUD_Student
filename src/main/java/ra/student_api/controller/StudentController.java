package ra.student_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.student_api.model.entity.Student;
import ra.student_api.model.service.StudentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("getAll")
    public List<Student> getAll() {
        return studentService.findAll();
    }

    @GetMapping("getByID")
    public Student getById(@RequestParam int studentId) {
        return studentService.findById(studentId);
    }

    @GetMapping("getPagingAndSortingByName")
    public ResponseEntity<Map<String, Object>> getPagingAndSortingByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam String direction
    ) {
        Sort.Order order;
        if (direction.equals("asc")) {
            order = new Sort.Order(Sort.Direction.ASC, "studentId");
        } else {
            order = new Sort.Order(Sort.Direction.DESC, "studentId");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        Page<Student> pageStudent = studentService.getPagination(pageable);
        Map<String, Object> data = new HashMap<>();
        List<Student> listStudent = pageStudent.getContent();
        data.put("listStudent", listStudent);
        data.put("total", pageStudent.getSize());
        data.put("totalItems", pageStudent.getTotalElements());
        data.put("totalPages", pageStudent.getTotalPages());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveOrUpdate(student);
    }

    @PutMapping("{studentId}")
    public Student updateStudent(@PathVariable("studentId") int studentId, @RequestBody Student student) {
        Student studentUpdate = studentService.findById(studentId);
        studentUpdate.setStudentName(student.getStudentName());
        studentUpdate.setStudentAge(student.getStudentAge());
        studentUpdate.setStudentStatus(student.isStudentStatus());
        return studentService.saveOrUpdate(studentUpdate);
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable("studentId") int studentId) {
        studentService.deleteById(studentId);
        return ResponseEntity.ok("Student have been deleted");
    }

    @GetMapping("getByName")
    public List<Student> searchStudentByName(@RequestParam String studentName){
        return studentService.findByStudentNameContaining(studentName);
    }

    @GetMapping("sortByName")
    public ResponseEntity<List<Student>> sortByStudentName(@RequestParam("direction") String direction){
        List<Student> listStudent = studentService.sortByStudentName(direction);
        return new ResponseEntity<>(listStudent,HttpStatus.OK);
    }

    @GetMapping("sortByAge")
    public ResponseEntity<List<Student>> sortByStudentAge(@RequestParam("direction") String direction){
        List<Student> listStudent = studentService.sortByStudentAge(direction);
        return new ResponseEntity<>(listStudent,HttpStatus.OK);
    }

    @GetMapping("sortByAgeAndName")
    public ResponseEntity<List<Student>> sortByStudentAgeAndName(@RequestParam("directionAge") String directionAge,
                                                                 @RequestParam("directionName") String directionName){
        List<Student> listStudent = studentService.sortByAgeAndName(directionAge,directionName);
        return new ResponseEntity<>(listStudent,HttpStatus.OK);
    }
}

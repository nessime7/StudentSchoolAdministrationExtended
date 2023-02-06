package com.student.controller;

import com.student.model.*;
import com.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("students")
    public ResponseEntity<List<Student>> getStudents(@RequestParam("unassigned") boolean unassigned) {
        return ResponseEntity.ok(studentService.getStudents(unassigned));
    }

    @GetMapping("classes")
    public ResponseEntity<List<SchoolClass>> getStudentsInClass() {
        return ResponseEntity.ok(studentService.getSchoolClasses());
    }

    @PostMapping("classes/{id}/students")
    public ResponseEntity<SchoolClass> createStudent(@PathVariable UUID id, @RequestBody AssignStudentToClassRequest request) {
        final var studentInClass = studentService.assignStudentToClass(id, request);
        return ResponseEntity.ok(studentInClass);
    }

    @PostMapping("students")
    public ResponseEntity<List<Student>> createStudent(@RequestBody CreateStudentRequest request) {
        final var unassignedStudents = studentService.createStudentInUnassignedStudents(request);
        return ResponseEntity.ok(unassignedStudents);
    }

    @PostMapping("classes")
    public ResponseEntity<Void> addSchoolClass(@RequestBody CreateSchoolClassRequest request) {
        studentService.createSchoolClass(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("students/{id}")
    public ResponseEntity<Student> editStudent(@PathVariable UUID id, @RequestBody EditStudentRequest request) {
        final var student = studentService.editStudent(id, request);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("classes/{classId}/students/{studentsId}")
    public ResponseEntity<Void> deleteStudentFromClass(@PathVariable("classId") UUID classId,
                                                       @PathVariable("studentsId") UUID studentsId) {
        studentService.deleteStudentFromClass(classId, studentsId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id) {
        studentService.deleteUnassignedStudent(id);
        return ResponseEntity.noContent().build();
    }
}
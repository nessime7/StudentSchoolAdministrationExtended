package com.student.service;

import com.student.model.*;
import com.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<SchoolClass> getSchoolClasses() {
        return studentRepository.getSchoolClasses();
    }

    public SchoolClass assignStudentToClass(UUID classId, AssignStudentToClassRequest request) {
        final var student = studentRepository.getUnassignedStudentById(request.getId());
        studentRepository.getUsassignedStudents().remove(student);
        final var studentClass = studentRepository.getSchoolClassById(classId);
        studentClass.getStudents().add(student);
        return studentClass;
    }

    public List<Student> createStudentInUnassignedStudents(CreateStudentRequest request) {
        final var createdStudent = new Student(UUID.randomUUID(),
                request.getName(),
                request.getSurname(),
                request.getEmail(),
                request.getAge());
        if (request.getClassId() != null) {
            studentRepository.getSchoolClassById(request.getClassId()).getStudents().add(createdStudent);
        } else {
            final var unassignedStudents = studentRepository.getUsassignedStudents();
            unassignedStudents.add(createdStudent);
        }
        return getStudents(false);
    }

    public Student editStudent(UUID id, EditStudentRequest request) {
        final var classStudents = studentRepository.getSchoolClasses().stream()
                .flatMap(c -> c.getStudents().stream());
        final var unassignedStudents = studentRepository.getUnassignedStudents().stream();
        return Stream.concat(classStudents, unassignedStudents)
                .filter(student -> student.getId().equals(id))
                .peek(student -> editStudent(request, student))
                .findFirst()
                .orElseThrow();
    }

    private void editStudent(EditStudentRequest request, Student a) {
        if (request.getName() != null) {
            a.setName(request.getName());
        }

        if (request.getAge() != 0) {
            a.setAge(request.getAge());
        }

        if (request.getEmail() != null) {
            a.setEmail(request.getEmail());
        }

        if (request.getSurname() != null) {
            a.setSurname(request.getSurname());
        }
    }

    public void deleteUnassignedStudent(UUID id) {
        final var student = studentRepository.getUnassignedStudentById(id);
        studentRepository.getUnassignedStudents().remove(student);
    }

    public void createSchoolClass(CreateSchoolClassRequest request) {
        studentRepository.saveNewSchoolClass(new SchoolClass(UUID.randomUUID(), request.getName(), new ArrayList<>()));
    }

    public void deleteStudentFromClass(UUID classId, UUID studentId) {
        final var student = studentRepository.getStudentByClassIdAndId(classId, studentId);
        studentRepository.removeStudentFromClassById(classId, studentId);
        studentRepository.getUnassignedStudents().add(student);
    }

    public List<Student> getStudents(boolean unassigned) {
        final var unassignedStudents = studentRepository.getUnassignedStudents();
        if (unassigned) {
            return unassignedStudents;
        }
        return Stream.concat(unassignedStudents.stream(), studentRepository.getClassStudents().stream())
                .collect(Collectors.toList());
    }
}

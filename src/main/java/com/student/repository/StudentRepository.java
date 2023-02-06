package com.student.repository;

import com.student.model.SchoolClass;
import com.student.model.Student;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class StudentRepository {

    private final List<Student> usassignedStudents = new ArrayList<>(Set.of(
            new Student(UUID.randomUUID(), "Sara", "Przebinda", "sara.przebinda@gmail.com", 28),
            new Student(UUID.randomUUID(), "Albert", "Podraza", "albert.podraza@gmail.com", 28),
            new Student(UUID.randomUUID(), "Julia", "Przebinda", "julia.przebinda@gmail.com", 17)
    ));

    private List<SchoolClass> schoolClasses = new ArrayList<>(List.of(
            new SchoolClass(UUID.randomUUID(), "1A", new ArrayList<>()),
            new SchoolClass(UUID.randomUUID(), "1B", new ArrayList<>())));


    public List<SchoolClass> getSchoolClasses() {
        return schoolClasses;
    }

    public SchoolClass getSchoolClassById(UUID id) {
        return getSchoolClasses().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

    public List<Student> getUsassignedStudents() {
        return usassignedStudents;
    }

    public Student getUnassignedStudentById(UUID studentId) {
        return getUnassignedStudents().stream()
                .filter(s -> s.getId().equals(studentId))
                .findFirst()
                .orElseThrow();
    }

    public void saveNewSchoolClass(SchoolClass schoolClass) {
        schoolClasses.add(schoolClass);
    }

    public void removeStudentById(UUID id) {
        final var student = getSchoolClasses().stream()
                .flatMap(a -> a.getStudents().stream())
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow();

        getSchoolClasses()
                .forEach(a -> a.getStudents().remove(student));
        getUsassignedStudents().remove(student);
    }

    public void removeStudentFromClassById(UUID classId, UUID studentId) {
        final var student = getStudentByClassIdAndId(classId, studentId);
        getSchoolClasses().stream()
                .filter(a -> a.getId().equals(classId))
                .forEach(c -> c.getStudents().remove(student));
    }

    public List<Student> getUnassignedStudents() {
        return usassignedStudents;
    }

    public Student getStudentByClassIdAndId(UUID classId, UUID studentId) {
        return schoolClasses.stream()
                .filter(c -> c.getId().equals(classId))
                .flatMap(c -> c.getStudents().stream())
                .filter(s -> s.getId().equals(studentId)).findFirst().orElseThrow();
    }

    public List<Student> getClassStudents() {
        return getSchoolClasses().stream()
                .flatMap(c -> c.getStudents().stream())
                .collect(Collectors.toList());
    }
}

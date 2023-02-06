package com.student.model;

import java.util.UUID;

public class AssignStudentToClassRequest {

    private UUID id;

    public AssignStudentToClassRequest(UUID id) {
        this.id = id;
    }

    public AssignStudentToClassRequest() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

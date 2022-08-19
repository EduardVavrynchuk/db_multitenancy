package com.evavrynchuk.multitenancy.service;

import com.evavrynchuk.multitenancy.entity.Student;
import java.util.List;

public interface StudentService {

    List<Student> getAll();

    Student create(Student student);

    void delete(Long id);
}

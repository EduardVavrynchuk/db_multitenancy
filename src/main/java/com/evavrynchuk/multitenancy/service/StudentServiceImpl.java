package com.evavrynchuk.multitenancy.service;

import com.evavrynchuk.multitenancy.entity.Student;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.ws.rs.BadRequestException;

@SessionScoped
public class StudentServiceImpl extends BaseEntityManager implements StudentService, Serializable {

    @Override
    public List<Student> getAll() {
        return getEntityManager().createNamedQuery(Student.QUERY_FIND_ALL, Student.class).getResultList();
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public Student create(Student student) {
        getEntityManager().persist(student);
        getEntityManager().flush();

        return student;
    }

    @Override
    @Transactional(TxType.REQUIRED)
    public void delete(Long id) {
        Student student = getEntityManager().find(Student.class, id);

        if (student == null) {
            throw new BadRequestException("No student by presented id: " + id);
        }

        getEntityManager().remove(student);
        getEntityManager().flush();
    }
}

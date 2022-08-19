package com.evavrynchuk.multitenancy.rest.api;

import com.evavrynchuk.multitenancy.entity.Student;
import com.evavrynchuk.multitenancy.service.StudentService;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@SessionScoped
@Path("/student")
public class StudentApi implements Serializable {

    @Inject
    StudentService studentService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getAll() {
        return studentService.getAll();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Student create(Student student) {
        return studentService.create(student);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        studentService.delete(id);
    }
}

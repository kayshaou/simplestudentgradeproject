package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@TestPropertySource("/application.properties")
@SpringBootTest
class GradebookCollegeStudentTest {

    @Autowired
    StudentAndGradeService studentService;
    @Autowired
    StudentDao studentDao;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void createStudentService() {
        String email = "chad.darby@luv2code-school.com";
        studentService.createStudent("Chad", "darby",email);

        CollegeStudent student = studentDao.findByEmailAddress(email);
        assertEquals(email, student.getEmailAddress(), "email address should return "+email);
    }

    @Test
    public void isStudentNullCheck(){
        Iterator i = studentDao.findAll().iterator();
        while(i.hasNext()){
            System.out.println(i.next());
        }
        assertTrue(studentService.checkIfStudentIsPresent(1));
        assertFalse(studentService.checkIfStudentIsPresent(0));
    }

    @BeforeEach
    public void insertSampleData(){
        jdbcTemplate.execute("insert into student (id, firstname, lastname, email_address) values (1, 'Eric', 'Roby', 'eric.roby@email.com')");
    }

    @Test
    public void deleteStudent() {
        Optional<CollegeStudent> student = studentDao.findById(1);

        assertTrue(student.isPresent(), "return True");

        studentService.deleteStudent(1);

        Optional<CollegeStudent> studentAfter = studentDao.findById(1);

        assertFalse(studentAfter.isPresent(), "return False");
    }

    @Sql("/insertDataSql.sql")
    @Test
    public void getGradebookService() {
        Iterable<CollegeStudent> studentList = studentDao.findAll();
        assertEquals(5, ((Collection<?>) studentList).size());
    }

    @AfterEach
    public void clear(){
        jdbcTemplate.execute(" delete from student ");
    }

}
package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentAndGradeService {
    @Autowired
    StudentDao studentDao;
    public void createStudent(String firstname, String lastname, String email){
        CollegeStudent collegeStudent= new CollegeStudent(firstname, lastname, email);
        collegeStudent.setId(0);
        studentDao.save(collegeStudent);
    }

    public boolean checkIfStudentIsPresent(Integer id){
        Optional<CollegeStudent> student = studentDao.findById(id);
        if(student.isPresent()){
            return true;
        }
        return false;
    }

    public void deleteStudent(Integer id){
        if(checkIfStudentIsPresent(id)) {
            studentDao.deleteById(id);
        }
    }
}

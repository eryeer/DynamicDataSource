package com.wxbc.controller;

import com.github.pagehelper.PageInfo;
import com.wxbc.pojo.Student;
import com.wxbc.service.StudentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zhaochen on 2019/1/26.
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping(value = "/insertStudent")
    @ApiOperation(value = "/insertStudent", notes = "插入记录",
            response = String.class)
    public String insertStudent(Student student) {
        studentService.insertStudent(student);
        return "success";
    }

    @PostMapping(value = "/getStudent")
    @ApiOperation(value = "/getStudent", notes = "获取记录",
            response = List.class)
    public PageInfo<Student> getStudent() {
        return studentService.getStudent();

    }


}

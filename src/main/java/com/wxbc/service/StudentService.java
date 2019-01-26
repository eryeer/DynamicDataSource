package com.wxbc.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxbc.dao.AgeDao;
import com.wxbc.dao.StudentDao;
import com.wxbc.datasource.DataSourceRouter;
import com.wxbc.datasource.DynamicDataSource;
import com.wxbc.datasource.DynamicDataSourceHolder;
import com.wxbc.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentDao studentDao;

    @Autowired
    AgeDao ageDao;

    @Transactional
    @DataSourceRouter
    public void insertStudent(Student student) {

        studentDao.insertStudent(student);
        ageDao.insertAge(student);
    }

    @Transactional
    @DataSourceRouter(DynamicDataSourceHolder.SLAVE)
    public PageInfo<Student> getStudent() {
        PageHelper.startPage(1, 2);
        List<Student> students = studentDao.getStudent();
        return new PageInfo<>(students);
    }
}

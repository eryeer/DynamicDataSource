package com.wxbc.dao;

import com.wxbc.pojo.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentDao {

    @Insert("insert into student (name,language_name) value (#{name},#{languageName})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    void insertStudent(Student student);

    @Select("select * from student")
    List<Student> getStudent();
}

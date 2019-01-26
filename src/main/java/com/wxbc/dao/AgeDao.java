package com.wxbc.dao;

import com.wxbc.pojo.Student;
import org.apache.ibatis.annotations.Insert;

public interface AgeDao {
    @Insert("insert into age (age,student_id) value(#{age},#{id})")
    void insertAge(Student student);
}

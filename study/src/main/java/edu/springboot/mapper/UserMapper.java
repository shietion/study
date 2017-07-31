package edu.springboot.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import edu.springboot.model.User;

@Repository
public interface UserMapper {

    @Select("SELECT * FROM sys_user WHERE name = #{name}")
    User findByName(@Param("name") String name);

    @Results({ @Result(property = "name", column = "name"), @Result(property = "age", column = "age") })
    @Select("SELECT id, name, age FROM sys_user")
    List<User> findAll();

    @Insert("INSERT INTO sys_user(name, age) VALUES(#{name}, #{age})")
    int insert(@Param("name") String name, @Param("age") Integer age);

    @Update("UPDATE sys_user SET age=#{age} WHERE name=#{name}")
    void update(User user);

    @Delete("DELETE FROM sys_user WHERE id =#{id}")
    void delete(Long id);

    @Insert("INSERT INTO sys_user(name, age) VALUES(#{name}, #{age})")
    int insertByUser(User user);

    @Insert("INSERT INTO sys_user(name, age) VALUES(#{name,jdbcType=VARCHAR}, #{age,jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);
}

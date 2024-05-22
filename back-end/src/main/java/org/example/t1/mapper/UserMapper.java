package org.example.t1.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.t1.entity.User;

import java.util.List;

/**
 * @author 张喆宇
 * @Description:
 * @date 2024/5/18 21:03
 */
@Mapper
public interface UserMapper {
    // 查询所有用户
    @Select("select * from users")
    public List<User> findAll();

    @Insert("insert into users values(#{id},#{username},#{password},#{email})")
    public int insert(User user);

    //根据用户名查找用户
    @Select("Select * from users where username=#{username}")
    public User findUserByName(String username);

}

package org.example.t1.dao;


import org.example.t1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//jpa默认具有一些crud操作方法
/*
save(User entity)：保存一个实体。
findById(Integer id)：按ID查找一个实体。
findAll()：查找所有实体。
deleteById(Integer id)：按ID删除一个实体。
 */

public interface UserDao extends JpaRepository<User, Integer>{
    //根据用户名创建用户
    User findDistinctByUsername(String username);

    User findDistinctByEmail(String email);
}

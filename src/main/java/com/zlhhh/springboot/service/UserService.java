package com.zlhhh.springboot.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zlhhh.springboot.entity.User;
import com.zlhhh.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService  extends ServiceImpl<UserMapper, User> {
    public boolean saveUser(User user) {
//        if (user.getId() == null) {
//            return save(user);  //  mybatis-plus 提供的方法，表示插入数据
//
//        } else {
//            return updateById(user);
//        }
        return saveOrUpdate(user);
    }

//    @Autowired
//    private UserMapper userMapper;
//    public int save(User user) {
//        if(user.getId() == null) {  //user没有ID则新增，否则更新
//            return userMapper.insert(user);
//        } else {
//            return userMapper.update(user);
//        }
//    }
}

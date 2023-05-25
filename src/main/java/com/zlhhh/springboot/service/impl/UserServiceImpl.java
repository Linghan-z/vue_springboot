package com.zlhhh.springboot.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlhhh.springboot.controller.dto.UserDTO;
import com.zlhhh.springboot.entity.User;
import com.zlhhh.springboot.mapper.UserMapper;
import com.zlhhh.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.One;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zlhhh
 * @since 2023-05-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();
    @Override
    public boolean login(UserDTO userDTO) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, userDTO.getUsername());
        lambdaQueryWrapper.eq(User::getPassword, userDTO.getPassword());
        try {
            User one = getOne(lambdaQueryWrapper);
            return one != null;
        } catch (Exception e) {
            e.printStackTrace();  //打印异常的堆栈
            LOG.error(e);
            return false;
        }
    }
}

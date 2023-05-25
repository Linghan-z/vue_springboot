package com.zlhhh.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlhhh.springboot.common.Constants;
import com.zlhhh.springboot.controller.dto.UserDTO;
import com.zlhhh.springboot.entity.User;
import com.zlhhh.springboot.exception.ServiceException;
import com.zlhhh.springboot.mapper.UserMapper;
import com.zlhhh.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.One;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zlhhh
 * @since 2023-05-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

    @Override
    public UserDTO login(UserDTO userDTO) {
        User one = getUserInfo(userDTO);
        if (one != null) {  // 业务异常
            BeanUtil.copyProperties(one, userDTO, true);
            return userDTO;
        } else {
            // 失败会抛出异常，抛出异常之后会被全局异常捕获器GlobalExceptionHandler捕获到，捕获完之后返回一个error
            throw new ServiceException(Constants.CODE_600, "用户名或密码错误");
        }
    }

    @Override
    public User register(UserDTO userDTO) {

        User one = getUserInfo(userDTO);
        if (one == null) {
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            save(one);  // 把 copy 之后的用户对象存入数据库
        } else {
            throw new ServiceException(Constants.CODE_600, "用户已存在");
        }
        return one;
    }

    private  User getUserInfo(UserDTO userDTO) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, userDTO.getUsername());
        lambdaQueryWrapper.eq(User::getPassword, userDTO.getPassword());
        User one;
        try {
            one = getOne(lambdaQueryWrapper);  // 从数据库查询用户信息

        } catch (Exception e) {
            e.printStackTrace();  //  打印异常的堆栈
            LOG.error(e);
            // sql查询发生的错误（sql系统），返回错误信息
            throw  new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }
}

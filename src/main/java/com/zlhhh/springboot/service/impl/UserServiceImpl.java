package com.zlhhh.springboot.service.impl;

import com.zlhhh.springboot.entity.User;
import com.zlhhh.springboot.mapper.UserMapper;
import com.zlhhh.springboot.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}

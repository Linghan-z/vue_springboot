package com.zlhhh.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlhhh.springboot.entity.User;
import com.zlhhh.springboot.mapper.UserMapper;
import com.zlhhh.springboot.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @PostMapping
    public boolean save(@RequestBody User user) {
        // 新增或更新
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }
    @PostMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeBatchByIds(ids);
    }

    // 分页查询
    // @RequestParam 接收 ?pageNum=1&pageSize=10
    // limit 第一个参数： (pageNum - 1) * pageSize
    // limit 第二个参数： pageSize
//    @GetMapping("/page")
//    public Map<String, Object> findPage(@RequestParam Integer pageNum,
//                                        @RequestParam Integer pageSize,
//                                        @RequestParam String username) {
//        pageNum = (pageNum - 1) * pageSize;
//        username = "%" + username + "%";
//        List<User> data = userMapper.selectPage(pageNum, pageSize, username);
//        Integer total = userMapper.selectTotal(username);
//        Map<String, Object> res = new HashMap<>();
//        res.put("data", data);
//        res.put("total", total);
//        return res;
//    }
    // 分页查询 mybatis-plus
    @GetMapping("/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address,
                                @RequestParam(defaultValue = "") String avatar) {
        IPage<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.like("username", username);
//        if (!"".equals(username)) {
//
//        }
        lambdaQueryWrapper.like(Strings.isNotEmpty(username),User::getUsername,username);
        lambdaQueryWrapper.like(Strings.isNotEmpty(email), User::getEmail, email);
        lambdaQueryWrapper.like(Strings.isNotEmpty(address), User::getAddress, address);
        lambdaQueryWrapper.like(Strings.isNotEmpty(avatar), User::getAvatar, avatar);
        lambdaQueryWrapper.orderByDesc(User::getId);
        return userService.page(page, lambdaQueryWrapper);
    }

}

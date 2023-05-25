package com.zlhhh.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlhhh.springboot.controller.dto.UserDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.zlhhh.springboot.service.IUserService;
import com.zlhhh.springboot.entity.User;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zlhhh
 * @since 2023-05-24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    public boolean login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return false;
        }
        return userService.login(userDTO);
    }

    @PostMapping
    public boolean save(@RequestBody User user) {
        // 新增或更新
        return userService.saveOrUpdate(user);
    }

    @PostMapping("/del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeBatchByIds(ids);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @GetMapping("/search")
    public Page<User> fuzzySearch(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam(defaultValue = "") String username,
                                  @RequestParam(defaultValue = "") String email,
                                  @RequestParam(defaultValue = "") String address) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Strings.isNotEmpty(username), User::getUsername, username);
        lambdaQueryWrapper.like(Strings.isNotEmpty(email), User::getEmail, email);
        lambdaQueryWrapper.like(Strings.isNotEmpty(address), User::getAddress, address);
        return userService.page(new Page<>(pageNum, pageSize), lambdaQueryWrapper);
    }

    @GetMapping("/page")
    public Page<User> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize) {
        return userService.page(new Page<>(pageNum, pageSize));
    }

    
//    @GetMapping("/page")
//    public Page<User> findPage(@RequestParam Integer pageNum,
//                               @RequestParam Integer pageSize,
//                               @RequestParam(defaultValue = "") String username,
//                               @RequestParam(defaultValue = "") String email,
//                               @RequestParam(defaultValue = "") String address) {
//        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.like(Strings.isNotEmpty(username), User::getUsername, username);
//        lambdaQueryWrapper.like(Strings.isNotEmpty(email), User::getEmail, email);
//        lambdaQueryWrapper.like(Strings.isNotEmpty(address), User::getAddress, address);
//        return userService.page(new Page<>(pageNum, pageSize), lambdaQueryWrapper);
//    }

}

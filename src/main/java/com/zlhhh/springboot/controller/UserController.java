package com.zlhhh.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlhhh.springboot.common.Constants;
import com.zlhhh.springboot.common.Result;
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
    public Result login(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            // 失败
            return Result.error(Constants.CODE_400, "参数错误");
        }
        UserDTO dto = userService.login(userDTO);
        return Result.success(dto);  // 成功调用
    }


    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            // 失败
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.register(userDTO));
    }
    @PostMapping
    public Result save(@RequestBody User user) {
        // 新增或更新
        return Result.success(userService.saveOrUpdate(user));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(userService.removeBatchByIds(ids));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(userService.removeById(id));
    }

    @GetMapping
    public Result findAll() {
        return Result.success(userService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

    @GetMapping("/username/{username}")
    public Result findOne(@PathVariable String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username);
        return Result.success(userService.getOne(lambdaQueryWrapper));
    }

    @GetMapping("/search")
    public Result fuzzySearch(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam(defaultValue = "") String username,
                                  @RequestParam(defaultValue = "") String email,
                                  @RequestParam(defaultValue = "") String address) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Strings.isNotEmpty(username), User::getUsername, username);
        lambdaQueryWrapper.like(Strings.isNotEmpty(email), User::getEmail, email);
        lambdaQueryWrapper.like(Strings.isNotEmpty(address), User::getAddress, address);
        return Result.success(userService.page(new Page<>(pageNum, pageSize), lambdaQueryWrapper));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize) {
        return Result.success(userService.page(new Page<>(pageNum, pageSize)));
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

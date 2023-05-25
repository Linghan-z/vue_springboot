package com.zlhhh.springboot.controller.dto;

import lombok.Data;

/**
 * 接收前端登陆请求的参数
 */
@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
}

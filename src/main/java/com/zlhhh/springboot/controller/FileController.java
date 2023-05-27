package com.zlhhh.springboot.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlhhh.springboot.common.Result;
import com.zlhhh.springboot.entity.Files;
import com.zlhhh.springboot.entity.User;
import com.zlhhh.springboot.mapper.FileMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 文件上传相关接口
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FileMapper fileMapper;


    /**
     * 文件上传接口
     *
     * @param file 前端传递过来的文件
     * @return
     * @throws IOException
     */

//    @PostMapping("/upload")
//    public String upload(@RequestParam MultipartFile file) throws IOException {
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public String upload(@RequestPart("file") MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUid = uuid + StrUtil.DOT + type;

        File uploadFile = new File((fileUploadPath + fileUUid));
        // 判断配置的文件父级目录是否存在，若不存在则创建一个新的目录
        File parentFile = uploadFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        String url;
        // 把获取到的文件存储到磁盘目录去
        file.transferTo(uploadFile);
        // 获取文件的md5
        String md5 = SecureUtil.md5(uploadFile);
        // 从数据库查询是否有相同的记录
        Files dbFiles = getFileByMd5(md5);
        // 获取文件的url
        if (dbFiles != null) {
            url = dbFiles.getUrl();
            // 删除已存在的文件
            uploadFile.delete();
        } else {
            // 数据库不存在重复文件，则不删除刚才上传的文件
            url = "http://localhost:8080/file/" + fileUUid;
        }


        // 存储数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);


        fileMapper.insert(saveFile);
        return url;

    }

    /**
     * 文件下载路径接口 http://localhost:8080/file/{fileUUid}
     *
     * @param fileUUid
     * @param response
     * @throws IOException
     */

    @GetMapping("{fileUUid}")
    public void download(@PathVariable String fileUUid, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File((fileUploadPath + fileUUid));
        ServletOutputStream os = response.getOutputStream();
        // 设置输出流的格式
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUid, StandardCharsets.UTF_8));
        response.setContentType("application/octet-stream");
//        response.setContentType("multipart/form-data");
        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    /**
     * 通过文件的md5查询文件
     *
     * @param md5
     * @return
     */
    private Files getFileByMd5(String md5) {
        LambdaQueryWrapper<Files> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Files::getMd5, md5);
        List<Files> filesList = fileMapper.selectList(lambdaQueryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                              @RequestParam Integer pageSize,
                              @RequestParam(defaultValue = "") String name) {
        LambdaQueryWrapper<Files> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Files::getIs_delete, false);  // 查询未删除的记录
        lambdaQueryWrapper.like(Strings.isNotEmpty(name), Files::getName, name);
        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize), lambdaQueryWrapper));
    }


    @PostMapping("/update")
    public Result save(@RequestBody Files files) {
        // 更新
        return Result.success(fileMapper.updateById(files));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = fileMapper.selectById(id);
        files.setIs_delete(true);
        fileMapper.updateById(files);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        // select * from sys_file where id in (id,id,id...)
        LambdaQueryWrapper<Files> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Files::getId,ids);
        List<Files> files = fileMapper.selectList(lambdaQueryWrapper);
        for (Files file : files){
            file.setIs_delete(true);
            fileMapper.updateById(file);
        }
        return Result.success();
    }

}

package com.mszlu.blog.controller;

import com.mszlu.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Author Peekaboo
 * Date 2022/2/15 17:21
 */
@RestController
@RequestMapping("upload")
public class UploadController {

//    @PostMapping
//    public Result upload(@RequestParam("image")MultipartFile multipartFile){
//        //获取原始文件名称
//        String originalFilename = multipartFile.getOriginalFilename();
//        //生成唯一文件名称,截取在第二个字符参数之后的字符串
//        String s = String.valueOf(UUID.randomUUID()) + "." + StringUtils.substringAfterLast(originalFilename, ".");
//    }
}

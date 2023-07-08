package com.reggie_take_out.controller;

import com.reggie_take_out.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/common")
public class CommonController {
    @Value("${reggie.path}")
    private String basepath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();  //使用原有文件名 可能文件名一致会覆盖
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.'));
        //使用UUID重新生成文件名
        String filename = UUID.randomUUID().toString() + suffix;
        File file1 = new File(basepath);
        if (!file1.exists()){
            file1.mkdirs();
        }
        try {
            file.transferTo(new File(basepath + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(filename);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        //输入流，读取文件内容
        FileInputStream fileInputStream = new FileInputStream(new File(basepath + name));
        //输出流 写回浏览器
        ServletOutputStream outputStream = response.getOutputStream();
        int len = 0;
        response.setContentType("image/jpeg");
        byte[] bytes = new byte[1024];
        while ((len = fileInputStream.read(bytes)) != -1){
            outputStream.write(bytes,0,len);
            outputStream.flush();
        }
        outputStream.close();
        fileInputStream.close();
    }
}

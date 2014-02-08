package com.smartestgift.controller;

/**
 * Created with IntelliJ IDEA.
 * User: dikkini
 * Date: 11/17/13
 * Time: 12:15 PM
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smartestgift.dao.FileDAO;
import com.smartestgift.dao.model.File;
import com.smartestgift.dao.model.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileDAO fileDAO;

    File file = null;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody String uploadFile(MultipartHttpServletRequest request, HttpServletResponse response) {

        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;
        Gson gson = new GsonBuilder().create();

        while (itr.hasNext()) {

            mpf = request.getFile(itr.next());

            try {
                file = new File(mpf.getOriginalFilename(), String.valueOf(mpf.getSize() / 1024), new FileType());
                fileDAO.store(file);
                System.out.println(mpf.getOriginalFilename() + " uploaded! id = " + file.getId());

                // TODO абсолютные пути это плохо
                try {
                    FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("/home/dikkini/" + mpf.getOriginalFilename()));
                } catch (FileNotFoundException e) {
                    FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("C:\\temp\\" + mpf.getOriginalFilename()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return gson.toJson(file);
    }

    @RequestMapping(value = "/get/{value}", method = RequestMethod.GET)
    public void getFileById(HttpServletResponse response, @PathVariable Integer value) {
        File getFile = fileDAO.find(value.longValue());
        java.io.File file = new java.io.File(getFile.getType().getPath() + getFile.getName());

        try {
            FileInputStream fis = new FileInputStream(file);
            response.setContentType(getFile.getType().getName());
            response.setHeader("Content-disposition", "attachment; filename=\"" + getFile.getName() + "\"");
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] buf = new byte[(int) file.length()];

            int c;
            while ((c = fis.read(buf, 0, buf.length)) > 0) {
                outputStream.write(buf, 0, c);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteFileById(@RequestParam(value = "fileId", required = true) String fileId,
                                  HttpServletResponse response) {
        File file = fileDAO.find(Long.valueOf(fileId));
        fileDAO.delete(file);
    }
}
package com.smartestgift.controller;

/**
 * Created by dikkini on 17.11.13.
 * Email: dikkini@gmail.com
 */

import com.google.gson.Gson;
import com.smartestgift.dao.model.File;
import com.smartestgift.dao.model.SmartUserDetails;
import com.smartestgift.service.FileService;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.ActiveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private SmartUserService smartUserService;

    @Autowired
    private FileService fileService;

    @Autowired
    private Gson gson;

    @RequestMapping(value = "/uploadUserPhoto", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public @ResponseBody String uploadFile(@ActiveUser SmartUserDetails smartUserDetails,
                                           @RequestParam (required = true, value = "fileTypeId") Integer fileTypeId,
                                           MultipartHttpServletRequest request, HttpServletResponse response) {

        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;
        File file = null;

        while (itr.hasNext()) {

            mpf = request.getFile(itr.next());

            try {
                //Long fileSize = mpf.getSize() / 1024;
                file = fileService.uploadFile(mpf.getOriginalFilename(), fileTypeId);
                smartUserService.updateUserFile(smartUserDetails.getSmartUser(), file);

                // TODO абсолютные пути это плохо
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("C:\\temp\\" + mpf.getOriginalFilename()));
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("/Volumes/Storage/temp/" + mpf.getOriginalFilename()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return gson.toJson(file);
    }

    @RequestMapping(value = "/get/{fileId}", method = RequestMethod.GET)
    public void getFileById(HttpServletResponse response, @PathVariable Integer fileId) {
        File file = fileService.getFile(fileId);
        java.io.File ioFile = new java.io.File(file.getType().getPath() + file.getName());

        try {
            FileInputStream fis = new FileInputStream(ioFile);
            response.setContentType(file.getType().getName());
            response.setHeader("Content-disposition", "attachment; filename=\"" + ioFile.getName() + "\"");
            ServletOutputStream outputStream = response.getOutputStream();
            byte[] buf = new byte[(int) ioFile.length()];

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
    public void deleteFileById(HttpServletResponse response,
                               @RequestParam(value = "fileId", required = true) Integer fileId) {
        fileService.deleteFile(fileId);
    }
}
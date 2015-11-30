package com.smartestgift.service;

import com.smartestgift.dao.FileDAO;
import com.smartestgift.dao.model.File;
import com.smartestgift.dao.model.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dikkini on 09/06/14.
 */

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDAO fileDAO;

    @Override
    public File uploadFile(String name, Integer typeId) {
        FileType fileType = fileDAO.findFileTypeById(typeId);
        File file = new File(name,  fileType);
        fileDAO.create(file);
        return file;
    }

    @Override
    public File getFile(Integer fileId) {
        return fileDAO.findOne(fileId);
    }

    @Override
    public void deleteFile(Integer fileId) {
        File file = fileDAO.findOne(fileId);
        fileDAO.delete(file);
    }
}

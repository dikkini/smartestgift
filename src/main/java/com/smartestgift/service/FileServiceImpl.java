package com.smartestgift.service;

import com.smartestgift.dao.FileDAO;
import com.smartestgift.dao.model.File;
import com.smartestgift.dao.model.FileType;
import com.smartestgift.utils.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
        fileDAO.store(file);
        return file;
    }

    @Override
    @Cacheable("files")
    public File getFile(Integer fileId) {
        return fileDAO.find(fileId);
    }

    @Override
    public void deleteFile(Integer fileId) {
        File file = fileDAO.find(fileId);
        fileDAO.delete(file);
    }
}

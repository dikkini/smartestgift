package com.smartestgift.service;

import com.smartestgift.dao.model.File;

/**
 * Created by dikkini on 09/06/14.
 */
public interface FileService {

    public File uploadFile(String name, Integer typeId);

    public File getFile(Integer fileId);

    public void deleteFile(Integer fileId);
}

package com.smartestgift.dao;

import com.smartestgift.dao.model.File;
import com.smartestgift.dao.model.FileType;

/**
 * Created by dikkini on 19.11.13.
 * Email: dikkini@gmail.com
 */
public interface FileDAO extends Repository<File, Integer> {

    /**
     *
     * @param typeId
     * @return
     */
    public FileType findFileTypeById(Integer typeId);
}

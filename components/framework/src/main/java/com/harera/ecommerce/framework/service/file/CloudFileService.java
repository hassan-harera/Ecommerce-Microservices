package com.harera.ecommerce.framework.service.file;

import java.io.File;

public interface CloudFileService {

    public String uploadFile(File file);

    public void deleteFile(String url);
}

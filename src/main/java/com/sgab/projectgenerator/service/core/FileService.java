package com.sgab.projectgenerator.service.core;



import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class FileService {

    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    public String getTempDir() {
        return TEMP_DIR;
    }

    public String getTempProjectPath(String projectName) {
        return TEMP_DIR + File.separator + projectName;
    }

    public void cleanupTempDirectory(String projectName) {
        File projectDir = new File(getTempProjectPath(projectName));
        if (projectDir.exists()) {
            deleteDirectory(projectDir);
        }
    }

    public void deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }
}
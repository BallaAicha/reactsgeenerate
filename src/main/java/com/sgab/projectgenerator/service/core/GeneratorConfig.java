package com.sgab.projectgenerator.service.core;



import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.generator")
public class GeneratorConfig {
    private String tempDir = System.getProperty("java.io.tmpdir");
    private String zipExtension = "-project.zip";
    private long processTimeoutMinutes = 5;

    // Getters and setters
    public String getTempDir() {
        return tempDir;
    }

    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    public String getZipExtension() {
        return zipExtension;
    }

    public void setZipExtension(String zipExtension) {
        this.zipExtension = zipExtension;
    }

    public long getProcessTimeoutMinutes() {
        return processTimeoutMinutes;
    }

    public void setProcessTimeoutMinutes(long processTimeoutMinutes) {
        this.processTimeoutMinutes = processTimeoutMinutes;
    }
}
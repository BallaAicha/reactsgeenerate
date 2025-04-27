package com.sgab.projectgenerator.service.core;



import com.sgab.projectgenerator.exception.ProjectGenerationException;
import com.sgab.projectgenerator.model.ProjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
public class ZipService {

    private static final String ZIP_EXTENSION = "-project.zip";

    @FunctionalInterface
    public interface ZipContentGenerator {
        void generateContent(ZipOutputStream zos, Set<String> addedFiles) throws IOException;
    }

    public ByteArrayResource createProjectZip(ProjectRequest request, ZipContentGenerator contentGenerator) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {

            Set<String> addedFiles = new LinkedHashSet<>();

            // Génération du contenu ZIP via le callback
            contentGenerator.generateContent(zos, addedFiles);

            zos.finish();
            log.debug("Project {} zipped successfully", request.getProjectName());
            return new ByteArrayResource(baos.toByteArray());
        }
    }

    public void createDirectory(ZipOutputStream zos, String path) throws IOException {
        ZipEntry entry = new ZipEntry(path + "/");
        zos.putNextEntry(entry);
        zos.closeEntry();
    }

    public void addToZip(ZipOutputStream zos, String path, String content, Set<String> addedFiles) throws IOException {
        if (addedFiles.contains(path)) {
            throw new IllegalStateException("Duplicate entry detected: " + path);
        }
        ZipEntry entry = new ZipEntry(path);
        zos.putNextEntry(entry);
        zos.write(content.getBytes(StandardCharsets.UTF_8));
        zos.closeEntry();
        addedFiles.add(path);
    }
}
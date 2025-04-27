//
//package com.sgab.projectgenerator.service;
//
//import com.sgab.projectgenerator.exception.ProjectGenerationException;
//import com.sgab.projectgenerator.model.ProjectRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.stereotype.Service;
//
//import java.io.*;
//import java.nio.charset.StandardCharsets;
//import java.util.LinkedHashSet;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class ProjectGeneratorService {
//
//    private final TemplateService templateService;
//    private static final String ZIP_EXTENSION = "-project.zip";
//    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
//
//    public ByteArrayResource generateProject(ProjectRequest request) {
//        String projectPath = TEMP_DIR + File.separator + request.getProjectName();
//        File projectDir = new File(projectPath);
//
//        try {
//            createBaseViteProject(request);
//
//            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                 ZipOutputStream zos = new ZipOutputStream(baos)) {
//
//                Set<String> addedFiles = new LinkedHashSet<>();
//                log.debug("Starting project generation for: {}", request.getProjectName());
//
//                generateProjectFiles(zos, request, addedFiles);
//
//                zos.finish();
//                log.debug("Project {} generated successfully", request.getProjectName());
//                return new ByteArrayResource(baos.toByteArray());
//            }
//        } catch (IOException | InterruptedException e) {
//            log.error("Error generating project {}", request.getProjectName(), e);
//            throw new ProjectGenerationException("Failed to generate project", e);
//        } finally {
//            if (projectDir.exists()) {
//                deleteDirectory(projectDir);
//            }
//        }
//    }
//
//    private void createBaseViteProject(ProjectRequest request) throws IOException, InterruptedException {
//        String projectPath = TEMP_DIR + File.separator + request.getProjectName();
//
//        ProcessBuilder pb = new ProcessBuilder(
//                "npm", "create", "vite@latest",
//                request.getProjectName(),
//                "--", "--template", request.getLanguage().equals("typescript") ? "react-ts" : "react"
//        );
//        pb.directory(new File(TEMP_DIR));
//
//        Process process = pb.start();
//        if (!process.waitFor(5, TimeUnit.MINUTES)) {
//            process.destroy();
//            throw new ProjectGenerationException("Timeout while creating Vite project");
//        }
//
//        if (process.exitValue() != 0) {
//            throw new ProjectGenerationException("Failed to create Vite project");
//        }
//
//        pb = new ProcessBuilder("npm", "install");
//        pb.directory(new File(projectPath));
//
//        process = pb.start();
//        if (!process.waitFor(5, TimeUnit.MINUTES)) {
//            process.destroy();
//            throw new ProjectGenerationException("Timeout while installing dependencies");
//        }
//
//        if (process.exitValue() != 0) {
//            throw new ProjectGenerationException("Failed to install dependencies");
//        }
//    }
//
//    private void deleteDirectory(File directory) {
//        File[] files = directory.listFiles();
//        if (files != null) {
//            for (File file : files) {
//                if (file.isDirectory()) {
//                    deleteDirectory(file);
//                } else {
//                    file.delete();
//                }
//            }
//        }
//        directory.delete();
//    }
//
//    private void generateProjectFiles(ZipOutputStream zos, ProjectRequest request, Set<String> addedFiles) throws IOException {
//        addToZip(zos, "package.json", templateService.getPackageJsonTemplate(request), addedFiles);
//        addToZip(zos, "vite.config." + getFileExtension(request), templateService.getViteConfigTemplate(request), addedFiles);
//        addToZip(zos, "index.html", templateService.getIndexHtmlTemplate(request), addedFiles);
//        addToZip(zos, "README.md", templateService.getReadmeTemplate(request), addedFiles);
//
//        createSourceDirectories(zos, request, addedFiles);
//    }
//
//
//private void createSourceDirectories(ZipOutputStream zos, ProjectRequest request, Set<String> addedFiles) throws IOException {
//    String[] baseDirs = {
//            "src/assets/images",
//            "src/assets/css",
//            "src/pages",
//            "src/components",  // Components déplacé au même niveau que pages
//            "src/config/environment",
//            "src/config/tenantConfig",
//            "src/routes"
//    };
//
//    for (String dir : baseDirs) {
//        createDirectory(zos, dir);
//    }
//
//    String ext = getFileExtension(request);
//
//    // Fichiers principaux
//    addToZip(zos, "src/main." + ext, templateService.getMainTemplate(request), addedFiles);
//    addToZip(zos, "src/App." + ext, templateService.getAppTemplate(request), addedFiles);
//    addToZip(zos, "src/index.css", templateService.getIndexCssTemplate(), addedFiles);
//
//    // Components (déplacés au bon emplacement)
//    addToZip(zos, "src/components/button." + ext, templateService.getButtonComponentTemplate(request), addedFiles);
//    addToZip(zos, "src/components/layout." + ext, templateService.getLayoutComponentTemplate(request), addedFiles);
//
//    // Fichiers spécifiques requis
//    addToZip(zos, "src/routes/navigation." + ext, templateService.getNavigationTemplate(request), addedFiles);
//    addToZip(zos, "src/routes/routes." + ext, templateService.getRoutesTemplate(request), addedFiles);
//    addToZip(zos, "src/config/environment/env.tsx", templateService.getEnvTemplate(request), addedFiles);
//    addToZip(zos, "src/utils.ts", templateService.getUtilsTemplate(request), addedFiles);
//
//    // Apis client
//    if (request.getFeatures().isApiClient()) {
//        createDirectory(zos, "src/apisClient");
//        addToZip(zos, "src/apisClient/index." + ext, templateService.getApiClientTemplate(request), addedFiles);
//    }
//
//    // I18n
//    if (request.getFeatures().isI18n()) {
//        createDirectory(zos, "src/config/languages");
//        addToZip(zos, "src/config/languages/en.json", templateService.getEnglishLanguageTemplate(), addedFiles);
//        addToZip(zos, "src/config/languages/fr.json", templateService.getFrenchLanguageTemplate(), addedFiles);
//    }
//
//    // Config multi-tenants
//    if (request.getFeatures().isTenantConfig()) {
//        addToZip(zos, "src/config/tenantConfig/tenantConfig." + ext, templateService.getTenantConfigTemplate(request), addedFiles);
//    }
//
//    // Feature login (corrigé pour être au même niveau que core)
//    createDirectory(zos, "src/packages/core");
//    createDirectory(zos, "src/packages/feat-login");  // Déplacé au même niveau que core
//    createDirectory(zos, "src/packages/feat-login/components");
//    createDirectory(zos, "src/packages/feat-login/core");
//    createDirectory(zos, "src/packages/feat-login/web");
//}
//
//    private void createDirectory(ZipOutputStream zos, String path) throws IOException {
//        ZipEntry entry = new ZipEntry(path + "/");
//        zos.putNextEntry(entry);
//        zos.closeEntry();
//    }
//
//    private void addToZip(ZipOutputStream zos, String path, String content, Set<String> addedFiles) throws IOException {
//        if (addedFiles.contains(path)) {
//            throw new IllegalStateException("Duplicate entry detected: " + path);
//        }
//        ZipEntry entry = new ZipEntry(path);
//        zos.putNextEntry(entry);
//        zos.write(content.getBytes(StandardCharsets.UTF_8));
//        zos.closeEntry();
//        addedFiles.add(path);
//    }
//
//    private String getFileExtension(ProjectRequest request) {
//        return request.getLanguage().equals("typescript") ? "tsx" : "jsx";
//    }
//}


package com.sgab.projectgenerator.service;

import com.sgab.projectgenerator.exception.ProjectGenerationException;
import com.sgab.projectgenerator.model.ProjectRequest;
import com.sgab.projectgenerator.service.core.FileService;
import com.sgab.projectgenerator.service.core.ProjectStructureService;
import com.sgab.projectgenerator.service.core.ViteProjectInitializer;
import com.sgab.projectgenerator.service.core.ZipService;
import com.sgab.projectgenerator.usecases.GeneratorReact;
import com.sgab.projectgenerator.usecases.RequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectGeneratorService implements GeneratorReact {

    private final TemplateService templateService;
    private final ViteProjectInitializer viteInitializer;
    private final FileService fileService;
    private final ZipService zipService;
    private final ProjectStructureService projectStructureService;

    @Override
    public ResponseEntity<ByteArrayResource> handle(ProjectRequest request, RequestContext context) {
        String projectPath = fileService.getTempProjectPath(request.getProjectName());

        try {
            log.debug("Starting project generation for: {}", request.getProjectName());

            // Initialisation du projet Vite
            viteInitializer.createBaseViteProject(request);

            // Génération des fichiers du projet
            ByteArrayResource resource = zipService.createProjectZip(request, (zos, addedFiles) ->
                    projectStructureService.generateProjectFiles(zos, request, addedFiles, templateService));

            // Configuration de la réponse pour le téléchargement
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + request.getProjectName() + "-project.zip\"")
                    .body(resource);

        } catch (Exception e) {
            log.error("Error generating project {}", request.getProjectName(), e);
            throw new ProjectGenerationException("Failed to generate project", e);
        } finally {
            fileService.cleanupTempDirectory(request.getProjectName());
        }
    }
}
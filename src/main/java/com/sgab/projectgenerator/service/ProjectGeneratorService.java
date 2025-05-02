


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
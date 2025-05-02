package com.sgab.projectgenerator.service.core;



import com.sgab.projectgenerator.exception.ProjectGenerationException;
import com.sgab.projectgenerator.model.ProjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class ViteProjectInitializer {

    private final FileService fileService;
    private static final long PROCESS_TIMEOUT_MINUTES = 5;



    public void createBaseViteProject(ProjectRequest request) throws IOException, InterruptedException {
        String projectPath = fileService.getTempProjectPath(request.getProjectName());
        String tempDir = fileService.getTempDir();
        String npmPath = fileService.getNpmPath(); // Obtenir le chemin vers npm

        // Création du projet Vite
        executeProcess(
                new ProcessBuilder(
                        npmPath, "create", "vite@latest", // Utiliser le chemin complet vers npm
                        request.getProjectName(),
                        "--", "--template", request.getLanguage().equals("typescript") ? "react-ts" : "react"
                ).directory(new File(tempDir)),
                "creating Vite project"
        );

        // Installation des dépendances
        executeProcess(
                new ProcessBuilder(npmPath, "install") // Utiliser le chemin complet vers npm
                        .directory(new File(projectPath)),
                "installing dependencies"
        );
    }

    private void executeProcess(ProcessBuilder pb, String operation) throws IOException, InterruptedException {
        Process process = pb.start();

        if (!process.waitFor(PROCESS_TIMEOUT_MINUTES, TimeUnit.MINUTES)) {
            process.destroy();
            throw new ProjectGenerationException("Timeout while " + operation);
        }

        if (process.exitValue() != 0) {
            throw new ProjectGenerationException("Failed when " + operation);
        }
    }
}
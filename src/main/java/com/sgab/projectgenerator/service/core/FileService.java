


package com.sgab.projectgenerator.service.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class FileService {

    // Utiliser le répertoire utilisateur au lieu du répertoire temporaire
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String PROJECTS_DIR = "project-generator";
    // Chemin explicite vers npm basé sur votre sortie "where npm"
    private static final String NPM_PATH = USER_HOME + "\\bin\\node-v23.10.0-win-x64\\npm.cmd";

    public String getTempDir() {
        // Créer le répertoire s'il n'existe pas
        File directory = new File(USER_HOME + File.separator + PROJECTS_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return USER_HOME + File.separator + PROJECTS_DIR;
    }

    public String getTempProjectPath(String projectName) {
        return getTempDir() + File.separator + projectName;
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

    /**
     * Obtenir le chemin complet vers l'exécutable npm
     */
    public String getNpmPath() {
        // Vérifier si le fichier existe
        File npmFile = new File(NPM_PATH);
        if (npmFile.exists()) {
            return NPM_PATH;
        }

        // Si le chemin codé en dur ne fonctionne pas, essayer d'utiliser d'autres chemins connus
        String[] possiblePaths = {
                USER_HOME + "\\bin\\node-v23.10.0-win-x64\\npm.cmd",
                USER_HOME + "\\.local\\bin\\npm.bat",
                USER_HOME + "\\AppData\\Roaming\\npm\\npm.cmd"
        };

        for (String path : possiblePaths) {
            File file = new File(path);
            if (file.exists()) {
                return path;
            }
        }

        // Si tout échoue, retourner juste "npm" et espérer qu'il soit dans le PATH
        return "npm";
    }
}
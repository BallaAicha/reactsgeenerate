package com.sgab.projectgenerator.service.core;



import com.sgab.projectgenerator.model.ProjectRequest;
import com.sgab.projectgenerator.service.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectStructureService {

    private final ZipService zipService;

    public void generateProjectFiles(ZipOutputStream zos, ProjectRequest request,
                                     Set<String> addedFiles, TemplateService templateService) throws IOException {
        // Générer les fichiers principaux
        zipService.addToZip(zos, "package.json", templateService.getPackageJsonTemplate(request), addedFiles);
        zipService.addToZip(zos, "vite.config." + getFileExtension(request), templateService.getViteConfigTemplate(request), addedFiles);
        zipService.addToZip(zos, "index.html", templateService.getIndexHtmlTemplate(request), addedFiles);
        zipService.addToZip(zos, "README.md", templateService.getReadmeTemplate(request), addedFiles);

        // Créer la structure de répertoires et les fichiers spécifiques
        createSourceDirectories(zos, request, addedFiles, templateService);
    }

    private void createSourceDirectories(ZipOutputStream zos, ProjectRequest request,
                                         Set<String> addedFiles, TemplateService templateService) throws IOException {
        // Définir la structure de base
        String[] baseDirs = {
                "src/assets/images",
                "src/assets/css",
                "src/pages",
                "src/components",
                "src/config/environment",
                "src/config/tenantConfig",
                "src/routes"
        };

        // Création des répertoires
        for (String dir : baseDirs) {
            zipService.createDirectory(zos, dir);
        }

        String ext = getFileExtension(request);

        // Générer les fichiers par catégorie
        generateCoreFiles(zos, request, addedFiles, templateService, ext);
        generateRouteFiles(zos, request, addedFiles, templateService, ext);
        generateComponentFiles(zos, request, addedFiles, templateService, ext);
        generateConfigFiles(zos, request, addedFiles, templateService, ext);

        // Générer les fichiers spécifiques aux fonctionnalités optionnelles
        if (request.getFeatures().isApiClient()) {
            generateApiClientFiles(zos, request, addedFiles, templateService, ext);
        }

        if (request.getFeatures().isI18n()) {
            generateI18nFiles(zos, request, addedFiles, templateService);
        }

        if (request.getFeatures().isTenantConfig()) {
            generateTenantConfigFiles(zos, request, addedFiles, templateService, ext);
        }
    }

    private void generateCoreFiles(ZipOutputStream zos, ProjectRequest request,
                                   Set<String> addedFiles, TemplateService templateService, String ext) throws IOException {
        zipService.addToZip(zos, "src/main." + ext, templateService.getMainTemplate(request), addedFiles);
        zipService.addToZip(zos, "src/App." + ext, templateService.getAppTemplate(request), addedFiles);
        zipService.addToZip(zos, "src/index.css", templateService.getIndexCssTemplate(), addedFiles);
        zipService.addToZip(zos, "src/utils.ts", templateService.getUtilsTemplate(request), addedFiles);
    }

    private void generateComponentFiles(ZipOutputStream zos, ProjectRequest request,
                                        Set<String> addedFiles, TemplateService templateService, String ext) throws IOException {
        zipService.addToZip(zos, "src/components/button." + ext, templateService.getButtonComponentTemplate(request), addedFiles);
        zipService.addToZip(zos, "src/components/layout." + ext, templateService.getLayoutComponentTemplate(request), addedFiles);
    }

    private void generateRouteFiles(ZipOutputStream zos, ProjectRequest request,
                                    Set<String> addedFiles, TemplateService templateService, String ext) throws IOException {
        zipService.addToZip(zos, "src/routes/navigation." + ext, templateService.getNavigationTemplate(request), addedFiles);
        zipService.addToZip(zos, "src/routes/routes." + ext, templateService.getRoutesTemplate(request), addedFiles);
    }

    private void generateConfigFiles(ZipOutputStream zos, ProjectRequest request,
                                     Set<String> addedFiles, TemplateService templateService, String ext) throws IOException {
        zipService.addToZip(zos, "src/config/environment/env.tsx", templateService.getEnvTemplate(request), addedFiles);

        // Création des répertoires pour les fonctionnalités
        zipService.createDirectory(zos, "src/packages/core");
        zipService.createDirectory(zos, "src/packages/feat-login");
        zipService.createDirectory(zos, "src/packages/feat-login/components");
        zipService.createDirectory(zos, "src/packages/feat-login/core");
        zipService.createDirectory(zos, "src/packages/feat-login/web");
    }

    private void generateApiClientFiles(ZipOutputStream zos, ProjectRequest request,
                                        Set<String> addedFiles, TemplateService templateService, String ext) throws IOException {
        zipService.createDirectory(zos, "src/apisClient");
        zipService.addToZip(zos, "src/apisClient/index." + ext, templateService.getApiClientTemplate(request), addedFiles);
    }

    private void generateI18nFiles(ZipOutputStream zos, ProjectRequest request,
                                   Set<String> addedFiles, TemplateService templateService) throws IOException {
        zipService.createDirectory(zos, "src/config/languages");
        zipService.addToZip(zos, "src/config/languages/en.json", templateService.getEnglishLanguageTemplate(), addedFiles);
        zipService.addToZip(zos, "src/config/languages/fr.json", templateService.getFrenchLanguageTemplate(), addedFiles);
    }

    private void generateTenantConfigFiles(ZipOutputStream zos, ProjectRequest request,
                                           Set<String> addedFiles, TemplateService templateService, String ext) throws IOException {
        zipService.addToZip(zos, "src/config/tenantConfig/tenantConfig." + ext, templateService.getTenantConfigTemplate(request), addedFiles);
    }

    public String getFileExtension(ProjectRequest request) {
        return request.getLanguage().equals("typescript") ? "tsx" : "jsx";
    }
}
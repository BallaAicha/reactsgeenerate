package com.sgab.projectgenerator.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ProjectRequest {
    @NotBlank(message = "Project name is required")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Project name must contain only lowercase letters, numbers, and hyphens")
    private String projectName;
    
    private String description;
    
    @NotBlank(message = "Version is required")
    @Pattern(regexp = "^\\d+\\.\\d+\\.\\d+$", message = "Version must be in format x.y.z")
    private String version;
    
    @NotBlank(message = "Language is required")
    @Pattern(regexp = "^(javascript|typescript)$", message = "Language must be either 'javascript' or 'typescript'")
    private String language;
    
    private Features features = new Features();
    
    @Data
    public static class Features {
        private boolean routing = true;
        private boolean i18n = true;//:signifie t-il que le projet est internationalisé
        private boolean apiClient = true;
        private boolean tenantConfig = false;
    }
}
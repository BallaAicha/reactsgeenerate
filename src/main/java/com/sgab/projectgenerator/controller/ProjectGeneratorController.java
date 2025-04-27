//package com.sgab.projectgenerator.controller;
//
//import com.sgab.projectgenerator.model.ProjectRequest;
//import com.sgab.projectgenerator.service.ProjectGeneratorService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/projects")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
//public class ProjectGeneratorController {
//    private final ProjectGeneratorService projectGeneratorService;
//
//    @PostMapping("/generate")
//    public ResponseEntity<ByteArrayResource> generateProject(@Valid @RequestBody ProjectRequest request) {
//        ByteArrayResource resource = projectGeneratorService.generateProject(request);
//
//        return ResponseEntity.ok()
//            .contentType(MediaType.APPLICATION_OCTET_STREAM)
//            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + request.getProjectName() + "-project.zip\"")
//            .body(resource);
//    }
//}



package com.sgab.projectgenerator.controller;

import com.sgab.projectgenerator.model.ProjectRequest;
import com.sgab.projectgenerator.usecases.GeneratorReact;
import com.sgab.projectgenerator.usecases.RequestContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProjectGeneratorController implements GeneratorReact {

    private final GeneratorReact generatorReact;

    @Override
    @PostMapping("/generate")
    public ResponseEntity<ByteArrayResource> handle(@Valid @RequestBody ProjectRequest request, RequestContext context) {
        return generatorReact.handle(request, context);
    }
}
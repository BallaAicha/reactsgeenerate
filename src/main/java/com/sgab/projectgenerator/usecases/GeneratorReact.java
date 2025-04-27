package com.sgab.projectgenerator.usecases;

import com.sgab.projectgenerator.model.ProjectRequest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

public interface GeneratorReact extends Command{
  public ResponseEntity <ByteArrayResource> handle(ProjectRequest request, RequestContext context);
}

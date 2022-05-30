package com.zzq.swagger.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * http://localhost:8080/swagger/swagger-ui/index.html
 */
@RestController
@Tag(name = "SwaggerController")
public class SwaggerController {


    @GetMapping
    @Operation(operationId = "get", description = "GET DATA")
    public String get() {
        return "Hello GET";
    }

    @DeleteMapping
    @Operation(operationId = "delete", description = "DELETE DATA")
    public String delete() {
        return "Hello Delete";
    }

    @PostMapping
    @Operation(operationId = "post", description = "POST DATA")
    public String post() {
        return "Hello POST";
    }

    @PutMapping
    @Operation(operationId = "put", description = "PUT DATA")
    public String put() {
        return "Hello PUT";
    }
}

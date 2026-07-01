package com.example.demo.controller;

import com.example.demo.dto.CodeRequest;
import com.example.demo.dto.CodeResponse;
import com.example.demo.service.PythonExecutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/execute")
public class PythonExecutionController {

    private final PythonExecutionService pythonExecutionService;

    // Constructor injection for the service
    public PythonExecutionController(PythonExecutionService pythonExecutionService) {
        this.pythonExecutionService = pythonExecutionService;
    }

    @PostMapping("/python")
    public ResponseEntity<CodeResponse> executePython(@RequestBody CodeRequest request) {
        if (request.getCode() == null || request.getCode().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new CodeResponse("Code cannot be empty", false));
        }

        try {
            // Execute code via the Fabric8 Kubernetes client service
            String terminalOutput = pythonExecutionService.runPythonCode(request.getCode());

            // Check if the service caught an execution error
            if (terminalOutput.startsWith("Error executing code:")) {
                return ResponseEntity.internalServerError().body(new CodeResponse(terminalOutput, false));
            }

            return ResponseEntity.ok(new CodeResponse(terminalOutput, true));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CodeResponse(e.getMessage(), false));
        }
    }
}
package com.example.demo.service;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PythonExecutionService {

    public String runPythonCode(String code) {
        // Initialize the client (auto-detects config or service account)
        try (KubernetesClient client = new KubernetesClientBuilder().build()) {

            // 1. Define the Pod
            Pod podDefinition = new PodBuilder()
                    .withNewMetadata()
                    .withGenerateName("python-runner-") // Generates a unique name like python-runner-abcde
                    .endMetadata()
                    .withNewSpec()
                    .addNewContainer()
                    .withName("python-env")
                    .withImage("python:3.11-slim") // Lightweight python image
                    .withCommand("python", "-c", code) // Pass the string directly
                    .endContainer()
                    .withRestartPolicy("Never") // Ensure it runs once and stops
                    .endSpec()
                    .build();

            // 2. Create the Pod in the "default" namespace
            Pod createdPod = client.pods().inNamespace("default").resource(podDefinition).create();
            String podName = createdPod.getMetadata().getName();

            try {
                // 3. Wait for the pod to finish executing (Succeeded or Failed)
                client.pods().inNamespace("default").withName(podName)
                        .waitUntilCondition(p -> {
                            String phase = p.getStatus().getPhase();
                            return "Succeeded".equals(phase) || "Failed".equals(phase);
                        }, 2, TimeUnit.MINUTES);

                // 4. Fetch the terminal output (logs)
                return client.pods().inNamespace("default").withName(podName).getLog();

            } finally {
                // 5. Clean up: Delete the pod so you don't leak resources
                client.pods().inNamespace("default").withName(podName).delete();
            }

        } catch (Exception e) {
            return "Error executing code: " + e.getMessage();
        }
    }
}
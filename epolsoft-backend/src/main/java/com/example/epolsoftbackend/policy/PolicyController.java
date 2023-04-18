package com.example.epolsoftbackend.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/policy")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<PolicyDTO> savePolicySettings(@RequestBody PolicyDTO policyDTO){
        return new ResponseEntity<>(policyService.setSettingsPolicy(policyDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<PolicyDTO> getPolicySettings() {
        return new ResponseEntity<>(policyService.getAllPolicyAttributes(), HttpStatus.OK);
    }
}

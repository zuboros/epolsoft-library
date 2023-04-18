package com.example.epolsoftbackend.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/policy")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    @PostMapping()
    public ResponseEntity<PolicyDTO> savePolicySettings(@RequestBody PolicyDTO policyDTO){
        return new ResponseEntity<>(policyService.setSettingsPolicy(policyDTO), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PolicyDTO> getPolicySettings() {
        return new ResponseEntity<>(policyService.getAllPolicyAttributes(), HttpStatus.OK);
    }
}

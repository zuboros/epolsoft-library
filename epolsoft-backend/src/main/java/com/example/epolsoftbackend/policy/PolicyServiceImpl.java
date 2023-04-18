package com.example.epolsoftbackend.policy;

import com.example.epolsoftbackend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService{

        private final PolicyRepository policyRepository;
        private final PolicyMapper policyMapper;

        public PolicyDTO getAllPolicyAttributes() {
               Policy policy = policyRepository.findById(1L).orElseThrow(
                       () -> new ResourceNotFoundException("Topic", "id", 1));
               return policyMapper.policyToPolicyDTO(policy);
        }

        public PolicyDTO setSettingsPolicy(PolicyDTO policyDTO) {
                Policy updatedPolicy = policyMapper.policyDTOToPolicy(policyDTO);
                updatedPolicy.setId(1L);
                return policyMapper.policyToPolicyDTO(policyRepository.saveAndFlush(updatedPolicy));
        }

}

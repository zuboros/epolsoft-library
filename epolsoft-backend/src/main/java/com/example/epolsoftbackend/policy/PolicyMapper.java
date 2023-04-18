package com.example.epolsoftbackend.policy;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PolicyMapper {
    PolicyMapper INSTANCE = Mappers.getMapper(PolicyMapper.class);

    default Policy policyDTOToPolicy (PolicyDTO policyDTO) {
        if (policyDTO == null) {
            return null;
        }

        Policy.PolicyBuilder policy = Policy.builder();

        policy.minCharNumber(policyDTO.getMinCharNumber());
        policy.maxCharNumber(policyDTO.getMaxCharNumber());
        policy.minDigitNumber(policyDTO.getMinDigitNumber());
        policy.minLowercaseLetterNumber(policyDTO.getMinLowercaseLetterNumber());
        policy.minUppercaseLetterNumber(policyDTO.getMinUppercaseLetterNumber());
        policy.minSpecSymbolNumber(policyDTO.getMinSpecSymbolNumber());
        policy.outdatePasswordNotificationTime(policyDTO.getOutdatePasswordNotificationTime() + " day");
        policy.passwordExpirationTime(policyDTO.getPasswordExpirationTime() + " day");

        return policy.build();
    }

    default PolicyDTO policyToPolicyDTO (Policy policy) {
        if (policy == null) {
            return null;
        }

        PolicyDTO.PolicyDTOBuilder policyDTO = PolicyDTO.builder();

        policyDTO.minCharNumber(policy.getMinCharNumber());
        policyDTO.maxCharNumber(policy.getMaxCharNumber());
        policyDTO.minDigitNumber(policy.getMinDigitNumber());
        policyDTO.minLowercaseLetterNumber(policy.getMinLowercaseLetterNumber());
        policyDTO.minUppercaseLetterNumber(policy.getMinUppercaseLetterNumber());
        policyDTO.minSpecSymbolNumber(policy.getMinSpecSymbolNumber());
        policyDTO.outdatePasswordNotificationTime(Integer.parseInt(policy.getOutdatePasswordNotificationTime().split(" ")[0]));
        policyDTO.passwordExpirationTime(Integer.parseInt(policy.getPasswordExpirationTime().split(" ")[0]));

        return policyDTO.build();
    }

}

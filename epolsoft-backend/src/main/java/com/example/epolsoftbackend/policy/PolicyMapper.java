package com.example.epolsoftbackend.policy;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PolicyMapper {
    PolicyMapper INSTANCE = Mappers.getMapper(PolicyMapper.class);

    Policy policyDTOToPolicy ( PolicyDTO policyDTO);

    PolicyDTO policyToPolicyDTO ( Policy policy);

}

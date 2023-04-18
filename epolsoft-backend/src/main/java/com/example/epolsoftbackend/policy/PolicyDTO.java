package com.example.epolsoftbackend.policy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyDTO {

    private int minCharNumber;

    private int maxCharNumber;

    private int minUppercaseLetterNumber;

    private int minLowercaseLetterNumber;

    private int minDigitNumber;

    private int minSpecSymbolNumber;

    private String passwordExpirationTime;

    private String outdatePasswordNotificationTime;

}

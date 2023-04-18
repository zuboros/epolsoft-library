package com.example.epolsoftbackend.policy;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "policy")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "min_char_number")
    private int minCharNumber;

    @Column(name = "max_char_number")
    private int maxCharNumber;

    @Column(name = "min_uppercase_letter_number")
    private int minUppercaseLetterNumber;

    @Column(name = "min_lowercase_letter_number")
    private int minLowercaseLetterNumber;

    @Column(name = "min_digit_number")
    private int minDigitNumber;

    @Column(name = "min_spec_symbol_number")
    private int minSpecSymbolNumber;

    @Column(name = "password_expiration_time")
    private String passwordExpirationTime;

    @Column(name = "outdate_password_notification_time")
    private String outdatePasswordNotificationTime;
}

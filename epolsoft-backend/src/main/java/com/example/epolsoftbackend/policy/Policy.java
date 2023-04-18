package com.example.epolsoftbackend.policy;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import io.hypersistence.utils.hibernate.type.interval.PostgreSQLIntervalType;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@TypeDef(typeClass = PostgreSQLIntervalType.class, defaultForType = Duration.class)
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

    @Column(name = "password_expiration_time", columnDefinition = "interval")
    private Duration passwordExpirationTime;

    @Column(name = "outdate_password_notification_time", columnDefinition = "interval")
    private Duration outdatePasswordNotificationTime;

}

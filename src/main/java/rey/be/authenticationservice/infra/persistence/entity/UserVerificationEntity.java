package rey.be.authenticationservice.infra.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_verification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVerificationEntity {

    @Id
    @Column(name = "user_info", nullable = false, unique = true)
    private String userInfo;

    @Column(name = "user_info_type", nullable = false)
    private String userInfoType;

    @Column(name = "verification_code", nullable = false)
    private String verificationCode;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}

package io.github.karMiguel.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "reset_password")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ResetPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private User user;

    @Column(length = 25)
    @Enumerated(EnumType.STRING)
    private StatusResetPassword status;

    @Column(length = 25)
    private String code;

    @Column(name = "new_password")
    private String newPassword;

    @CreatedDate
    @Column(name = "date_created",updatable = false)
    private LocalDateTime dateCreated;


}
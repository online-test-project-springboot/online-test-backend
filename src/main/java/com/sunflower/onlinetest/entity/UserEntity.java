package com.sunflower.onlinetest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity implements iEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    @Column(name = "full_name")
    private String fullName;

    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @NotNull
    private String password;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDate createdDate;

    @CreationTimestamp
    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;
}

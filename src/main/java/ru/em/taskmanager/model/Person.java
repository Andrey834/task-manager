package ru.em.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "person")
public class Person implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String email;
    private String password;
    private boolean enabled;
    @Transient
    private Set<PersonRole> authorities;

    @Override
    public String getUsername() {
        return email;
    }
}

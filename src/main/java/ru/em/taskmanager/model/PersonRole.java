package ru.em.taskmanager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import ru.em.taskmanager.enums.ERole;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person_role")
public class PersonRole implements GrantedAuthority, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private Long personId;
    private ERole role;

    @Override
    public String getAuthority() {
        return role.getValue();
    }
}

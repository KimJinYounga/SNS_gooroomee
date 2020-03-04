package com.gooroomee.api.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="userId")
@Entity
@Table(name="user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"}, name = "email_unique")})
public class User {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="userId")
    private long userId;

    @Column(name="email", unique = true)
    private String email;

    @Column(name="name")
    private String name;

    @Column(name="password")
    private String password;
}

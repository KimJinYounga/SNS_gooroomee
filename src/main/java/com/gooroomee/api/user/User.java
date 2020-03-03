package com.gooroomee.api.user;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of="user_id")
@Entity
public class User {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long user_id;
    private String email;
    private String name;
    private String password;
}

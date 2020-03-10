package com.gooroomee.api.member;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
//@Setter
@EqualsAndHashCode(of="memberId")
@Entity
@Table(name="member",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"email"}, name = "email_unique")})
public class Member{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="memberId")
    private Long memberId;

    @Column(name="email", unique = true)
    @Setter
    private String email;

    @Column(name="name")
    @Setter
    private String name;

    @Column(name="password")
    @Setter
    private String password;
}

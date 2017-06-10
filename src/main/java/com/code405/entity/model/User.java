package com.code405.entity.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by birth on 07.02.2017.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString()
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "msisdn", nullable = false, unique = true)
    private String msisdn;

    @Column(name = "birthday", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "sex", nullable = false)
    private boolean sex;

    @Column(name = "password", nullable = false)
    protected String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

//    @OneToOne(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
//    @LazyToOne(value = LazyToOneOption.PROXY)
//    private VerificationToken verificationToken;
//
//    @OneToOne(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
//    @LazyToOne(value = LazyToOneOption.PROXY)
//    private PasswordResetToken passwordResetToken;

}

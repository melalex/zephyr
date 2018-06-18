package com.zephyr.auth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@Table(indexes = @Index(name = "name_idx", columnList = "name", unique = true))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(unique = true, nullable = false, length = 40)
    private String username;

    @Column(unique = true, nullable = false, length = 40)
    private String password;

    public Example<User> asExample() {
        return Example.of(this);
    }
}

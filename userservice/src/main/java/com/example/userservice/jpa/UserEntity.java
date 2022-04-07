package com.example.userservice.jpa;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id  // 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id를 따로 지정 안하면 DB가 알아서 Auto_Increment 해줌.
    private Long id;



}

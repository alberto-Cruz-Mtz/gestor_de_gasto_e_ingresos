package com.example.backend.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class UserEntity {

  public UserEntity(String email, String password, String username) {
    this.email = email;
    this.password = password;
    this.username = username;
  }

  public UserEntity(String email, String password, String username, Set<FundEntity> funds) {
    this.email = email;
    this.password = password;
    this.username = username;
    this.funds = funds;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false, unique = true, length = 120)
  private String email;

  @Column(nullable = false, length = 120, unique = true)
  private String username;

  @Column(nullable = false, length = 120)
  private String password;

  @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
      CascadeType.REMOVE }, mappedBy = "user", orphanRemoval = true)
  private Set<FundEntity> funds;
}

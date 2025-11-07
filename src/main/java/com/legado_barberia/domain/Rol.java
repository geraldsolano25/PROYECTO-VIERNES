package com.legado_barberia.domain;
import jakarta.persistence.*;

@Entity
public class Rol {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Column(unique = true, nullable = false, length=40) private String nombre;
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getNombre(){return nombre;} public void setNombre(String nombre){this.nombre=nombre;}
}

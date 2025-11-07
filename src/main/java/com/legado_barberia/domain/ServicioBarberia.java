package com.legado_barberia.domain;
import jakarta.persistence.*; import jakarta.validation.constraints.*;

@Entity
public class ServicioBarberia {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @NotBlank @Column(nullable=false,length=120) private String nombre;
  @Column(length=300) private String descripcion;
  @Positive @Column(nullable=false) private Integer duracionMin;
  @Positive @Column(nullable=false) private Integer precio;
  // getters/setters
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getNombre(){return nombre;} public void setNombre(String n){this.nombre=n;}
  public String getDescripcion(){return descripcion;} public void setDescripcion(String d){this.descripcion=d;}
  public Integer getDuracionMin(){return duracionMin;} public void setDuracionMin(Integer m){this.duracionMin=m;}
  public Integer getPrecio(){return precio;} public void setPrecio(Integer p){this.precio=p;}
}

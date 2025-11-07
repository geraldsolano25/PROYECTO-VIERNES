package com.legado_barberia.domain;
import jakarta.persistence.*; import jakarta.validation.constraints.*; import java.time.LocalDateTime; import java.util.Set;

@Entity
public class Usuario {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @Email @NotBlank @Column(unique=true,nullable=false,length=120) private String email;
  @NotBlank @Column(nullable=false) private String passwordHash;
  @NotBlank @Column(nullable=false,length=120) private String nombre;
  @Column(length=25) private String telefono;
  @Column(nullable=false) private boolean activo=true;
  @Column(nullable=false) private LocalDateTime creadoEn=LocalDateTime.now();
  @ManyToMany(fetch=FetchType.EAGER)
  @JoinTable(name="usuario_roles", joinColumns=@JoinColumn(name="usuario_id"), inverseJoinColumns=@JoinColumn(name="rol_id"))
  private Set<Rol> roles;
  // getters/setters
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getEmail(){return email;} public void setEmail(String e){this.email=e;}
  public String getPasswordHash(){return passwordHash;} public void setPasswordHash(String p){this.passwordHash=p;}
  public String getNombre(){return nombre;} public void setNombre(String n){this.nombre=n;}
  public String getTelefono(){return telefono;} public void setTelefono(String t){this.telefono=t;}
  public boolean isActivo(){return activo;} public void setActivo(boolean a){this.activo=a;}
  public LocalDateTime getCreadoEn(){return creadoEn;} public void setCreadoEn(LocalDateTime c){this.creadoEn=c;}
  public Set<Rol> getRoles(){return roles;} public void setRoles(Set<Rol> roles){this.roles=roles;}
}

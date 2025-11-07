package com.legado_barberia.domain;
import jakarta.persistence.*; import jakarta.validation.constraints.NotNull; import java.time.LocalDateTime;

@Entity
public class Cita {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @ManyToOne(optional=false) private Usuario cliente;
  @ManyToOne(optional=false) private ServicioBarberia servicio;
  @NotNull @Column(nullable=false) private LocalDateTime inicio;
  @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private Estado estado = Estado.ACTIVA;
  public enum Estado { ACTIVA, CANCELADA }
  // getters/setters
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public Usuario getCliente(){return cliente;} public void setCliente(Usuario c){this.cliente=c;}
  public ServicioBarberia getServicio(){return servicio;} public void setServicio(ServicioBarberia s){this.servicio=s;}
  public LocalDateTime getInicio(){return inicio;} public void setInicio(LocalDateTime i){this.inicio=i;}
  public Estado getEstado(){return estado;} public void setEstado(Estado e){this.estado=e;}
}

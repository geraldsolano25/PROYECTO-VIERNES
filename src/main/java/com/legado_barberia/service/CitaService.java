package com.legado_barberia.service;
import com.legado_barberia.domain.*; import com.legado_barberia.repository.*;
import org.springframework.stereotype.Service; import java.time.LocalDateTime; import java.util.List;

@Service
public class CitaService {
  private final CitaRepository citas; private final UsuarioRepository usuarios; private final ServicioBarberiaRepository servicios;
  public CitaService(CitaRepository c, UsuarioRepository u, ServicioBarberiaRepository s){citas=c; usuarios=u; servicios=s;}
  public Cita reservar(Long uid, Long servicioId, LocalDateTime inicio){
    Usuario cli=usuarios.findById(uid).orElseThrow(); ServicioBarberia serv=servicios.findById(servicioId).orElseThrow();
    LocalDateTime fin=inicio.plusMinutes(serv.getDuracionMin());
    if(citas.existsByInicioBetween(inicio.minusMinutes(1), fin.minusMinutes(1))) throw new IllegalStateException("Horario no disponible");
    Cita c=new Cita(); c.setCliente(cli); c.setServicio(serv); c.setInicio(inicio); return citas.save(c);
  }
  public void cancelar(Long uid, Long citaId){
    Cita c=citas.findById(citaId).orElseThrow();
    if(!c.getCliente().getId().equals(uid)) throw new SecurityException("No autorizado");
    c.setEstado(Cita.Estado.CANCELADA); citas.save(c);
  }
  public List<Cita> misCitas(Long uid){ Usuario u=usuarios.findById(uid).orElseThrow(); return citas.findByClienteOrderByInicioDesc(u); }
}

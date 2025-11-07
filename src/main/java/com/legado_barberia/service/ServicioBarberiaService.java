package com.legado_barberia.service;
import com.legado_barberia.domain.ServicioBarberia; import com.legado_barberia.repository.ServicioBarberiaRepository;
import org.springframework.stereotype.Service; import java.util.List;
@Service public class ServicioBarberiaService {
  private final ServicioBarberiaRepository repo; public ServicioBarberiaService(ServicioBarberiaRepository r){repo=r;}
  public List<ServicioBarberia> listar(){ return repo.findAll(); }
}

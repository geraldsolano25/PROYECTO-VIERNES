package com.legado_barberia.repository;
import com.legado_barberia.domain.*; import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime; import java.util.List;
public interface CitaRepository extends JpaRepository<Cita,Long>{
  List<Cita> findByClienteOrderByInicioDesc(Usuario cliente);
  boolean existsByInicioBetween(LocalDateTime start, LocalDateTime end);
}

package com.tienda_vm.repository;

import com.tienda_vm.domain.Ruta;
import com.tienda_vm.domain.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RutaRepository extends JpaRepository<Ruta, Integer> {
    List<Ruta> findAllByOrderByRequiereRolAsc();
}
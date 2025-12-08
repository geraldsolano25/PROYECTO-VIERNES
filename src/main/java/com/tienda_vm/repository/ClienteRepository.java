
package com.tienda_vm.repository;
import com.tienda_vm.domain.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
    // Método para buscar clientes activos
    @Query("SELECT c FROM Cliente c WHERE c.activo = true ORDER BY c.nombre")
    List<Cliente> findActivos();
    
    // Método para buscar por nombre (ignorando mayúsculas/minúsculas)
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
}

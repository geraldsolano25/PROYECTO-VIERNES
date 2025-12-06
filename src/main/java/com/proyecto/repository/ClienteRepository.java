/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.repository;
import com.proyecto.domain.Cliente;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    // Tu entidad Cliente no tiene campo 'activo', así que no podemos usar findByActivoTrue()
    // En lugar de eso, usaremos métodos básicos
    
    // Buscar por teléfono
    Optional<Cliente> findByTelefono(String telefono);
    
    // Buscar por email
    Optional<Cliente> findByEmail(String email);
    
    // Buscar por nombre (parcial, case insensitive)
    List<Cliente> findByNombreCompletoContainingIgnoreCase(String nombre);
    
    // Buscar todos (ya viene con JpaRepository)
    // findAll() ya está disponible
}
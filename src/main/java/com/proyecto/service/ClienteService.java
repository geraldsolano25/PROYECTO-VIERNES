/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.service;

import com.proyecto.domain.Cliente;
import com.proyecto.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Cliente getCliente(Long idCliente) {
        return clienteRepository.findById(idCliente).orElseThrow(
            () -> new NoSuchElementException("Cliente con ID " + idCliente + " no encontrado."));
    }
    
    @Transactional(readOnly = true)
    public Optional<Cliente> getClienteOptional(Long idCliente) {
        return clienteRepository.findById(idCliente);
    }

    @Transactional
    public void save(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Transactional
    public void delete(Long idCliente) {
        // Verifica si el cliente existe antes de intentar eliminarlo
        if (!clienteRepository.existsById(idCliente)) {
            throw new IllegalArgumentException("El Cliente con ID " + idCliente + " no existe.");
        }
        try {
            clienteRepository.deleteById(idCliente);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el cliente. Tiene datos asociados.", e);
        }
    }
    
    // Métodos adicionales útiles
    @Transactional(readOnly = true)
    public Optional<Cliente> getClienteByTelefono(String telefono) {
        return clienteRepository.findByTelefono(telefono);
    }
    
    @Transactional(readOnly = true)
    public Optional<Cliente> getClienteByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
    
    @Transactional(readOnly = true)
    public List<Cliente> searchByNombre(String nombre) {
        return clienteRepository.findByNombreCompletoContainingIgnoreCase(nombre);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByTelefono(String telefono) {
        return clienteRepository.findByTelefono(telefono).isPresent();
    }
    
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return clienteRepository.findByEmail(email).isPresent();
    }
}
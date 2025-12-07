/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.service;

import com.proyecto.domain.Cita;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.proyecto.repository.CitaRepository;
import java.util.NoSuchElementException;

@Service
public class CitaService {
    private final CitaRepository citaRepository;

    @Autowired
    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Transactional(readOnly = true)
    public List<Cita> getCitas() {
        var lista = citaRepository.findAll();
        return lista;
    }

    @Transactional(readOnly = true)
    public Cita getCita(Long idCita) {
        return citaRepository.findById(idCita).orElseThrow(
            () -> new NoSuchElementException("Cita con ID " + idCita + " no encontrada."));
    }
    
    @Transactional(readOnly = true)
    public Optional<Cita> getCitaOptional(Long idCita) {
        return citaRepository.findById(idCita);
    }

    @Transactional
    public void save(Cita cita) {
        citaRepository.save(cita);
    }

    @Transactional
    public void delete(Long idCita) {
        if (!citaRepository.existsById(idCita)) {
            throw new IllegalArgumentException("La Cita con ID " + idCita + " no existe.");
        }
        try {
            citaRepository.deleteById(idCita);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar la cita. Tiene datos asociados.", e);
        }
    }
    
    @Transactional(readOnly = true)
    public List<Cita> getCitasByCliente(Long clienteId) {
        return citaRepository.findByClienteId(clienteId);
    }
    
    @Transactional(readOnly = true)
    public List<Cita> getCitasByEstado(String estado) {
        return citaRepository.findByEstado(estado);
    }
}
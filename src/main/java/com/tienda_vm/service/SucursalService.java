/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda_vm.service;

import com.tienda_vm.domain.Sucursal;
import com.tienda_vm.repository.SucursalRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SucursalService {
    
    @Autowired
    private SucursalRepository sucursalRepository;

    @Transactional(readOnly = true)
    public List<Sucursal> getSucursales() {
        return sucursalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Sucursal> getSucursales(boolean activo) {
        if (activo) {
            return sucursalRepository.findByActivoTrue();
        }
        return sucursalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Sucursal getSucursal(Integer idSucursal) {
        return sucursalRepository.findById(idSucursal).orElseThrow(
            () -> new NoSuchElementException("Sucursal con ID " + idSucursal + " no encontrada."));
    }

    @Transactional
    public void save(Sucursal sucursal) {
        sucursalRepository.save(sucursal);
    }

    @Transactional
    public void delete(Integer idSucursal) {
        if (!sucursalRepository.existsById(idSucursal)) {
            throw new IllegalArgumentException("La Sucursal con ID " + idSucursal + " no existe.");
        }
        try {
            sucursalRepository.deleteById(idSucursal);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar la sucursal. Tiene datos asociados.", e);
        }
    }
}
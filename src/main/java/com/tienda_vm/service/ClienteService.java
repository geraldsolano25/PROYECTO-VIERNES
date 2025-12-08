
package com.tienda_vm.service;

import com.tienda_vm.domain.Cliente;
import com.tienda_vm.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<Cliente> getClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
      
        return clientes;
    }

    @Transactional(readOnly = true)
    public List<Cliente> getClientesActivos() {
        return clienteRepository.findActivos();
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> getCliente(Integer idCliente) {
        return clienteRepository.findById(idCliente);
    }

    @Transactional
    public void save(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Transactional
    public void delete(Integer idCliente) {
        if (!clienteRepository.existsById(idCliente)) {
            throw new IllegalArgumentException("El Cliente con ID " + idCliente + " no existe.");
        }
        clienteRepository.deleteById(idCliente);
    }
}

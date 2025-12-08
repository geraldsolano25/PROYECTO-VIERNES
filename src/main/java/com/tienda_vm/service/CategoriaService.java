/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tienda_vm.service;

import com.tienda_vm.domain.Categoria;
import com.tienda_vm.repository.CategoriaRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    //Este metodo recibe un id categoria, se busca en la tabla y lo retorna,
    //se usa en el proceso de modificar
    @Transactional(readOnly=true)
    public List<Categoria> getCategorias(boolean activo)
    {
        if(activo)
        {
            return categoriaRepository.findByActivoTrue();
        }
        return categoriaRepository.findAll();
    }
    @Transactional(readOnly=true)
    public Optional<Categoria> getCategoria(Integer idCategoria)
    {
        return categoriaRepository.findById(idCategoria);
    }
    
    @Transactional
    public void delete (Integer idCategoria)
    {
        if(!categoriaRepository.existsById(idCategoria)){
            throw new IllegalArgumentException("No existe una categoría con el id"+idCategoria);
            
        }
        try{
            categoriaRepository.deleteById(idCategoria);
        }
        catch(DataIntegrityViolationException e)
        {
            throw new IllegalArgumentException("No se puede eliminar la categoría "+idCategoria+" porque tiene datos asociados:"+e);
        }
    }
    @Autowired
    private FirebaseStorageService firebaseStorageService; 
    
  @Transactional
    public void save(Categoria categoria, MultipartFile imagenFile) {
    categoria = categoriaRepository.save(categoria);
        if(!imagenFile.isEmpty()) //Si nos pasaron una imagen... se guarda en la nube
        {
            try {
                String rutaImagen = firebaseStorageService.uploadImage(imagenFile, "categoria", categoria.getIdCategoria());
                categoria.setRutaImagen(rutaImagen);
                categoriaRepository.save(categoria);

            } catch(IOException e) {
                // Deberías manejar el error adecuadamente
                e.printStackTrace();
                // O lanzar una excepción personalizada
                throw new RuntimeException("Error al subir la imagen: " + e.getMessage(), e);
            }
        }
    }
}

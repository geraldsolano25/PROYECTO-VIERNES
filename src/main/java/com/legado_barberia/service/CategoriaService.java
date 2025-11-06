package com.legado_barberia.service;

import com.legado_barberia.domain.Categoria;
import com.legado_barberia.repository.CategoriaRepository;
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
    
    @Transactional(readOnly=true)
    public List<Categoria> getCategorias(boolean activo){
        if(activo) {
            return categoriaRepository.findByActivoTrue();
        }
        return categoriaRepository.findAll();
    }
 
    
    //Este metodo recibe un idCategoria, se busca en la tabla y lo retorna
    // Se una en el proceso de modificar una categoria
       @Transactional(readOnly=true)
    public Optional<Categoria> getCategoria(Integer idCategoria){
        return categoriaRepository.findById(idCategoria);
    }
    
    //Este metodo elimina, fisicamente, un registro de la tabla categoria.
    //Se hacen validaciones para prever efectivamente que se borre
    @Transactional
    public void delete(Integer idCategoria){
        //Se valida que el idCategoria exista
        if (!categoriaRepository.existsById(idCategoria)) {
            //No existe.... se lanza una excepcion para mostrar al usuario que no existe.
            throw new IllegalArgumentException("No existe una categoria con el id: "+idCategoria);
        }
        try {
            categoriaRepository.deleteById(idCategoria);
        } catch (DataIntegrityViolationException e){
            //Se lanza una excepcion para indicar que hay datos asociados.
            throw new IllegalStateException("No se puede elimnar "+idCategoria+" porque tiene datos asociados: "+e);
        }
    }
    
    @Autowired
    private FirebaseStorageService firebaseStorageService;
    //Este elemento hace dos acciones, crea un registro o lo actualiza
    //si el idCategoria tiene un valor se hace un update, si viene vacio un insert
    @Transactional
    public void save(Categoria categoria, MultipartFile imagenFile){
        categoria = categoriaRepository.save(categoria);
        if (!imagenFile.isEmpty()) { // Si nos pasaraon una imagen... se guarda en la nube
            try{
                String rutaImagen = firebaseStorageService.uploadImage(imagenFile, "categoria", categoria.getIdCategoria());
                categoria.setRutaImagen(rutaImagen);
                categoriaRepository.save(categoria);
            }catch(IOException e){
                
            }
        }
    }
}

package com.tiendaelectronica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tiendaelectronica.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	boolean existsByNombre(String nombre);
}
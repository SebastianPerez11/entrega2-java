package com.tiendaelectronica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tiendaelectronica.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	boolean existsByDni(int dni);
	
	boolean existsByEmail(String email);
}
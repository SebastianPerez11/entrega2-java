package com.tiendaelectronica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tiendaelectronica.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
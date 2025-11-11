package com.tiendaelectronica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendaelectronica.dto.AsignarCategoriaProductoDTO;
import com.tiendaelectronica.models.Producto;
import com.tiendaelectronica.responses.ErrorResponse;
import com.tiendaelectronica.services.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

	@Autowired
	private ProductoService productoService;

	@GetMapping
	public ResponseEntity<List<Producto>> getAllProductos() {
		try {
			List<Producto> productos = productoService.findAll();
			return ResponseEntity.ok(productos); // 200
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // 500
		}
	}

	@GetMapping("/{productoId}")
	public ResponseEntity<Producto> getProductoById(@PathVariable Long productoId) {
		try {
			Producto producto = productoService.findById(productoId);
			return ResponseEntity.ok(producto); // 200
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build(); // 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // 500
		}
	}

	@PostMapping("/create")
	public ResponseEntity<?> createProducto(@RequestBody Producto producto) {
		try {
			Producto productoCreado = productoService.save(producto);
			return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado); // 201
		} catch (IllegalStateException e) {
			ErrorResponse error = new ErrorResponse("Conflicto", e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error); // 409
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // 500
		}
	}

	@PutMapping("/{productoId}")
	public ResponseEntity<Producto> updateProductoById(@PathVariable Long productoId,
			@RequestBody Producto productoActualizado) {
		try {
			Producto producto = productoService.update(productoId, productoActualizado);
			return ResponseEntity.ok(producto); // 200
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build(); // 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // 500
		}
	}

	@DeleteMapping("/{productoId}")
	public ResponseEntity<Void> deleteProductoById(@PathVariable Long productoId) {
		try {
			productoService.deleteById(productoId);
			return ResponseEntity.noContent().build(); // 204
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build(); // 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // 500
		}
	}

	@PostMapping("/asignar-categoria")
	public ResponseEntity<?> asignarCategoriaAProducto(@RequestBody AsignarCategoriaProductoDTO dto) {
		try {
			Producto producto = productoService.asignarCategoriaAProducto(dto);
			return ResponseEntity.ok(producto);
		} catch (IllegalStateException e) {
			ErrorResponse error = new ErrorResponse("Conflicto", e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error); // 409
		} catch (IllegalArgumentException e) {
			ErrorResponse error = new ErrorResponse("Error 404", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); // 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // 500
		}
	}
}
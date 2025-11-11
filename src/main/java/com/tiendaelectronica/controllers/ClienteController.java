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

import com.tiendaelectronica.models.Cliente;
import com.tiendaelectronica.responses.ErrorResponse;
import com.tiendaelectronica.services.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public ResponseEntity<List<Cliente>> getAllClientes() {
		try {
			List<Cliente> clientes = clienteService.findAll();
			return ResponseEntity.ok(clientes); // 200
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // 500
		}
	}

	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> getClienteById(@PathVariable Long clienteId) {
		try {
			Cliente cliente = clienteService.findById(clienteId);
			return ResponseEntity.ok(cliente); // 200
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build(); // 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // 500
		}
	}

	@PostMapping("/create")
	public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
		try {
			Cliente clienteCreado = clienteService.save(cliente);
			return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado); // 201
		} catch (IllegalStateException e) {
			ErrorResponse error = new ErrorResponse("Conflicto", e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error); // 409
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // 500
		}
	}

	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> updateClienteById(@PathVariable Long clienteId,
			@RequestBody Cliente clienteActualizado) {
		try {
			Cliente cliente = clienteService.update(clienteId, clienteActualizado);
			return ResponseEntity.ok(cliente); // 200
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build(); // 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // 500
		}
	}

	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> deleteClienteById(@PathVariable Long clienteId) {
		try {
			clienteService.deleteById(clienteId);
			return ResponseEntity.noContent().build(); // 204
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build(); // 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // 500
		}
	}
}
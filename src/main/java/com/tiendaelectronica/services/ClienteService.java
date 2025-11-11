package com.tiendaelectronica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendaelectronica.interfaces.CRUDInterface;
import com.tiendaelectronica.models.Cliente;
import com.tiendaelectronica.repositories.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService implements CRUDInterface<Cliente, Long> {

	private final String message = "Cliente no encontrado";

	@Autowired
	private ClienteRepository repo;

	@Override
	public List<Cliente> findAll() {
		return repo.findAll();
	}

	@Override
	public Cliente findById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(message));
	}

	@Override
	@Transactional
	public Cliente save(Cliente nuevoCliente) {
		if (nuevoCliente.getDni() != 0 && repo.existsByDni(nuevoCliente.getDni())) {
			throw new IllegalStateException("Este DNI ya existe");
		}

		if (nuevoCliente.getEmail() != null && !nuevoCliente.getEmail().isEmpty()
				&& repo.existsByEmail(nuevoCliente.getEmail())) {
			throw new IllegalStateException("Este Email ya existe");
		}

		return repo.save(nuevoCliente);
	}

	@Override
	@Transactional
	public Cliente update(Long id, Cliente clienteActualizado) {
		Cliente cliente = findById(id);

		if (clienteActualizado.getNombre() != null && !clienteActualizado.getNombre().isEmpty()) {
			cliente.setNombre(clienteActualizado.getNombre());
		}

		if (clienteActualizado.getApellido() != null && !clienteActualizado.getApellido().isEmpty()) {
			cliente.setApellido(clienteActualizado.getApellido());
		}

		if (clienteActualizado.getDni() != 0) {
			cliente.setDni(clienteActualizado.getDni());
		}

		if (clienteActualizado.getEmail() != null && !clienteActualizado.getEmail().isEmpty()) {
			cliente.setEmail(clienteActualizado.getEmail());
		}

		if (clienteActualizado.getTelefono() != null && !clienteActualizado.getTelefono().isEmpty()) {
			cliente.setTelefono(clienteActualizado.getTelefono());
		}

		return repo.save(cliente);
	}

	@Override
	public void deleteById(Long id) {
		if (!repo.existsById(id)) {
			throw new IllegalArgumentException(message);
		}
		repo.deleteById(id);
	}
}
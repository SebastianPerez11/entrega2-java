package com.tiendaelectronica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendaelectronica.interfaces.CRUDInterface;
import com.tiendaelectronica.models.Categoria;
import com.tiendaelectronica.repositories.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoriaService implements CRUDInterface<Categoria, Long> {

	private final String message = "Categoría no encontrada";

	@Autowired
	private CategoriaRepository repo;

	@Override
	public List<Categoria> findAll() {
		return repo.findAll();
	}

	@Override
	public Categoria findById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(message));
	}

	@Override
	@Transactional
	public Categoria save(Categoria nuevaCategoria) {
		if (nuevaCategoria.getNombre() != null && !nuevaCategoria.getNombre().isEmpty()
				&& repo.existsByNombre(nuevaCategoria.getNombre())) {
			throw new IllegalStateException("Esta Categoría ya existe");
		}

		return repo.save(nuevaCategoria);
	}

	@Override
	@Transactional
	public Categoria update(Long id, Categoria categoriaActualizada) {
		Categoria categoria = findById(id);

		if (categoriaActualizada.getNombre() != null && !categoriaActualizada.getNombre().isEmpty()) {
			categoria.setNombre(categoriaActualizada.getNombre());
		}

		return repo.save(categoria);
	}

	@Override
	public void deleteById(Long id) {
		if (!repo.existsById(id)) {
			throw new IllegalArgumentException(message);
		}
		repo.deleteById(id);
	}
}
package com.tiendaelectronica.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendaelectronica.dto.AsignarCategoriaProductoDTO;
import com.tiendaelectronica.interfaces.CRUDInterface;
import com.tiendaelectronica.models.Categoria;
import com.tiendaelectronica.models.Producto;
import com.tiendaelectronica.repositories.CategoriaRepository;
import com.tiendaelectronica.repositories.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoService implements CRUDInterface<Producto, Long> {

	private final String message = "Producto no encontrado";

	@Autowired
	private ProductoRepository repo;

	@Autowired
	private CategoriaRepository categoriaRepo;

	@Override
	public List<Producto> findAll() {
		return repo.findAll();
	}

	@Override
	public Producto findById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(message));
	}

	@Override
	@Transactional
	public Producto save(Producto nuevoProducto) {
		return repo.save(nuevoProducto);
	}

	@Override
	@Transactional
	public Producto update(Long id, Producto productoActualizado) {
		Producto producto = findById(id);

		if (productoActualizado.getNombre() != null && !productoActualizado.getNombre().isEmpty()) {
			producto.setNombre(productoActualizado.getNombre());
		}

		if (productoActualizado.getDescripcion() != null && !productoActualizado.getDescripcion().isEmpty()) {
			producto.setDescripcion(productoActualizado.getDescripcion());
		}

		if (productoActualizado.getPrecio() != null) {
			producto.setPrecio(productoActualizado.getPrecio());
		}

		if (productoActualizado.getStock() != null) {
			producto.setStock(productoActualizado.getStock());
		}

		return repo.save(producto);
	}

	@Override
	public void deleteById(Long id) {
		if (!repo.existsById(id)) {
			throw new IllegalArgumentException(message);
		}
		repo.deleteById(id);
	}

	@Transactional
	public Producto asignarCategoriaAProducto(AsignarCategoriaProductoDTO dto) {
		Producto producto = findById(dto.getProductoId());

		Categoria categoria = categoriaRepo.findById(dto.getCategoriaId())
				.orElseThrow(() -> new IllegalArgumentException(
						"La Categoría con ID " + dto.getCategoriaId() + " no existe"));

		if (producto.getCategoria() != null && producto.getCategoria().getId().equals(categoria.getId())) {
			throw new IllegalStateException("El Producto ya tiene asignada esta Categoría");
		}

		producto.setCategoria(categoria);

		return repo.save(producto);
	}
}
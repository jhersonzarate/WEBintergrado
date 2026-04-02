package com.snacktopia.backend.repository;
import com.snacktopia.backend.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}

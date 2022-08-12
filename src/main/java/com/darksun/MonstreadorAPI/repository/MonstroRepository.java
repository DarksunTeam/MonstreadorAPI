package com.darksun.MonstreadorAPI.repository;

import com.darksun.MonstreadorAPI.entity.Monstro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonstroRepository extends JpaRepository<Monstro, Long> {

}

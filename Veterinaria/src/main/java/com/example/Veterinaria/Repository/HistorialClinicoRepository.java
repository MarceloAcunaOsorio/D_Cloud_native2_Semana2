package com.example.Veterinaria.Repository;

import com.example.Veterinaria.Model.HistorialClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistorialClinicoRepository extends JpaRepository<HistorialClinico, Long> {
    List<HistorialClinico> findByMascotaId(Long mascotaId);
    List<HistorialClinico> findByVeterinarioId(Long veterinarioId);
} 
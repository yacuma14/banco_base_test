package banco.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import banco.test.entity.PagoEntity;

public interface PagoRepository extends JpaRepository<PagoEntity, Long>   {

	 Optional<PagoEntity> findByStatusPagoAndRealizaPago(String status, String realizaPago);
	
	 List<PagoEntity> findByRealizaPago(String realizaPago);
}

package test.java.br.com.zyntra.prontia.repository;

import br.com.zyntra.prontia.entity.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Servico, Long> {
    Optional<Servico> findBySlug(String slug);
}

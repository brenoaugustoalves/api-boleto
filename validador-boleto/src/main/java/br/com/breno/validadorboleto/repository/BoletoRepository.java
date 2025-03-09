package br.com.breno.validadorboleto.repository;

import br.com.breno.validadorboleto.entity.BoletoEntity;
import org.springframework.data.repository.CrudRepository;

public interface BoletoRepository extends CrudRepository<BoletoEntity, Long> {
}

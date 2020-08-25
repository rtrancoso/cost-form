package br.com.rtrancoso.costcovidform.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.rtrancoso.costcovidform.model.Presenca;

public interface PresencaRepository extends MongoRepository<Presenca, String> {

	public Optional<Presenca> findByNome( String nome );

}

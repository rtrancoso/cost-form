package br.com.rtrancoso.costcovidform2.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.rtrancoso.costcovidform2.model.Presenca;

public interface PresencaRepository extends MongoRepository<Presenca, String> {

	public Optional<Presenca> findByNome( String nome );

}

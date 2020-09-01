package br.com.rtrancoso.costcovidform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.rtrancoso.costcovidform.model.Presenca;
import br.com.rtrancoso.costcovidform.model.Reuniao;

public interface PresencaRepository extends MongoRepository<Presenca, String> {

	public Optional<Presenca> findByNomeAndReuniao( String nome, Reuniao reuniao );

	public List<Presenca> findAllByReuniao( Reuniao reuniao );

}

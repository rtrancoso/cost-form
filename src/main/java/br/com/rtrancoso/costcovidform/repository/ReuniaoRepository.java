package br.com.rtrancoso.costcovidform.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.rtrancoso.costcovidform.model.Reuniao;

public interface ReuniaoRepository extends MongoRepository<Reuniao, String> {

	public List<Reuniao> findAllByAtivo( boolean ativo );

}

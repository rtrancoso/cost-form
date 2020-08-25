package br.com.rtrancoso.costcovidform2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.rtrancoso.costcovidform2.model.Config;

public interface ConfigRepository extends MongoRepository<Config, String> {

	public Config findByKey( String key );

}

package br.com.rtrancoso.costcovidform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.rtrancoso.costcovidform.model.Config;

public interface ConfigRepository extends MongoRepository<Config, String> {

	public Config findByKey( String key );

}

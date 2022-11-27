package br.com.rtrancoso.costform.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.rtrancoso.costform.model.Config;

public interface ConfigRepository extends MongoRepository<Config, String> {

    public Config findByKey(String key);

}

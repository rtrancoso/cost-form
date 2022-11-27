package br.com.rtrancoso.costform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.rtrancoso.costform.model.Confirmation;
import br.com.rtrancoso.costform.model.Meeting;

public interface ConfirmationRepository extends MongoRepository<Confirmation, String> {

    public Optional<Confirmation> findByNameAndMeeting(String name, Meeting meeting);

    public List<Confirmation> findAllByMeeting(Meeting meeting);

}

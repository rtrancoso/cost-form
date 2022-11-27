package br.com.rtrancoso.costform.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.rtrancoso.costform.model.Meeting;

public interface MeetingRepository extends MongoRepository<Meeting, String> {

    public Meeting findByTypeAndActive(Meeting.Type type, boolean active);

    public List<Meeting> findAllByActive(boolean active);

}

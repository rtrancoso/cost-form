package br.com.rtrancoso.costform.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rtrancoso.costform.dto.StatusDTO;
import br.com.rtrancoso.costform.model.Confirmation;
import br.com.rtrancoso.costform.model.Meeting;
import br.com.rtrancoso.costform.repository.ConfigRepository;
import br.com.rtrancoso.costform.repository.ConfirmationRepository;
import br.com.rtrancoso.costform.repository.MeetingRepository;

@RestController
@RequestMapping("meetings")
public class MainController {

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private ConfirmationRepository confirmationRepository;

    @GetMapping("{type}/active")
    public Meeting getActiveByType(@PathVariable Meeting.Type type) {
        return meetingRepository.findByTypeAndActive(type, true);
    }

    @GetMapping
    public List<Meeting> getAll() {
        return meetingRepository.findAll();
    }

    @GetMapping("active")
    public List<Meeting> getAllActive() {
        return meetingRepository.findAllByActive(true);
    }

    @PostMapping
    public Meeting create(@RequestBody Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    @PutMapping("{meetingId}/active")
    public void toggle(@PathVariable String meetingId) {
        Optional<Meeting> meeting = meetingRepository.findById(meetingId);
        if (meeting.isPresent()) {
            Meeting r = meeting.get();
            r.setActive(!r.isActive());
            meetingRepository.save(r);
        }
    }

    @DeleteMapping("{meetingId}")
    public void delete(@PathVariable String meetingId) {
        meetingRepository.deleteById(meetingId);
    }

    @GetMapping("{meetingId}/status")
    public StatusDTO status(@PathVariable String meetingId) {
        long total = Long.parseLong(configRepository.findByKey("QUANTIDADE").getValue());
        long rest = total - confirmationRepository.findAllByMeeting(meetingRepository.findById(meetingId).get()).size();

        return StatusDTO.builder().rest(rest).full(total).build();
    }

    @GetMapping("{meetingId}/list")
    public List<Confirmation> listConfirmation(@PathVariable String meetingId) {
        return confirmationRepository.findAllByMeeting(meetingRepository.findById(meetingId).get());
    }

    @PostMapping("{meetingId}/confirm")
    public Confirmation confirm(@PathVariable String meetingId, @RequestBody Confirmation confirmation) {
        var meeting = meetingRepository.findById(meetingId).get();
        var optional = confirmationRepository.findByNameAndMeeting(confirmation.getName(), meeting);
        if (optional.isEmpty()) {
            confirmation.setConfirmedAt(LocalDateTime.now());
            confirmationRepository.save(confirmation);
        } else {
            confirmation = optional.get();
        }
        return confirmation;
    }

}

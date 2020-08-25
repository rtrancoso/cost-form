package br.com.rtrancoso.costcovidform.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.rtrancoso.costcovidform.dto.StatusDTO;
import br.com.rtrancoso.costcovidform.model.Presenca;
import br.com.rtrancoso.costcovidform.repository.ConfigRepository;
import br.com.rtrancoso.costcovidform.repository.PresencaRepository;

@RestController
public class MainController {

	@Autowired
	private ConfigRepository configRepository;

	@Autowired
	private PresencaRepository presencaRepository;

	@GetMapping( "/status" )
	public StatusDTO status() {
		long total = Long.parseLong( configRepository.findByKey( "QUANTIDADE" ).getValue() );
		long restante = total - presencaRepository.count();

		return StatusDTO.builder().rest( restante ).full( total ).build();
	}

	@GetMapping( "/list" )
	public List<Presenca> list() {
		return presencaRepository.findAll();
	}

	@PostMapping( "/confirm" )
	public Presenca confirm( @RequestBody Presenca presenca ) {
		var optional = presencaRepository.findByNome( presenca.getNome() );
		if( !optional.isPresent() ) {
			presenca.setDataConfirmacao( LocalDateTime.now() );
			presencaRepository.save( presenca );
		} else {
			presenca = optional.get();
		}
		return presenca;
	}

}

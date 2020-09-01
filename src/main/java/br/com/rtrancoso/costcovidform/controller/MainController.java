package br.com.rtrancoso.costcovidform.controller;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.rtrancoso.costcovidform.dto.StatusDTO;
import br.com.rtrancoso.costcovidform.model.Presenca;
import br.com.rtrancoso.costcovidform.model.Reuniao;
import br.com.rtrancoso.costcovidform.repository.ConfigRepository;
import br.com.rtrancoso.costcovidform.repository.PresencaRepository;
import br.com.rtrancoso.costcovidform.repository.ReuniaoRepository;

@RestController
public class MainController {

	@Autowired
	private ConfigRepository configRepository;

	@Autowired
	private ReuniaoRepository reuniaoRepository;

	@Autowired
	private PresencaRepository presencaRepository;

	@GetMapping( "/meetings" )
	public List<Reuniao> meetings() {
		return reuniaoRepository.findAll();
	}

	@GetMapping( "/meetings/active" )
	public List<Reuniao> meetingsActive() {
		return reuniaoRepository.findAllByAtivo( true );
	}

	@PostMapping( "/meetings" )
	public Reuniao meetingsCreate( @RequestBody Reuniao reuniao ) {
		return reuniaoRepository.save( reuniao );
	}

	@PutMapping( "/meetings/{meeting}/ativo" )
	public void meetingsToggle( @PathVariable String meeting ) {
		Optional<Reuniao> reuniao = reuniaoRepository.findById( meeting );
		if( reuniao.isPresent() ) {
			Reuniao r = reuniao.get();
			r.setAtivo( !r.isAtivo() );
			reuniaoRepository.save( r );
		}
	}

	@DeleteMapping( "/meetings/{meeting}" )
	public void meetingsDelete( @PathVariable String meeting ) {
		reuniaoRepository.deleteById( meeting );
	}

	@GetMapping( "/meetings/{meeting}/status" )
	public StatusDTO meetingsStatus( @PathVariable String meeting ) {
		long total = Long.parseLong( configRepository.findByKey( "QUANTIDADE" ).getValue() );
		long restante = total - presencaRepository.findAllByReuniao( reuniaoRepository.findById( meeting ).get() ).size();

		return StatusDTO.builder().rest( restante ).full( total ).build();
	}

	@GetMapping( "/list" )
	public List<Presenca> list() {
		return presencaRepository.findAll();
	}

	@PostMapping( "/confirm" )
	public Presenca confirm( @RequestBody Presenca presenca ) {
		var optional = presencaRepository.findByNomeAndReuniao( presenca.getNome(), presenca.getReuniao() );
		if( !optional.isPresent() ) {
			presenca.setDataConfirmacao( LocalDateTime.now() );
			presencaRepository.save( presenca );
		} else {
			presenca = optional.get();
		}
		return presenca;
	}

}

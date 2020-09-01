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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rtrancoso.costcovidform.dto.StatusDTO;
import br.com.rtrancoso.costcovidform.model.Presenca;
import br.com.rtrancoso.costcovidform.model.Reuniao;
import br.com.rtrancoso.costcovidform.repository.ConfigRepository;
import br.com.rtrancoso.costcovidform.repository.PresencaRepository;
import br.com.rtrancoso.costcovidform.repository.ReuniaoRepository;

@RestController
@RequestMapping( "meetings" )
public class MainController {

	@Autowired
	private ConfigRepository configRepository;

	@Autowired
	private ReuniaoRepository reuniaoRepository;

	@Autowired
	private PresencaRepository presencaRepository;

	@GetMapping
	public List<Reuniao> list() {
		return reuniaoRepository.findAll();
	}

	@GetMapping( "active" )
	public List<Reuniao> listActive() {
		return reuniaoRepository.findAllByAtivo( true );
	}

	@PostMapping
	public Reuniao create( @RequestBody Reuniao reuniao ) {
		return reuniaoRepository.save( reuniao );
	}

	@PutMapping( "{meeting}/ativo" )
	public void toggle( @PathVariable String meeting ) {
		Optional<Reuniao> reuniao = reuniaoRepository.findById( meeting );
		if( reuniao.isPresent() ) {
			Reuniao r = reuniao.get();
			r.setAtivo( !r.isAtivo() );
			reuniaoRepository.save( r );
		}
	}

	@DeleteMapping( "{meeting}" )
	public void delete( @PathVariable String meeting ) {
		reuniaoRepository.deleteById( meeting );
	}

	@GetMapping( "{meeting}/status" )
	public StatusDTO status( @PathVariable String meeting ) {
		long total = Long.parseLong( configRepository.findByKey( "QUANTIDADE" ).getValue() );
		long restante = total - presencaRepository.findAllByReuniao( reuniaoRepository.findById( meeting ).get() ).size();

		return StatusDTO.builder().rest( restante ).full( total ).build();
	}

	@GetMapping( "{meeting}/list" )
	public List<Presenca> listConfirmation( @PathVariable String meeting ) {
		return presencaRepository.findAllByReuniao( reuniaoRepository.findById( meeting ).get() );
	}

	@PostMapping( "{meeting}/confirm" )
	public Presenca confirm( @PathVariable String meeting, @RequestBody Presenca presenca ) {
		var reuniao = reuniaoRepository.findById( meeting ).get();
		var optional = presencaRepository.findByNomeAndReuniao( presenca.getNome(), reuniao );
		if( !optional.isPresent() ) {
			presenca.setDataConfirmacao( LocalDateTime.now() );
			presencaRepository.save( presenca );
		} else {
			presenca = optional.get();
		}
		return presenca;
	}

}

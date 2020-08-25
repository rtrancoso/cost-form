package br.com.rtrancoso.costcovidform2.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document( collection = "presenca" )
public class Presenca {

	@Id
	private String id;
	private String nome;
	private LocalDateTime dataConfirmacao;

}

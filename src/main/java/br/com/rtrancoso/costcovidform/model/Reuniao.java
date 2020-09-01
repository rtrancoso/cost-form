package br.com.rtrancoso.costcovidform.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document( collection = "reuniao" )
public class Reuniao {

	@Id
	private String id;
	private String descricao;
	private boolean ativo;

}

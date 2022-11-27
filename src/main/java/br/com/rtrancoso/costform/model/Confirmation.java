package br.com.rtrancoso.costform.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "confirmation")
public class Confirmation {

    @Id
    private String id;
    private String name;
    private LocalDateTime confirmedAt;

    @DBRef
    private Meeting meeting;

}

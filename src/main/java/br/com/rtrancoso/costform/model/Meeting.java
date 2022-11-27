package br.com.rtrancoso.costform.model;

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
@Document(collection = "meeting")
public class Meeting {

    @Id
    private String id;
    private String description;
    private Type type;
    private boolean active;

    public enum Type {
        LUNCH,
        MEETING,
        SATURDAY,
        SUNDAY
    }

}

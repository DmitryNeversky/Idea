package org.dneversky.idea.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Tag {

    @Id
    private String id;

    @NotNull
    @Size(max = 128, message = "Name size is: min 0 max 128")
    private String name;

    private List<Idea> ideas = new ArrayList<>();

    public Tag(String name) {
        this.name = name;
    }
}

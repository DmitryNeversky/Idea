package org.dneversky.idea.agregate;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
@NoArgsConstructor
public class Tag {

    @Id
    private Integer id;

    @NotNull
    @Size(max = 128, message = "Name size is: min 0 max 128")
    private String name;

    private List<Long> ideas = new ArrayList<>();

    public Tag(String name) {
        this.name = name;
    }
}

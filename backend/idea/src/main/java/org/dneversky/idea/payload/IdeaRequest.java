package org.dneversky.idea.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dneversky.idea.entity.Tag;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class IdeaRequest {

    @NotNull(message = "Title can not be null")
    @Size(min = 8, max = 256, message = "Title's size is: min 8 max 256")
    private String title;

    @NotNull(message = "Text can not be null")
    @Size(min = 256, max = 32768, message = "Text's size is: min 256 max 32768")
    private String body;

    @JsonIgnoreProperties(value = "ideas")
    private List<Tag> tags;

    private List<String> removeImages;
    private List<String> removeFiles;
}

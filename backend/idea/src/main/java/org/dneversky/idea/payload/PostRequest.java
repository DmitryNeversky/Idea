package org.dneversky.idea.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class PostRequest {

    @NotNull
    @Size(max = 128, message = "Name size is: min 0 max 128")
    private String name;
}

package org.dneversky.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dneversky.user.entity.Post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserRequest {

    @NotNull(message = "Name can not be null")
    @Size(min = 3, max = 96, message = "Name's size is: min 3 max 96")
    private String name;

    @NotNull(message = "Phone can not be null")
    @Size(min = 10, max = 10, message = "Phone's size is: min 10 max 10")
    @NotBlank(message = "Phone contains whitespaces or null value")
    private String phone;

    @NotNull(message = "Birthday can not be null")
    private Date birthday;

    @Size(max = 255, message = "About size is: min 0 max 255")
    private String city;

    @Size(max = 1024, message = "About size is: min 0 max 1024")
    private String about;

    @JsonIgnoreProperties("users")
    private Post post;
}

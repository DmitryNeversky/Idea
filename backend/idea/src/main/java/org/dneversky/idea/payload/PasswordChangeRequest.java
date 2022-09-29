package org.dneversky.idea.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class PasswordChangeRequest {

    private String currentPassword;

    @NotNull(message = "Password can not be null")
    @Size(min = 6, max = 32, message = "Password's size is: min 6 max 32")
    private String newPassword;
}

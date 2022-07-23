package org.dneversky.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveUserRequest {
    String username;
    String password;
    String name;
    String phone;
    LocalDate birthday;
    int postId;
}

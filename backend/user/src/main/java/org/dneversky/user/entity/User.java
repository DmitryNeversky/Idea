package org.dneversky.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.dneversky.user.dto.SaveUserRequest;
import org.dneversky.user.dto.UpdateUserRequest;
import org.hibernate.annotations.NaturalId;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @NotNull(message = "Username can not be null")
    @Email(message = "Email is invalid")
    @Size(min = 3, max = 64, message = "Username's size is: min 3 max 64")
    private String username;

    @NotNull(message = "Password can not be null")
    @Size(min = 6, message = "Password's size is: min 6 max 32")
    @Lob
    private String password;

    private boolean enabled;

    @NotNull(message = "Name can not be null")
    @Size(min = 3, max = 96, message = "Name's size is: min 3 max 96")
    private String name;

    @NotNull(message = "Phone can not be null")
    @Size(min = 10, max = 10, message = "Phone's size is: min 10 max 10")
    @NotBlank(message = "Phone contains whitespaces or null value")
    private String phone;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull(message = "Birthday can not be null")
    private LocalDate birthday;

    private String avatar;

    @Size(max = 255, message = "About size is: min 0 max 255")
    private String city;

    @Size(max = 1024, message = "About size is: min 0 max 1024")
    @Lob
    private String about;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate registeredDate;

    @JsonIgnoreProperties("users")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @JsonIgnoreProperties("users")
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_post",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Post post;

    public void mergeUpdateRequest(UpdateUserRequest userRequest) {
        name = userRequest.getName();
        phone = userRequest.getPhone();
        birthday = userRequest.getBirthday();
        avatar = userRequest.getAvatar();
        city = userRequest.getCity();
        about = userRequest.getAbout();
    }

    public User(SaveUserRequest userRequest) {
        this.username = userRequest.getUsername();
        this.name = userRequest.getName();
        this.phone = userRequest.getPhone();
        this.birthday = userRequest.getBirthday();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}

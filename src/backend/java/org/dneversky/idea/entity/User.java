package org.dneversky.idea.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.NaturalId;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NaturalId
    @NotNull(message = "Username can not be null")
    @Email(message = "Email is invalid")
    @Size(min = 3, max = 64, message = "Username's size is: min 3 max 64")
    private String username;

    @NotNull(message = "Password can not be null")
    @Size(min = 6, message = "Password's size is: min 6")
    @NotBlank(message = "Password contains whitespaces or null value")
    @Lob
    private String password;

    // Need a composition _ Profile

    @NotNull(message = "Name can not be null")
    @Size(min = 6, max = 96, message = "Name's size is: min 6 max 96")
    private String name;

    @NotNull(message = "Phone can not be null")
    @Size(min = 11, max = 11, message = "Phone's size is: min 11 max 11")
    @NotBlank(message = "Phone contains whitespaces or null value")
    private String phone;

    @Lob
    @Size(max = 1024, message = "About size is: min 0 max 1024")
    private String about;

    @Size(max = 255, message = "About size is: min 0 max 255")
    private String city;

    private String avatar;

    @NotNull(message = "Birthday can not be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate registeredDate;

    @OneToMany(mappedBy = "author", cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
    private List<Idea> ideas = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Notification> notifications;

    private String post;

    public void addNotification(Notification notification) {
        if(notifications == null) {
            notifications = new ArrayList<>();
        }
        notifications.add(notification);
    }
}

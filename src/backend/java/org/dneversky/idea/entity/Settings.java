package org.dneversky.idea.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dneversky.idea.entity.settings.NoticeSetting;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private NoticeSetting noticeSetting;
}

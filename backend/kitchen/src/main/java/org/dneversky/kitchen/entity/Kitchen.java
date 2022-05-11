package org.dneversky.kitchen.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@NoArgsConstructor
@Document
public class Kitchen {

    @Id
    private Integer id;

    @DocumentReference
    private Menu menu;
}

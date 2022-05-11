package org.dneversky.kitchen.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Data
@NoArgsConstructor
@Document
public class Consumer {

    @Id
    private Long id;

    private String name;

    @DocumentReference
    private Kitchen kitchen;
}

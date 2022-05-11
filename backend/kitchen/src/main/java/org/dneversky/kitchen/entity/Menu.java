package org.dneversky.kitchen.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document
public class Menu {

    @Id
    private int id;

    @DocumentReference
    private List<MenuItem> menuItemList = new ArrayList<>();
}

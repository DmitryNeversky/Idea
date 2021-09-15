package org.dneversky.idea.entity.settings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class NoticeSetting {

    // may refactor to user's session

    private boolean disabledNotice;
    private int successDuration;
    private int errorDuration;
    private String horizontalPosition;
    private String verticalPosition;
}

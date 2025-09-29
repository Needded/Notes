package com.needded.notes.Entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoteDTO {

    private Long id;
    private String Title;
    private String text;
}

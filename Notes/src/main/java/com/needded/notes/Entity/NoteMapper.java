package com.needded.notes.Entity;

public class NoteMapper {

    public static Note DTOtoNote (NoteDTO noteDTO, String userId){

        Note note = new Note();
        note.setId(noteDTO.getId());
        note.setTitle(noteDTO.getTitle());
        note.setText(noteDTO.getText());
        note.setUserId(userId);

        return note;
    }

    public static NoteDTO noteToDTO (Note note){

        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setId(note.getId());
        noteDTO.setTitle(note.getTitle());
        noteDTO.setText(note.getText());

        return noteDTO;
    }
}

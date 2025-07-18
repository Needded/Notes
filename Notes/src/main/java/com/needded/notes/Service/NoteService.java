package com.needded.notes.Service;

import com.needded.notes.Entity.Note;
import com.needded.notes.Repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotes() {
        try{
            return noteRepository.findAll();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void createNote(Note note){
        try {
            noteRepository.save(note);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void editNote(Long noteId, Note note){
        try {
            Note noteToUpdate = noteRepository.findById(noteId)
                    .orElseThrow(RuntimeException::new);

            noteToUpdate.setText(note.getText());
            noteRepository.save(noteToUpdate);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteNote(Long noteId){
        try {
            noteRepository.deleteById(noteId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Optional<Note> getNoteById(Long noteId) {
        try {
            return noteRepository.findById(noteId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

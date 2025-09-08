package com.needded.notes.Service;

import com.needded.notes.Entity.Note;
import com.needded.notes.Repository.NoteRepository;
import com.needded.notes.Security.NotesJWTService;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final NotesJWTService notesJWTService;

    public NoteService(NoteRepository noteRepository, NotesJWTService notesJWTService) {
        this.noteRepository = noteRepository;
        this.notesJWTService = notesJWTService;
    }


    public Note createNote(@NonNull Note note){
        try {
            return noteRepository.save(note);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Note editNote(@NonNull Long noteId, @NonNull Note note){
        try {
            Note noteToUpdate = noteRepository.findById(noteId)
                    .orElseThrow(RuntimeException::new);

            noteToUpdate.setText(note.getText());
            return noteRepository.save(noteToUpdate);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean deleteNote(@NonNull Long noteId, @NonNull String userId){
        try {
            noteRepository.deleteNoteByIdAndUserId(noteId, userId);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Note getNoteByIdAndUserId(@NonNull Long noteId, String userId) {
        try {
            return noteRepository.findNoteByIdAndUserId(noteId, userId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Note> findAllByUserId(@NonNull String userId) {
        try {
            return noteRepository.findAllNotesByUserId(userId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return List.of();
    }

    public String extractUserId (@NonNull String headerToken){
        String token = headerToken.substring(7); // remove "Bearer "

        return notesJWTService.extractUserId(token);
    }
}

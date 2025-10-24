package com.needded.notes.Service;

import com.needded.notes.Entity.Note;
import com.needded.notes.Repository.NoteRepository;
import com.needded.notes.Security.NotesJWTService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {

    private final Logger logger= LoggerFactory.getLogger(NoteService.class);
    private final NoteRepository noteRepository;
    private final NotesJWTService notesJWTService;

    public NoteService(NoteRepository noteRepository, NotesJWTService notesJWTService) {
        this.noteRepository = noteRepository;
        this.notesJWTService = notesJWTService;
    }

    @Transactional
    public Note createNote(@NonNull Note note){
        try {
            return noteRepository.save(note);

        } catch (Exception e) {
            logger.error("Error saving note.", e);
        }
        return null;
    }

    @Transactional
    public Note editNote(@NonNull Long noteId, @NonNull Note note, String userId){
        try {
            Note noteToUpdate = noteRepository.findNoteByIdAndUserId(noteId, userId);

            noteToUpdate.setTitle(note.getTitle());
            noteToUpdate.setText(note.getText());
            return noteRepository.save(noteToUpdate);

        } catch (RuntimeException e) {
            logger.error("Error saving note {} for user {}", noteId, userId, e);
        }
        return null;
    }

    @Transactional
    public boolean deleteNote(@NonNull Long noteId, @NonNull String userId){
        try {
            noteRepository.deleteByIdAndUserId(noteId, userId);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting note {} for user {}", noteId, userId, e);
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Note getNoteByIdAndUserId(@NonNull Long noteId, String userId) {
        try {
            return noteRepository.findNoteByIdAndUserId(noteId, userId);
        } catch (Exception e) {
            logger.error("Error fetching note {} for user {}", noteId, userId, e);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Note> findAllByUserId(@NonNull String userId) {
        try {
            return noteRepository.findAllNotesByUserId(userId);
        } catch (Exception e) {
            logger.error("Error fetching notes for user {}", userId, e);
        }
        return List.of();
    }

    public String extractUserId (@NonNull String headerToken){
        String token = headerToken.substring(7); // remove "Bearer "

        return notesJWTService.extractUserId(token);
    }

}

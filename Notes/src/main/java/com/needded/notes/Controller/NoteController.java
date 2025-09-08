package com.needded.notes.Controller;

import com.needded.notes.Entity.Note;
import com.needded.notes.Service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private static final Logger logger= LoggerFactory.getLogger(NoteController.class);
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Note>> getAllNotes(@RequestHeader("Authorization") String authHeader) {
        String userId = noteService.extractUserId(authHeader);

        logger.info("Getting all notes...");
        logger.info("User id: {}",userId);


        List <Note> notes= noteService.findAllByUserId(userId);

        return ResponseEntity.ok(notes);
    }

    @GetMapping("/getNoteById/{noteId}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long noteId, @RequestHeader("Authorization") String authHeader) {
        String userId = noteService.extractUserId(authHeader);

        logger.info("Getting note with id: {}", noteId);
        logger.info("User id: {}",userId);

        Note note= noteService.getNoteByIdAndUserId(noteId, userId);

        return ResponseEntity.ok(note);
    }

    @PostMapping("/save")
    public ResponseEntity<Note> saveNote(@RequestHeader("Authorization") String authHeader, @RequestBody Note note) {
        String userId = noteService.extractUserId(authHeader);

        logger.info("Saving note...");
        logger.info("User id: {}",userId);


        note.setUserId(userId);
        Note saved;

        if (note.getId() == null) {
            saved = noteService.createNote(note);
        } else {
            saved = noteService.editNote(note.getId(), note);
        }


        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id, @RequestHeader("Authorization") String authHeader){
        String userId = noteService.extractUserId(authHeader);

        logger.info("Deleting note with id: {}", id);
        logger.info("User id: {}",userId);


        boolean deleted = noteService.deleteNote(id, userId);
        if (deleted) {
            return ResponseEntity.ok("Note deleted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found!");
        }
    }

}

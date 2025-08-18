package com.needded.notes.Controller;

import com.needded.notes.Entity.Note;
import com.needded.notes.Service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @PostMapping
    public ResponseEntity<Note> saveNote(@RequestBody Note note) {
        Note saved;
        if (note.getId() == null) {
            saved = noteService.createNote(note);
        } else {
            saved = noteService.editNote(note.getId(), note);
        }
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id){
        boolean deleted = noteService.deleteNote(id);
        if (deleted) {
            return ResponseEntity.ok("Note deleted!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found!");
        }
    }

}

package com.needded.notes.Controller;

import com.needded.notes.Entity.Note;
import com.needded.notes.Entity.NoteDTO;
import com.needded.notes.Entity.NoteMapper;
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

    private final Logger logger= LoggerFactory.getLogger(NoteController.class);
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<NoteDTO>> getAllNotes(@RequestHeader("Authorization") String authHeader) {
        String userId = noteService.extractUserId(authHeader);

        logger.info("Getting all notes...");
        logger.info("User id: {}",userId);


        List <Note> notes= noteService.findAllByUserId(userId);
        List <NoteDTO> noteDTO= notes
                .stream()
                .map(NoteMapper::noteToDTO)
                .toList();

        return ResponseEntity.ok(noteDTO);
    }

    @GetMapping("/getNoteById/{noteId}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable Long noteId, @RequestHeader("Authorization") String authHeader) {
        String userId = noteService.extractUserId(authHeader);

        logger.info("Getting note with id: {}", noteId);
        logger.info("User id: {}",userId);

        Note note= noteService.getNoteByIdAndUserId(noteId, userId);
        NoteDTO noteDTO=NoteMapper.noteToDTO(note);

        return ResponseEntity.ok(noteDTO);
    }

    @PostMapping("/save")
    public ResponseEntity<NoteDTO> saveNote(@RequestHeader("Authorization") String authHeader, @RequestBody NoteDTO noteDTO) {
        String userId = noteService.extractUserId(authHeader);

        logger.info("Saving note...");
        logger.info("User id: {}",userId);

        Note toSave = NoteMapper.DTOtoNote(noteDTO,userId);
        Note saved = noteService.createNote(toSave);
        NoteDTO response= NoteMapper.noteToDTO(saved);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{noteId}")
    public ResponseEntity<NoteDTO> updateNote(@RequestHeader("Authorization") String authHeader,@PathVariable Long noteId, @RequestBody NoteDTO noteDTO) {
        String userId = noteService.extractUserId(authHeader);

        logger.info("Updating note...");
        logger.info("User id: {}",userId);

        Note toUpdate=NoteMapper.DTOtoNote(noteDTO,userId);
        Note updated=noteService.editNote(noteId,toUpdate,userId);
        NoteDTO response=NoteMapper.noteToDTO(updated);

        return ResponseEntity.ok(response);
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

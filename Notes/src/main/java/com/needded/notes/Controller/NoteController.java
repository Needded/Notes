package com.needded.notes.Controller;

import com.needded.notes.Entity.Note;
import com.needded.notes.Service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @GetMapping("/view")
    public String getAllNotes (Model model){

        List<Note> notesList=noteService.getAllNotes();

        model.addAttribute("notes", notesList);

        return "notesPage";
    }

    @PostMapping("/save")
    public String saveNote(@ModelAttribute Note note) {

        if (note.getId() == null) {
            noteService.createNote(note);
        } else {
            noteService.editNote(note.getId(), note);
        }

        return "redirect:/notesPage";
    }
    @GetMapping("/form")
    public String showForm(@RequestParam(required = false) Long id, Model model) {

        Note note;
        if (id != null) {
            note = noteService.getNoteById(id).orElse(new Note());
        } else {
            note = new Note();
        }
        model.addAttribute("note", note);
        return "noteForm";
    }


    @DeleteMapping("/delete/{noteId}")
    public String deleteNote (@PathVariable Long noteId){

        noteService.deleteNote(noteId);

        return "redirect:/notesPage";
    }
}

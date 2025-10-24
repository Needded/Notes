package com.needded.notes.Repository;

import com.needded.notes.Entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {

    //Returns all notes from the current user.
    List<Note> findAllNotesByUserId(String userId);

    //Returns the note with the given "id" and associated with the "userId".
    Note findNoteByIdAndUserId (Long id, String userId);

    //Delete note with the given id and userId.
    void deleteByIdAndUserId (Long noteId, String userId);
}

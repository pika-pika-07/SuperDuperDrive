package com.example.SuperDuperDrive.services;


import com.example.SuperDuperDrive.mapper.NoteMapper;
import com.example.SuperDuperDrive.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes(Integer userid) {
        return noteMapper.getAllNotes(userid);
    }

    public void createOrEditNote(Note note) {
        if (note.getNoteId() == null) {
            noteMapper.insert(note);
        } else {
            noteMapper.update(note);
        }

    }


    public void deleteNote(Integer noteid, Integer userid) {
        noteMapper.delete(noteid, userid);
    }
}

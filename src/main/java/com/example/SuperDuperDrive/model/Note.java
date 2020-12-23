package com.example.SuperDuperDrive.model;

public class Note {

    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userId;

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String notetitle) {
        this.noteTitle = notetitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String notedescription) {
        this.noteDescription = notedescription;
    }

    public Integer getUserid() {
        return userId;
    }

    public void setUserid(Integer userid) {
        this.userId = userId;
    }
}

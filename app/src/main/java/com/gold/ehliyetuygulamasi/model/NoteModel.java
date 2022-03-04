package com.gold.ehliyetuygulamasi.model;

public class NoteModel {

    private String noteCategoryId, noteTitle, note, noteImage;

    public NoteModel() {

    }


    public NoteModel(String noteCategoryId, String noteTitle, String note, String noteImage) {
        this.noteTitle = noteTitle;
        this.note = note;
        this.noteCategoryId = noteCategoryId;
        this.noteImage = noteImage;

    }

    public String getNoteImage() {
        return noteImage;
    }

    public void setNoteImage(String noteImage) {
        this.noteImage = noteImage;
    }

    public String getNoteCategoryId() {
        return noteCategoryId;
    }

    public void setNoteCategoryId(String noteCategoryId) {
        this.noteCategoryId = noteCategoryId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }


    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

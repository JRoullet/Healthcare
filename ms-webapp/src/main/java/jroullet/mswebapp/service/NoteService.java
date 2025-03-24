//package jroullet.mswebapp.service;
//
//import jroullet.mswebapp.clients.NotesFeignClient;
//import jroullet.mswebapp.model.Note;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class NoteService {
//
//    @Autowired
//    NotesFeignClient notesFeignClient;
//
//    public List<Note> getNotesByPatientId(Long id) {
//        return notesFeignClient.getNotesByPatientId(id);
//    }
//
//    public Note getNoteById(Long id, String noteId) {
//        List<Note> notes = notesFeignClient.getNotesByPatientId(id);
//        return notes
//                .stream()
//                .filter(note -> noteId.equals(note.getId()))
//                .findFirst()
//                .orElseThrow(()-> new RuntimeException("Note not found with id : " + noteId));
//    }
//
//    public Note updateNote(String noteId, Note note) {
//        return notesFeignClient.updateNote(noteId, note).getBody();
//    }
//
//}

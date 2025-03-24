package jroullet.msnotes.mapper;

import jroullet.msnotes.dto.NoteDto;
import jroullet.msnotes.model.Note;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class NoteMapper {

    public Note toEntity(NoteDto noteDTO) {
        Note note = new Note();
        note.setContent(noteDTO.getContent());
        note.setPatientId(noteDTO.getPatientId());
        note.setCreationDate(LocalDate.now());
        return note;
    }

    public NoteDto toDTO(Note note) {
        NoteDto noteDTO = new NoteDto();
        noteDTO.setId(note.getId());
        noteDTO.setContent(note.getContent());
        noteDTO.setPatientId(note.getPatientId());
        noteDTO.setCreationDate(note.getCreationDate());
        return noteDTO;
    }
}

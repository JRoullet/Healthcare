package jroullet.mspatient.client;

import jroullet.mspatient.model.dto.NoteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient (name="ms-notes)")
public interface NotesFeignClient {

    @GetMapping("notes/patient/{patientId}")
    List<NoteDto> getNotesByPatientId(@PathVariable Long patientId);

    @PostMapping("/notes")
    NoteDto createNote(@RequestBody NoteDto noteDto);


}

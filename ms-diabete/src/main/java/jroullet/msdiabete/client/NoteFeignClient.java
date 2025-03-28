package jroullet.msdiabete.client;

import jroullet.msdiabete.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "ms-notes")
public interface NoteFeignClient {


    @GetMapping("/notes/patient/{patientId}/all")
    ResponseEntity<List<Note>> getNotesByPatientId(@PathVariable Long patientId);

}

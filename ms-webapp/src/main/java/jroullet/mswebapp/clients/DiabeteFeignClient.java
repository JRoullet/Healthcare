package jroullet.mswebapp.clients;

import jroullet.mswebapp.model.RiskLevel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient (name = "ms-diabete")
public interface DiabeteFeignClient {

    @GetMapping("/diabete/value/{patientId}")
    RiskLevel determineRiskLevel(@PathVariable Long patientId);
}

package net.local.poc.organization.service.adapters.http.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import net.local.poc.organization.service.application.domain.dto.EmployeeDTO;
import net.local.poc.organization.service.application.ports.outgoing.LoadEmployeePort;
import reactor.core.publisher.Flux;

@Component
public class EmployeeClient implements LoadEmployeePort {

    @Value(value = "${services.employee_url}")
    private String CLIENT_URL;

    private final WebClient webClient;

    public EmployeeClient(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl(CLIENT_URL).build();
	}

    @Override
    public List<EmployeeDTO> load(String organizationId) {
        Flux<EmployeeDTO> response = webClient.get()
                                              .uri("/organization/".concat(organizationId))
                                              .retrieve()
                                              .bodyToFlux(EmployeeDTO.class);
        return response.collectList().block();
    }
    
}

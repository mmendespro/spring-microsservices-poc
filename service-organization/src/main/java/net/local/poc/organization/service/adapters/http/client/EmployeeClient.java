package net.local.poc.organization.service.adapters.http.client;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import net.local.poc.organization.service.application.domain.dto.EmployeeDTO;
import net.local.poc.organization.service.application.ports.outgoing.LoadEmployeePort;
import reactor.core.publisher.Flux;

@Component
public class EmployeeClient implements LoadEmployeePort {

    private final WebClient webClient;
    private final String BASE_URL="http://service-employee-jvm:8080/v1/employees";

    public EmployeeClient(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
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

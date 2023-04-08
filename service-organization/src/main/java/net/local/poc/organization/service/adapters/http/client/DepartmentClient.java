package net.local.poc.organization.service.adapters.http.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import net.local.poc.organization.service.application.domain.dto.DepartmentDTO;
import net.local.poc.organization.service.application.ports.outgoing.LoadDepartmentPort;
import reactor.core.publisher.Flux;

@Component
public class DepartmentClient implements LoadDepartmentPort {

    @Value(value = "${services.department_url}")
    private String CLIENT_URL;

    private final WebClient webClient;

    public DepartmentClient(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl(CLIENT_URL).build();
	}


    @Override
    public List<DepartmentDTO> load(String organizationId) {
        Flux<DepartmentDTO> response = webClient.get()
                                              .uri("/organization/".concat(organizationId))
                                              .retrieve()
                                              .bodyToFlux(DepartmentDTO.class);
        return response.collectList().block();
    }
    
}

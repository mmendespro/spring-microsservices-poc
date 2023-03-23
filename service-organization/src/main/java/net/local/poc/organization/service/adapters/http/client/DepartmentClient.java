package net.local.poc.organization.service.adapters.http.client;

import java.util.List;

import org.springframework.web.reactive.function.client.WebClient;

import net.local.poc.organization.service.application.domain.dto.DepartmentDTO;
import net.local.poc.organization.service.application.ports.outgoing.LoadDepartmentPort;
import reactor.core.publisher.Flux;

public class DepartmentClient implements LoadDepartmentPort {

    private final WebClient webClient;
    private final String BASE_URL="http://service-department-jvm:8080/v1/departments";

    public DepartmentClient(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
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

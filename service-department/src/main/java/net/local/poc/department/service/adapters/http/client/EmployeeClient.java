package net.local.poc.department.service.adapters.http.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import net.local.poc.department.service.application.domain.dto.EmployeeDTO;
import net.local.poc.department.service.application.ports.outgoing.LoadDepartmentEmployeesPort;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class EmployeeClient implements LoadDepartmentEmployeesPort {

    @Value(value = "${services.employee_url}")
    private String CLIENT_URL;

    private final WebClient webClient;

    public EmployeeClient(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl(CLIENT_URL).build();
	}

    @Override
    public List<EmployeeDTO> load(String departmentId) {
        log.info("get employees from {} for departmentId {}", CLIENT_URL, departmentId);
        Flux<EmployeeDTO> response = webClient.get()
                                              .uri("/department/".concat(departmentId))
                                              .retrieve()
                                              .bodyToFlux(EmployeeDTO.class);
        return response.collectList().block();
    }
    
}

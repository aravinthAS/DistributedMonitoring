package rabbit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApplicationStatusChecker {

    public HttpStatus checkStatus(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response = restTemplate.getForEntity(url, null);
        return response.getStatusCode();
    }
}
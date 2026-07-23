package net.omprasad.Journal.service;


import net.omprasad.Journal.api.response.WeatherResponse;
import net.omprasad.Journal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather_api_key}")
    private String apiKey;

    private static final String API = "https://api.weatherstack.com/current?access_key=<ApiKey>&query=<city>";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String finalAPI = API.replace("<ApiKey>", apiKey).replace("<city>", city);

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);

        return response.getBody();
    }


    public WeatherResponse postReqWithHeadersAndBodyExample(String city) {
        String finalAPI = API.replace("<ApiKey>", apiKey).replace("<city>", city);

        // Header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("key", "value");

        //body
        User user = User.builder().userName("om").password("om").build();

        //http Entity
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);
        //                                            body    header

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, httpEntity, WeatherResponse.class);

        return response.getBody();
    }
}
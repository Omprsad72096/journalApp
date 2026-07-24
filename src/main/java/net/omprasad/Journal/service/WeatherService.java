package net.omprasad.Journal.service;


import net.omprasad.Journal.api.response.WeatherResponse;
import net.omprasad.Journal.cache.AppCache;
import net.omprasad.Journal.constants.PlaceHolders;
import net.omprasad.Journal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather_api_key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String finalAPI = appCache.getCache().get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolders.API_KEY, apiKey).replace(PlaceHolders.CITY, city);

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);

        return response.getBody();
    }


    public WeatherResponse postReqWithHeadersAndBodyExample(String city) {
        String finalAPI = appCache.getCache().get("weather_api").replace("<ApiKey>", apiKey).replace("<city>", city);

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
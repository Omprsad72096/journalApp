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

import java.util.List;

@Service
public class WeatherService {

    @Value("${weather_api_key}")
    private String apiKey;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("Weather_of_"+ city, WeatherResponse.class);
        if(weatherResponse!=null) {
            return weatherResponse;
        }


        String finalAPI = appCache.getCache().get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolders.API_KEY, apiKey).replace(PlaceHolders.CITY, city);
//        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
//        WeatherResponse body = response.getBody();

        //manually creating body so that i dont waste api tokens while debugging
        WeatherResponse body = new WeatherResponse();
        WeatherResponse.Current current = new WeatherResponse.Current();
        current.setTemperature(28);
        current.setFeelslike(31);
        current.setWeatherDescriptions(List.of("Light rain shower", "Humid"));
        body.setCurrent(current);

        if(body!=null) {
            redisService.set("Weather_of_"+city, body, 3600l);
        }

        return body;
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
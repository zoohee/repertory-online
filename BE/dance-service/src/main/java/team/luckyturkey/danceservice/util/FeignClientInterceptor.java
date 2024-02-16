package team.luckyturkey.danceservice.util;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import team.luckyturkey.danceservice.service.TokenService;

@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

    private final TokenService tokenService;

    @Override
    public void apply(RequestTemplate template) {
        String token = tokenService.getToken();
        if (token != null) {
            template.header("Authorization", "Bearer " + token);
        }
    }
}

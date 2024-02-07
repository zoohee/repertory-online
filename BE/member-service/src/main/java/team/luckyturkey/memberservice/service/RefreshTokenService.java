package team.luckyturkey.memberservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RefreshTokenService {

//    private final RefreshTokenRepository refreshTokenRepository;
//
//    @Transactional
//    public void saveTokenInfo(String email, String refreshToken, String accessToken) {
//
//        RefreshToken refreshToken1 = new RefreshToken(email, refreshToken, accessToken);
//
//        refreshTokenRepository.save(refreshToken1);
//    }
//
//    @Transactional
//    public void removeRefreshToken(String accessToken) {
//        RefreshToken token = refreshTokenRepository.findByAccessToken(accessToken)
//                .orElseThrow(IllegalArgumentException::new);
//
//        refreshTokenRepository.delete(token);
//    }
}
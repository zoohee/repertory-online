package team.luckyturkey.memberservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.memberservice.auth.jwt.RefreshToken;
import team.luckyturkey.memberservice.member.repository.RefreshTokenRepository;


@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional //토큰을 redis에 저장
    public void saveTokenInfo(String memberName, String refreshToken, String accessToken) {

        RefreshToken refreshToken1 = new RefreshToken(memberName, refreshToken, accessToken);

        refreshTokenRepository.save(refreshToken1);
    }

    @Transactional //토큰을 삭제 (로그아웃등)
    public void removeRefreshToken(String accessToken) {
        RefreshToken token = refreshTokenRepository.findByAccessToken(accessToken)
                .orElseThrow(IllegalArgumentException::new);

        refreshTokenRepository.delete(token);
    }
}
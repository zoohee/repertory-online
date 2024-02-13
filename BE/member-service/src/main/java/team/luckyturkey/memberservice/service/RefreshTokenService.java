package team.luckyturkey.memberservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.luckyturkey.memberservice.auth.jwt.JWTUtil;
import team.luckyturkey.memberservice.auth.jwt.RefreshToken;
import team.luckyturkey.memberservice.member.repository.RefreshTokenRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RefreshTokenService {
//주석
    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTUtil jwtUtil;

    @Transactional //토큰을 redis에 저장
    public void saveTokenInfo(String memberLoginId, String refreshToken, String accessToken) {

        RefreshToken token = new RefreshToken(memberLoginId, refreshToken, accessToken);

        refreshTokenRepository.save(token);
    }

    @Transactional
    public boolean verifyAccessToken(String accessToken){
        return jwtUtil.verifyToken(accessToken);
    }

//    @Transactional //토큰을 삭제 (로그아웃등)
//    public void removeRefreshToken(String accessToken) {
//
//        //todo: 나중에 리팩토링 하기 👩‍❤️‍💋‍👩
//        // access token을 이용해서 refresh토큰 객체를 찾아온다
//
//        String id = refreshTokenRepository.findIdByAccessToken(accessToken)
//                .orElseThrow(RuntimeException::new);
//
//        RefreshToken refreshToken = refreshTokenRepository.findById(id)
//                .orElseThrow(RuntimeException::new);
//
//        //찾아온 객체를 이용해 삭제 요청을 보낸다.
//        refreshTokenRepository.delete(refreshToken);
//    }
//
//    public RefreshToken findToken(String accessToken){
//        String id = refreshTokenRepository.findIdByAccessToken(accessToken)
//                .orElseThrow(RuntimeException::new);
//
//        return refreshTokenRepository.findById(id)
//                .orElseThrow(RuntimeException::new);
//    }

    public RefreshToken findToken(String accessToken){
        return refreshTokenRepository.findByAccessToken(accessToken)
                .orElseThrow(RuntimeException::new);
    }

    public void deleteRefreshToken(String memberId){
        refreshTokenRepository.deleteById(memberId);
    }

}
package team.luckyturkey.memberservice.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "oauth2_authorized_client")
public class OAuth2AuthorizedClient {

    @Id
    @Column(name = "client_registration_id", length = 100, nullable = false)
    private String clientRegistrationId;

    @Id
    @Column(name = "principal_name", length = 200, nullable = false)
    private String principalName;

    @Column(name = "access_token_type", length = 100, nullable = false)
    private String accessTokenType;

    @Lob
    @Column(name = "access_token_value", nullable = false)
    private byte[] accessTokenValue;

    @Column(name = "access_token_issued_at", nullable = false)
    private Timestamp accessTokenIssuedAt;

    @Column(name = "access_token_expires_at", nullable = false)
    private Timestamp accessTokenExpiresAt;

    @Column(name = "access_token_scopes", length = 1000)
    private String accessTokenScopes;

    @Lob
    @Column(name = "refresh_token_value")
    private byte[] refreshTokenValue;

    @Column(name = "refresh_token_issued_at")
    private Timestamp refreshTokenIssuedAt;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    // 생성자, getter, setter 등 필요한 메서드를 추가할 수 있습니다.
}

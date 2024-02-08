package team.luckyturkey.communityservice.util;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEncryptableProperties
public class JasyptConfigAES {

//    @Value("${jasypt.encryptor.password}")
    private String password;

//    @Bean("jasyptEncryptorAES")
    public StringEncryptor stringEncryptor(@Value(value = "${jasypt.secret-key}") String secretKey) {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword(secretKey); // 암호화키
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256"); // 알고리즘
        config.setKeyObtentionIterations("1000"); // 반복할 해싱 회수
        config.setPoolSize("1"); // 인스턴스 pool
        config.setProviderName("zuhee");
        // TODO: salt 관리
//        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator"); // salt 생성 클래스
//        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64"); //인코딩 방식
        encryptor.setConfig(config);
        return encryptor;
    }
}
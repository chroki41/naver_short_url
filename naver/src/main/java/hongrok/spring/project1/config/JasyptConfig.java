package hongrok.spring.project1.config;


import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        String password = "my_project1";
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);       //암호화에 사용하는 키
        config.setAlgorithm("PBEWithMD5AndDES");    // 암호화 알고리즘
        config.setKeyObtentionIterations("1000");   // 반복할 hashing 횟수
        config.setPoolSize("1");                    // 인스턴스 pool
        config.setProviderName("SunJCE");
        // salt 생성 클래스 -> 랜덤으로 하기때문에 암호화를 할 때마다 값이 달라짐
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64"); //인코딩 방식

        encryptor.setConfig(config);
        return encryptor;
    }
}

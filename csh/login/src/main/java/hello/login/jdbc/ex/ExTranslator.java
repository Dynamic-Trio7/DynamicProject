package hello.login.jdbc.ex;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
public class ExTranslator {

    private final DataSource dataSource;

    @Bean
    SQLExceptionTranslator sqlExceptionTranslator(){
        return new SQLErrorCodeSQLExceptionTranslator(dataSource);
    }

}

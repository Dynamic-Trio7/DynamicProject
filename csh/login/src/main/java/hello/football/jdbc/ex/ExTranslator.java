package hello.football.jdbc.ex;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

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

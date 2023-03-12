package com.mpi.latushkina.exam.server.configurations;

import com.mpi.latushkina.exam.server.repository.IDataRepository;
import com.mpi.latushkina.exam.server.repository.SimpleDataRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataRepositoryConfig {
//    @Bean
//    @ConditionalOnProperty(name = "data.repository.type", havingValue = "db")
//    @Autowired
//    public IDataRepository dbDataRepository(EntityManager entityManager) {
//        return new DBDataRepository(entityManager);
//    }

    @Bean
    @ConditionalOnProperty(name = "data.repository.type", havingValue = "simple", matchIfMissing = true)
    public IDataRepository simpleDataRepository() {
        return new SimpleDataRepository();
    }
}

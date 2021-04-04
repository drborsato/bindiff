package br.com.drborsato.bindiff.configuration;

import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.FileId;
import br.com.drborsato.bindiff.model.Side;
import br.com.drborsato.bindiff.repository.BinFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(BinFileRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new BinFile(new FileId(1l, Side.LEFT),
                    Base64.getEncoder().encodeToString("abc".getBytes(StandardCharsets.UTF_8.toString())))));
            log.info("Preloading " + repository.save(new BinFile(new FileId(1l, Side.RIGHT),
                    Base64.getEncoder().encodeToString("abc".getBytes(StandardCharsets.UTF_8.toString())))));
        };
    }
}

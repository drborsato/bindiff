package br.com.drborsato.bindiff.service;

import br.com.drborsato.bindiff.repository.BinFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BinDiffServiceImplTest {

    @InjectMocks
    private BinDiffServiceImpl service;

    @Mock
    private BinFileRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getDiffEqual() {
    }

    @Test
    void getDiffDifferentSize() {
    }

    @Test
    void getDiff() {
    }

    @Test
    void getDiffFileNotFound() {
    }

}
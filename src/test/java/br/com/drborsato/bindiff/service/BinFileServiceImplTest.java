package br.com.drborsato.bindiff.service;

import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.FileId;
import br.com.drborsato.bindiff.model.Side;
import br.com.drborsato.bindiff.repository.BinFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BinFileServiceImplTest {

    @InjectMocks
    private BinFileServiceImpl service;
    @Mock
    private BinFileRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getBinFile() throws Exception {
        // Given
        long id = 1l;
        Side side = Side.LEFT;
        FileId fileId = new FileId(id, side);
        String data = Base64.getEncoder().encodeToString("abc".getBytes(StandardCharsets.UTF_8.toString()));
        BinFile expectedFile = new BinFile(fileId, data);
        when(repository.findById(fileId)).thenReturn(java.util.Optional.of(expectedFile));

        // When
        Optional<BinFile> actualFile = service.getBinFile(id, side);

        // Then
        assertEquals(actualFile, expectedFile);
    }

    @Test
    void getBinFileNotFound() throws Exception {
    }

    @Test
    void getBinFileException() throws Exception {
    }

    @Test
    void createBinFile() throws Exception {
    }

    @Test
    void createBinFileAlreadyExist() throws Exception {
    }

    @Test
    void createBinFileException() throws Exception {
    }

    @Test
    void updateBinFile() throws Exception {
    }

    @Test
    void updateBinFileNotFound() throws Exception {
    }

    @Test
    void updateBinFileException() throws Exception {
    }

    @Test
    void deleteBinFile() throws Exception {
    }

    @Test
    void deleteBinFileNotFound() throws Exception {
    }

    @Test
    void deleteBinFileException() throws Exception {
    }

}
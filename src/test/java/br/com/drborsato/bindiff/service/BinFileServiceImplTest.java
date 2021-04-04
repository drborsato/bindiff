package br.com.drborsato.bindiff.service;

import br.com.drborsato.bindiff.exception.BinDataConverterException;
import br.com.drborsato.bindiff.exception.BinFileConflictException;
import br.com.drborsato.bindiff.exception.BinFileNotFoundException;
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

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
        when(repository.findById(any(FileId.class))).thenReturn(java.util.Optional.of(expectedFile));

        // When
        BinFile actualFile = service.getBinFile(id, side);

        // Then
        assertEquals(actualFile, expectedFile);
    }

    @Test
    void getBinFileNotFound() {
        // Given
        long id = 1l;
        Side side = Side.LEFT;
        when(repository.findById(any(FileId.class))).thenReturn(java.util.Optional.empty());

        // When
        // Then
        assertThrows(BinFileNotFoundException.class, () -> {
            service.getBinFile(id, side);
        });
    }

    @Test
    void createBinFile() throws Exception {
        // Given
        long id = 1l;
        Side side = Side.LEFT;
        FileId fileId = new FileId(id, side);
        String data = Base64.getEncoder().encodeToString("abc".getBytes(StandardCharsets.UTF_8.toString()));
        BinFile expectedFile = new BinFile(fileId, data);
        when(repository.existsById(any(FileId.class))).thenReturn(false);
        when(repository.save(any(BinFile.class))).thenReturn(expectedFile);

        // When
        BinFile actualFile = service.createBinFile(id, side, data);

        // Then
        assertEquals(actualFile, expectedFile);
    }

    @Test
    void createBinFileAlreadyExist() throws Exception {
        // Given
        long id = 1l;
        Side side = Side.LEFT;
        String data = Base64.getEncoder().encodeToString("abc".getBytes(StandardCharsets.UTF_8.toString()));
        when(repository.existsById(any(FileId.class))).thenReturn(true);

        // When
        // Then
        assertThrows(BinFileConflictException.class, () -> {
            service.createBinFile(id, side, data);
        });
    }

    @Test
    void createBinFileInvalidData() {
        // Given
        long id = 1l;
        Side side = Side.LEFT;
        String invalidData = "abc;123";
        when(repository.existsById(any(FileId.class))).thenReturn(true);

        // When
        // Then
        assertThrows(BinDataConverterException.class, () -> {
            service.createBinFile(id, side, invalidData);
        });
    }

    @Test
    void updateBinFile() throws Exception {
        // Given
        long id = 1l;
        Side side = Side.LEFT;
        FileId fileId = new FileId(id, side);
        String data = Base64.getEncoder().encodeToString("abc".getBytes(StandardCharsets.UTF_8.toString()));
        BinFile expectedFile = new BinFile(fileId, data);
        when(repository.existsById(any(FileId.class))).thenReturn(true);
        when(repository.save(any(BinFile.class))).thenReturn(expectedFile);

        // When
        BinFile actualFile = service.updateBinFile(id, side, data);

        // Then
        assertEquals(actualFile, expectedFile);
    }

    @Test
    void updateBinFileNotFound() throws Exception {
        // Given
        long id = 1l;
        Side side = Side.LEFT;
        String data = Base64.getEncoder().encodeToString("abc".getBytes(StandardCharsets.UTF_8.toString()));
        when(repository.existsById(any(FileId.class))).thenReturn(false);

        // When
        // Then
        assertThrows(BinFileNotFoundException.class, () -> {
            service.updateBinFile(id, side, data);
        });
    }

    @Test
    void updateBinFileInvalidData() {
        // Given
        long id = 1l;
        Side side = Side.LEFT;
        String data = "abc;123";
        when(repository.existsById(any(FileId.class))).thenReturn(false);

        // When
        // Then
        assertThrows(BinDataConverterException.class, () -> {
            service.updateBinFile(id, side, data);
        });
    }

    @Test
    void deleteBinFile() throws Exception {
        // Given
        long id = 1l;
        Side side = Side.LEFT;
        FileId fileId = new FileId(id, side);
        String data = Base64.getEncoder().encodeToString("abc".getBytes(StandardCharsets.UTF_8.toString()));
        BinFile expectedFile = new BinFile(fileId, data);
        when(repository.existsById(any(FileId.class))).thenReturn(true);

        // When
        service.deleteBinFile(id, side);
        // Then no exception
    }

    @Test
    void deleteBinFileNotFound() {
        // Given
        long id = 1l;
        Side side = Side.LEFT;
        when(repository.existsById(any(FileId.class))).thenReturn(false);

        // When
        // Then
        assertThrows(BinFileNotFoundException.class, () -> {
            service.deleteBinFile(id, side);
        });
    }

}
package br.com.drborsato.bindiff.service;

import br.com.drborsato.bindiff.exception.BinFileNotFoundException;
import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.Diff;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    void getDiffEqual() throws Exception {
        // Given
        long id = 1l;
        Side side = Side.LEFT;
        FileId leftFileId = new FileId(id, side);
        String data = Base64.getEncoder().encodeToString("abc".getBytes(StandardCharsets.UTF_8.toString()));
        BinFile leftFile = new BinFile(leftFileId, data);
        side = Side.RIGHT;
        FileId rightFileId = new FileId(id, side);
        BinFile rightFile = new BinFile(rightFileId, data);
        when(repository.findById(any(FileId.class))).
                thenReturn(java.util.Optional.of(leftFile)).
                thenReturn(java.util.Optional.of(rightFile));

        // When
        Diff diff = service.getDiff(id);

        // Then
        assertTrue(diff.isEqual());
        assertNotNull(diff.getOffsetDiff());
        assertEquals(0, diff.getOffsetDiff().size());
    }

    @Test
    void getDiffDifferentSize() throws Exception {
        // Given
        long id = 1l;
        Side side = Side.LEFT;
        FileId leftFileId = new FileId(id, side);
        String leftData = Base64.getEncoder().encodeToString("abc".getBytes(StandardCharsets.UTF_8.toString()));
        BinFile leftFile = new BinFile(leftFileId, leftData);
        side = Side.RIGHT;
        FileId rightFileId = new FileId(id, side);
        String rightData = Base64.getEncoder().encodeToString("abcd".getBytes(StandardCharsets.UTF_8.toString()));
        BinFile rightFile = new BinFile(rightFileId, rightData);
        when(repository.findById(any(FileId.class))).
                thenReturn(java.util.Optional.of(leftFile)).
                thenReturn(java.util.Optional.of(rightFile));

        // When
        Diff diff = service.getDiff(id);

        // Then
        assertFalse(diff.isEqual());
        assertNotNull(diff.getOffsetDiff());
        assertEquals(0, diff.getOffsetDiff().size());
    }

    @Test
    void getDiff() throws Exception {
        // Given
        long id = 1l;
        Side side = Side.LEFT;
        FileId leftFileId = new FileId(id, side);
        String leftData = Base64.getEncoder().encodeToString("abc".getBytes(StandardCharsets.UTF_8.toString()));
        BinFile leftFile = new BinFile(leftFileId, leftData);
        side = Side.RIGHT;
        FileId rightFileId = new FileId(id, side);
        String rightData = Base64.getEncoder().encodeToString("cba".getBytes(StandardCharsets.UTF_8.toString()));
        BinFile rightFile = new BinFile(rightFileId, rightData);
        when(repository.findById(any(FileId.class))).
                thenReturn(java.util.Optional.of(leftFile)).
                thenReturn(java.util.Optional.of(rightFile));

        // When
        Diff diff = service.getDiff(id);

        // Then
        assertFalse(diff.isEqual());
        assertNotNull(diff.getOffsetDiff());
        assertEquals(3, diff.getOffsetDiff().size());
    }

    @Test
    void getDiffFileNotFound() {
        // Given
        long id = 1l;
        when(repository.findById(any(FileId.class))).thenReturn(java.util.Optional.empty());

        // When
        // Then
        assertThrows(BinFileNotFoundException.class, () -> {
            service.getDiff(id);
        });
    }

}
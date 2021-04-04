package br.com.drborsato.bindiff.service;

import br.com.drborsato.bindiff.exception.BinDataConverterException;
import br.com.drborsato.bindiff.exception.BinFileNotFoundException;
import br.com.drborsato.bindiff.exception.DiffFileNotFoundException;
import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.Diff;
import br.com.drborsato.bindiff.model.FileId;
import br.com.drborsato.bindiff.model.Side;
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
import static org.mockito.Mockito.when;

class BinDiffServiceImplTest {

    @InjectMocks
    private BinDiffServiceImpl service;

    @Mock
    private BinFileService binFileService;

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
        when(binFileService.getBinFile(id, Side.LEFT)).thenReturn(leftFile);
        when(binFileService.getBinFile(id, Side.RIGHT)).thenReturn(rightFile);

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
        when(binFileService.getBinFile(id, Side.LEFT)).thenReturn(leftFile);
        when(binFileService.getBinFile(id, Side.RIGHT)).thenReturn(rightFile);

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
        when(binFileService.getBinFile(id, Side.LEFT)).thenReturn(leftFile);
        when(binFileService.getBinFile(id, Side.RIGHT)).thenReturn(rightFile);

        // When
        Diff diff = service.getDiff(id);

        // Then
        assertFalse(diff.isEqual());
        assertNotNull(diff.getOffsetDiff());
        assertEquals(2, diff.getOffsetDiff().size());
    }

    @Test
    void getDiffFileNotFound() {
        // Given
        long id = 1l;
        when(binFileService.getBinFile(id, Side.LEFT)).thenThrow(BinFileNotFoundException.class);

        // When
        // Then
        assertThrows(DiffFileNotFoundException.class, () -> {
            service.getDiff(id);
        });
    }

    @Test
    void getDiffFileInvalidData() {
        // Given
        long id = 1l;
        Side side = Side.LEFT;
        String invalidData = "abc;123";
        FileId fileId = new FileId(id, side);
        BinFile file = new BinFile(fileId, invalidData);
        when(binFileService.getBinFile(id, Side.LEFT)).thenReturn(file);

        // When
        // Then
        assertThrows(BinDataConverterException.class, () -> {
            service.getDiff(id);
        });
    }

}
package br.com.drborsato.bindiff.service;

import br.com.drborsato.bindiff.exception.BinDataConverterException;
import br.com.drborsato.bindiff.exception.BinFileNotFoundException;
import br.com.drborsato.bindiff.exception.DiffFileNotFoundException;
import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.Diff;
import br.com.drborsato.bindiff.model.Offset;
import br.com.drborsato.bindiff.model.Side;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Service
public class BinDiffServiceImpl implements BinDiffService {

    private static final Logger log = LoggerFactory.getLogger(BinFileServiceImpl.class);

    private BinFileService binFileService;

    @Autowired
    public BinDiffServiceImpl(BinFileService binFileService) {
        this.binFileService = binFileService;
    }

    @Override
    public Diff getDiff(long id) {
        BinFile leftFile = fetchFile(id, Side.LEFT);
        BinFile rightFile = fetchFile(id, Side.RIGHT);

        byte[] leftData = fetchData(leftFile);
        byte[] rightData = fetchData(rightFile);

        return compareData(leftData, rightData);
    }

    private BinFile fetchFile(long id, Side side) {
        try {
            return binFileService.getBinFile(id, side);
        } catch (BinFileNotFoundException e) {
            log.info("File not found, impossible to compare");
            throw new DiffFileNotFoundException(e.getMessage());
        }
    }

    private byte[] fetchData(BinFile file) {
        try {
            return Base64.getDecoder().decode(file.getData());
        } catch (IllegalArgumentException e) {
            log.error(String.format("Illegal data at position %o %s", file.getFileId().getId(),
                    file.getFileId().getSide()));
            throw new BinDataConverterException(file.getData());
        }
    }

    private Diff compareData(byte[] leftData, byte[] rightData) {

        if (leftData.length != rightData.length) {
            return new Diff(false, Collections.emptyList());
        }

        boolean isEqual = true;
        List<Offset> offsets = new ArrayList<>();
        for (int i = 0; i < leftData.length; i++) {
            if (leftData[i] != rightData[i]) {
                isEqual = false;
                Offset offset = new Offset(i, leftData[i], rightData[i]);
                offsets.add(offset);
            }
        }

        return new Diff(isEqual, offsets);
    }
}

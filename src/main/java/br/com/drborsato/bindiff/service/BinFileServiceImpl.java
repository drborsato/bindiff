package br.com.drborsato.bindiff.service;

import br.com.drborsato.bindiff.exception.BinDataConverterException;
import br.com.drborsato.bindiff.exception.BinFileConflictException;
import br.com.drborsato.bindiff.exception.BinFileNotFoundException;
import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.FileId;
import br.com.drborsato.bindiff.model.Side;
import br.com.drborsato.bindiff.repository.BinFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class BinFileServiceImpl implements BinFileService {

    private static final Logger log = LoggerFactory.getLogger(BinFileServiceImpl.class);

    private BinFileRepository repository;

    @Autowired
    public BinFileServiceImpl(BinFileRepository repository) {
        this.repository = repository;
    }

    @Override
    public BinFile getBinFile(long id, Side side) {
        FileId fileId = new FileId(id, side);
        Optional<BinFile> file = repository.findById(fileId);

        if (file.isPresent()) {
            return file.get();
        }

        log.info("File not found, impossible to fetch");
        throw new BinFileNotFoundException(fileId);
    }

    @Override
    public BinFile createBinFile(long id, Side side, String data) {
        verifyDataIsBase64(data);

        FileId fileId = new FileId(id, side);

        if (repository.existsById(fileId)) {
            log.info("File already exist, impossible to create");
            throw new BinFileConflictException(fileId);
        }

        BinFile binFile = new BinFile(fileId, data);
        return repository.save(binFile);
    }

    @Override
    public BinFile updateBinFile(long id, Side side, String data) {
        verifyDataIsBase64(data);

        FileId fileId = new FileId(id, side);

        if (repository.existsById(fileId)) {
            BinFile binFile = new BinFile(fileId, data);
            return repository.save(binFile);
        }

        log.info("File not found, impossible to update");
        throw new BinFileNotFoundException(fileId);
    }

    @Override
    public void deleteBinFile(long id, Side side) {
        FileId fileId = new FileId(id, side);

        if (!repository.existsById(fileId)) {
            log.info("File not found, impossible to delete");
            throw new BinFileNotFoundException(fileId);
        }

        repository.deleteById(fileId);
    }

    private void verifyDataIsBase64(String data) {
        try {
            Base64.getDecoder().decode(data);
        } catch (IllegalArgumentException e) {
            throw new BinDataConverterException(data);
        }
    }

}

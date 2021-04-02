package br.com.drborsato.bindiff.service;

import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.FileId;
import br.com.drborsato.bindiff.model.Side;
import br.com.drborsato.bindiff.repository.BinFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BinFileServiceImpl implements BinFileService {

    private BinFileRepository repository;

    @Autowired
    public BinFileServiceImpl(BinFileRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<BinFile> getBinFile(long id, Side side) throws Exception {
        FileId fileId = new FileId(id, side);
        return repository.findById(fileId);
    }

    @Override
    public BinFile createBinFile(long id, Side side, String data) throws Exception {
        FileId fileId = new FileId(id, side);
        BinFile binFile = new BinFile(fileId, data);
        return repository.save(binFile);
    }

    @Override
    public BinFile updateBinFile(long id, Side side, String data) throws Exception {
        FileId fileId = new FileId(id, side);

        if (repository.existsById(fileId)) {
            BinFile binFile = new BinFile(fileId, data);
            return repository.save(binFile);
        }

        return null;
    }

    @Override
    public void deleteBinFile(long id, Side side) throws Exception {
        FileId fileId = new FileId(id, side);
        repository.deleteById(fileId);
    }

}

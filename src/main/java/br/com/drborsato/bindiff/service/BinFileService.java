package br.com.drborsato.bindiff.service;

import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.Side;

import java.util.Optional;

public interface BinFileService {
    Optional<BinFile> getBinFile(long id, Side side) throws Exception;
    BinFile createBinFile(long id, Side side, String data) throws Exception;
    BinFile updateBinFile(long id, Side side, String data) throws Exception;
    void deleteBinFile(long id, Side side) throws Exception;
}

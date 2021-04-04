package br.com.drborsato.bindiff.service;

import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.Side;

public interface BinFileService {
    BinFile getBinFile(long id, Side side);

    BinFile createBinFile(long id, Side side, String data);

    BinFile updateBinFile(long id, Side side, String data);

    void deleteBinFile(long id, Side side);
}

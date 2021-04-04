package br.com.drborsato.bindiff.controller;

import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.Diff;
import br.com.drborsato.bindiff.model.Side;
import br.com.drborsato.bindiff.service.BinDiffService;
import br.com.drborsato.bindiff.service.BinFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BinDiffController implements BinDiffOperations {

    private BinFileService fileService;
    private BinDiffService diffService;

    @Autowired
    public BinDiffController(BinFileService fileService, BinDiffService diffService) {
        this.fileService = fileService;
        this.diffService = diffService;
    }

    @Override
    public BinFile getBinFile(long id, Side side) {
        return fileService.getBinFile(id, side);
    }

    @Override
    public BinFile createBinFile(long id, Side side, String data) {
        return fileService.createBinFile(id, side, data);
    }

    @Override
    public BinFile updateBinFile(long id, Side side, String data) {
        return fileService.updateBinFile(id, side, data);
    }

    @Override
    public void deleteBinFile(long id, Side side) {
        fileService.deleteBinFile(id, side);
    }

    @Override
    public Diff getDiff(@PathVariable long id) {
        return diffService.getDiff(id);
    }
}

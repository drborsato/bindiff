package br.com.drborsato.bindiff.controller;

import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.Diff;
import br.com.drborsato.bindiff.model.Side;
import br.com.drborsato.bindiff.service.BinDiffService;
import br.com.drborsato.bindiff.service.BinFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
    public Optional<BinFile> getBinFile(long id, String side) throws Exception {
        Side s = Side.valueOf(side.toUpperCase());
        return fileService.getBinFile(id, s);
    }

    @Override
    public BinFile createBinFile(long id, String side, String data) throws Exception {
        Side s = Side.valueOf(side.toUpperCase());
        return fileService.createBinFile(id, s, data);
    }

    @Override
    public BinFile updateBinFile(long id, String side, String data) throws Exception {
        Side s = Side.valueOf(side.toUpperCase());
        return fileService.updateBinFile(id, s, data);
    }

    @Override
    public void deleteBinFile(long id, String side) throws Exception {
        Side s = Side.valueOf(side.toUpperCase());
        fileService.deleteBinFile(id, s);
    }

    @Override
    public Diff getDiff(@PathVariable long id) throws Exception {
        return diffService.getDiff(id);
    }
}

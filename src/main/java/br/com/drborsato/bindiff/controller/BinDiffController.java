package br.com.drborsato.bindiff.controller;

import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.Diff;
import br.com.drborsato.bindiff.model.RequestData;
import br.com.drborsato.bindiff.model.Side;
import br.com.drborsato.bindiff.service.BinDiffService;
import br.com.drborsato.bindiff.service.BinFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/diff/{id}")
public class BinDiffController{

    private BinFileService fileService;
    private BinDiffService diffService;

    @Autowired
    public BinDiffController(BinFileService fileService, BinDiffService diffService) {
        this.fileService = fileService;
        this.diffService = diffService;
    }

    @GetMapping("/{side}")
    @ResponseStatus(code = HttpStatus.OK)
    public BinFile getBinFile(@PathVariable long id, @PathVariable Side side) {
        return fileService.getBinFile(id, side);
    }

    @PostMapping("/{side}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BinFile createBinFile(@PathVariable long id, @PathVariable Side side, @RequestBody RequestData data) {
        return fileService.createBinFile(id, side, data.getData());
    }

    @PutMapping("/{side}")
    @ResponseStatus(code = HttpStatus.OK)
    public BinFile updateBinFile(@PathVariable long id, @PathVariable Side side, @RequestBody RequestData data) {
        return fileService.updateBinFile(id, side, data.getData());
    }

    @DeleteMapping("/{side}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteBinFile(@PathVariable long id, @PathVariable Side side) {
        fileService.deleteBinFile(id, side);
    }

    @GetMapping("/")
    @ResponseStatus(code = HttpStatus.OK)
    public Diff getDiff(@PathVariable long id) {
        return diffService.getDiff(id);
    }
}

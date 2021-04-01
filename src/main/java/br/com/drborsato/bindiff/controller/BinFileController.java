package br.com.drborsato.bindiff.controller;

import br.com.drborsato.bindiff.exception.BinFileNotFoundException;
import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.FileId;
import br.com.drborsato.bindiff.model.Side;
import br.com.drborsato.bindiff.repository.BinFileRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BinFileController {

    private final BinFileRepository repository;

    public BinFileController(BinFileRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/v1/diff/{id}/left")
    BinFile getLeftBinFile(@PathVariable long id) {
        FileId fileId = new FileId(id, Side.LEFT);
        return repository.findById(fileId).orElseThrow(() -> new BinFileNotFoundException(fileId));
    }

    @GetMapping("/v1/diff/{id}/right")
    BinFile getRightBinFile(@PathVariable long id) {
        FileId fileId = new FileId(id, Side.RIGHT);
        return repository.findById(fileId).orElseThrow(() -> new BinFileNotFoundException(fileId));
    }
}

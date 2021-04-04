package br.com.drborsato.bindiff.controller;

import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.Diff;
import br.com.drborsato.bindiff.model.Side;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/diff/{id}")
public interface BinDiffOperations {

    @GetMapping("/{side}")
    BinFile getBinFile(@PathVariable long id, @PathVariable Side side);

    @PostMapping("/{side}")
    BinFile createBinFile(@PathVariable long id, @PathVariable Side side, @RequestBody String data);

    @PutMapping("/{side}")
    BinFile updateBinFile(@PathVariable long id, @PathVariable Side side, @RequestBody String data);

    @DeleteMapping("/{side}")
    void deleteBinFile(@PathVariable long id, @PathVariable Side side);

    @GetMapping("/")
    Diff getDiff(@PathVariable long id);

}

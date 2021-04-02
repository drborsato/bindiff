package br.com.drborsato.bindiff.controller;

import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.Diff;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequestMapping("/v1/diff/{id}")
public interface BinDiffOperations {

    @GetMapping("/{side}")
    Optional<BinFile> getBinFile(@PathVariable long id, @PathVariable String side) throws Exception;

    @PostMapping("/{side}")
    BinFile createBinFile(@PathVariable long id, @PathVariable String side, @RequestBody String data) throws Exception;

    @PutMapping("/{side}")
    BinFile updateBinFile(@PathVariable long id, @PathVariable String side, @RequestBody String data) throws Exception;

    @DeleteMapping("/{side}")
    void deleteBinFile(@PathVariable long id, @PathVariable String side) throws Exception;

    @GetMapping("/")
    Diff getDiff(@PathVariable long id) throws Exception;

}

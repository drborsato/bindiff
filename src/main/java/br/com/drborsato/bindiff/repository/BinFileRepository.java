package br.com.drborsato.bindiff.repository;

import br.com.drborsato.bindiff.model.BinFile;
import br.com.drborsato.bindiff.model.FileId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinFileRepository extends JpaRepository<BinFile, FileId> {
}

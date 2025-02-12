package br.com.fiap.api_rest.controller;

import br.com.fiap.api_rest.model.Livro;
import br.com.fiap.api_rest.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    // CRUD == POST, GET, PUT, DELETE

    // Criar um novo livro
    @PostMapping
    public ResponseEntity<Livro> createLivro(@RequestBody Livro livro) {
        Livro livroSalvo = livroRepository.save(livro);
        return new ResponseEntity<>(livroSalvo, HttpStatus.CREATED);
    }

    // Listar todos os livros
    @GetMapping
    public ResponseEntity<List<Livro>> readLivros() {
        List<Livro> livros = livroRepository.findAll();
        return new ResponseEntity<>(livros, HttpStatus.OK);
    }

    // Buscar livro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Livro> readLivro(@PathVariable Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Atualizar livro existente
    @PutMapping("/{id}")
    public ResponseEntity<Livro> updateLivro(@PathVariable Long id, @RequestBody Livro livroAtualizado) {
        if (!livroRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        livroAtualizado.setId(id); // Garante que o ID seja mantido
        Livro livroSalvo = livroRepository.save(livroAtualizado);
        return new ResponseEntity<>(livroSalvo, HttpStatus.OK);
    }

    // Excluir livro por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        if (!livroRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        livroRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
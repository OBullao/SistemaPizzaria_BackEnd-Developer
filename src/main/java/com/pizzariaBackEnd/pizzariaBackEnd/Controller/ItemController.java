package com.pizzariaBackEnd.pizzariaBackEnd.Controller;

import com.pizzariaBackEnd.pizzariaBackEnd.Entity.Endereco;
import com.pizzariaBackEnd.pizzariaBackEnd.Entity.Item;
import com.pizzariaBackEnd.pizzariaBackEnd.Repository.EnderecoRepository;
import com.pizzariaBackEnd.pizzariaBackEnd.Repository.ItemRepository;
import com.pizzariaBackEnd.pizzariaBackEnd.Service.EnderecoService;
import com.pizzariaBackEnd.pizzariaBackEnd.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

public class ItemController {

    @Autowired
    private ItemRepository Repository;

    @Autowired
    private ItemService Service;

    @GetMapping("/lista")
    public ResponseEntity<Lista<Item>> lista(){
        return ResponseEntity.ok(Service.listaCompleta());
    }

    @GetMapping("/lista/id/{id}")
    public ResponseEntity<?> listarId(@PathVariable(value = "id") Long id){
        Item listarid = Repository.findById(id).orElse(null);
        return listarid == null
                ? ResponseEntity.badRequest().body(" <<ERRO>>: Dados nao encontrados.")
                : ResponseEntity.ok(listarid);
    }

    @GetMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Item cadastro) {
        try {
            this.Service.cadastrar(cadastro);
            return ResponseEntity.ok("cadastro feito com sucesso");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("ERRO: Integridade " + e.getMessage());
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body("ERRO: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<Item> deletarId = Repository.findById(id);
        if (deletarId.isPresent()) {
            Repository.deleteById(id);
            return ResponseEntity.ok("Deletado com sucesso");
        } else {
            return  ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar( @PathVariable Long id, @RequestBody Item atualizarId){
        try {
            this.Service.atualizar(id, atualizarId);
            return ResponseEntity.ok().body(" atualizado com sucesso");
        }catch (RuntimeException e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}

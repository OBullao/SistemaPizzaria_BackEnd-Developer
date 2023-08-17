package com.pizzariaBackEnd.pizzariaBackEnd.Controller;

import com.pizzariaBackEnd.pizzariaBackEnd.Entity.Pedido;
import com.pizzariaBackEnd.pizzariaBackEnd.Entity.Sabor;
import com.pizzariaBackEnd.pizzariaBackEnd.Repository.PedidoRepository;
import com.pizzariaBackEnd.pizzariaBackEnd.Repository.SaborRepository;
import com.pizzariaBackEnd.pizzariaBackEnd.Service.PedidoService;
import com.pizzariaBackEnd.pizzariaBackEnd.Service.SaborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

public class SaborController {

    @Autowired
    private SaborRepository Repository;

    @Autowired
    private SaborService Service;

    @GetMapping("/lista")
    public ResponseEntity<Lista<Sabor>> lista(){
        return ResponseEntity.ok(Service.listaCompleta());
    }

    @GetMapping("/lista/id/{id}")
    public ResponseEntity<?> listarId(@PathVariable(value = "id") Long id){
        Sabor listarid = Repository.findById(id).orElse(null);
        return listarid == null
                ? ResponseEntity.badRequest().body(" <<ERRO>>: Dados nao encontrados.")
                : ResponseEntity.ok(listarid);
    }

    @GetMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody Sabor cadastro) {
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
        Optional<Sabor> deletarId = Repository.findById(id);
        if (deletarId.isPresent()) {
            Repository.deleteById(id);
            return ResponseEntity.ok("Deletado com sucesso");
        } else {
            return  ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar( @PathVariable Long id, @RequestBody Sabor atualizarId){
        try {
            this.Service.atualizar(id, atualizarId);
            return ResponseEntity.ok().body(" atualizado com sucesso");
        }catch (RuntimeException e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}

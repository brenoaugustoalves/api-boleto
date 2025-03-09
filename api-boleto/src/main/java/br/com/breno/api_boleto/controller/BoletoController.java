package br.com.breno.api_boleto.controller;

import br.com.breno.api_boleto.dto.BoletoDTO;
import br.com.breno.api_boleto.dto.BoletoRequestDTO;
import br.com.breno.api_boleto.service.BoletoSerivce;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boleto")
public class BoletoController {

    public BoletoController(BoletoSerivce boletoSerivce) {
        this.boletoSerivce = boletoSerivce;
    }

    private final BoletoSerivce boletoSerivce;

    @GetMapping("/{codigobarras}")
    public ResponseEntity<BoletoDTO> buscarBoletoCodigoBarras(@PathVariable ("codigobarras") String codigoBarras) {
        var BoletoDTO = boletoSerivce.buscarBoletoPorCodigoBarras(codigoBarras);
        return ResponseEntity.ok(BoletoDTO);

    }

    @PostMapping
    public ResponseEntity<BoletoDTO> save(@Valid @RequestBody BoletoRequestDTO boletoRequestDTO) {
        var boleto = boletoSerivce.save(boletoRequestDTO.getCodigoBarras());
        return new ResponseEntity<BoletoDTO>(boleto, HttpStatus.CREATED);
    }
}

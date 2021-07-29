package com.example.springkafka.controller;

import com.example.springkafka.dto.PlayersDTO;
import com.example.springkafka.dto.ResultDTO;
import com.example.springkafka.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PlayerController {

    PlayerService playerService;

    @PostMapping("/players")
    public ResponseEntity<ResultDTO> registerPlayers(@RequestBody PlayersDTO players) {
        ResultDTO result = playerService.playersHandler(players);
        return ResponseEntity.status(201).body(result);
    }
}

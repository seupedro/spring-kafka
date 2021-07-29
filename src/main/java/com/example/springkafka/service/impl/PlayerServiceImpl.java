package com.example.springkafka.service.impl;

import com.example.springkafka.constant.PlayerType;
import com.example.springkafka.dto.PlayerDTO;
import com.example.springkafka.dto.PlayersDTO;
import com.example.springkafka.dto.ResultDTO;
import com.example.springkafka.entity.Player;
import com.example.springkafka.repository.PlayerRepository;
import com.example.springkafka.service.PlayerService;
import com.example.springkafka.service.ProducerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PlayerServiceImpl implements PlayerService {

    ProducerService producerService;
    PlayerRepository repository;

    @Override
    public ResultDTO playersHandler(PlayersDTO players) {
        List<PlayerDTO> expertPlayers = getExpertPlayers(players);
        List<PlayerDTO> novicePlayers = getNovicePlayers(players);
        List<PlayerDTO> unknownTypePlayers = getUnknownTypePlayers(players);

        return resultGenerator(expertPlayers, novicePlayers, unknownTypePlayers);
    }

    private List<PlayerDTO> getExpertPlayers(PlayersDTO players) {
        return players.getPlayers()
                .stream()
                .filter(player -> Objects.equals(player.getType(), PlayerType.EXPERT))
                .peek(player -> repository.save(assemblePlayerOf(player)))
                .collect(Collectors.toList());
    }

    private List<PlayerDTO> getNovicePlayers(PlayersDTO players) {
        return players.getPlayers()
                .stream()
                .filter(player -> Objects.equals(player.getType(), PlayerType.NOVICE))
                .peek(player -> producerService.sendMessage(player.getName()))
                .collect(Collectors.toList());
    }

    private List<PlayerDTO> getUnknownTypePlayers(PlayersDTO players) {
        return players.getPlayers()
                .stream()
                .filter(player -> !Objects.equals(player.getType(), PlayerType.NOVICE) && !Objects.equals(player.getType(), PlayerType.EXPERT))
                .collect(Collectors.toList());
    }

    private Player assemblePlayerOf(PlayerDTO player) {
        return new Player(null, player.getName(), player.getType());
    }

    private ResultDTO resultGenerator(List<PlayerDTO> expertPlayers, List<PlayerDTO> novicePlayers, List<PlayerDTO> unknownTypePlayers) {

        List<String> expertResult = expertPlayers.stream()
                .map(player -> String.format("player %s stored in DB", player.getName()))
                .collect(Collectors.toList());

        List<String> noviceResult = novicePlayers.stream()
                .map(player -> String.format("player %s sent to Kafka topic", player.getName()))
                .collect(Collectors.toList());

        List<String> unknownResult = unknownTypePlayers.stream()
                .map(player -> String.format("player %s did not fit", player.getName()))
                .collect(Collectors.toList());

        List<String> result = Stream.of(expertResult, noviceResult, unknownResult)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return new ResultDTO(result);
    }
}

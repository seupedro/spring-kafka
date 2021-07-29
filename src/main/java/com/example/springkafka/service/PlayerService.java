package com.example.springkafka.service;

import com.example.springkafka.dto.PlayersDTO;
import com.example.springkafka.dto.ResultDTO;

public interface PlayerService {

    ResultDTO playersHandler(PlayersDTO players);

}

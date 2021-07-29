package com.example.springkafka.service.impl;

import com.example.springkafka.service.ProducerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProducerServiceImpl implements ProducerService {

   private KafkaTemplate<String, String> kafkaTemplate;

   @Override
   public void sendMessage(String message) {
      ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("novice-players", message);

      future.addCallback(new ListenableFutureCallback<>() {
         @Override
         public void onFailure(Throwable ex) {
            log(Level.SEVERE, String.format("Error sending message=[%s] due to: %s %n", message, ex.getMessage()));
         }

         @Override
         public void onSuccess(SendResult<String, String> result) {
            log(Level.INFO, String.format("Message delivered=[%s]", message));
         }
      });
   }

   private void log(Level level, String message) {
      Logger logger = Logger.getLogger(this.getClass().getName());
      logger.log(level, message);
   }

}

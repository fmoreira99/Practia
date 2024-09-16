package com.example.cuentapovimiento.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ClienteEventConsumer {
    @KafkaListener(topics = "cliente-creado-topic", groupId = "cuenta-movimientos-group")
    public void listen(ConsumerRecord<String, String> record) {
        String clienteId = record.key();
        String clienteNombre = record.value();
        System.out.println("Cliente creado: ID = " + clienteId + ", Nombre = " + clienteNombre);
    }
}

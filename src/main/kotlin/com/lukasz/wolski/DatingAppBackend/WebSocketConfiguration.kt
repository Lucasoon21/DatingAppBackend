package com.lukasz.wolski.DatingAppBackend

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.messaging.simp.config.MessageBrokerRegistry

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfiguration : WebSocketMessageBrokerConfigurer {
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        println("-----------------------------------")
        registry.addEndpoint("/chat").setAllowedOrigins("*").setAllowedOriginPatterns("*").withSockJS()
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        println("-----------------------------------")
        registry.setApplicationDestinationPrefixes("/app").enableSimpleBroker("/topic");
/*
        registry.setApplicationDestinationPrefixes("/app")
        registry.enableSimpleBroker("/chatroom", "/user")
        registry.setUserDestinationPrefix("/user")*/
    }
}
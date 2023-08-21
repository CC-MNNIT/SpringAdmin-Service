package com.cc.spring.admin.security

import de.codecentric.boot.admin.server.config.AdminServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebSecurityConfig(private val adminServer: AdminServerProperties) {

    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
    ): SecurityWebFilterChain = http
        // Change context path of service
        .addFilterAt({ exchange, chain ->
            chain.filter(
                exchange.mutate()
                    .request(exchange.request.mutate().contextPath("/spring-admin").build())
                    .build()
            )
        }, SecurityWebFiltersOrder.FIRST)
        .authorizeExchange { spec ->
            spec.pathMatchers(
                "${adminServer.contextPath}/assets/**",
                "${adminServer.contextPath}/instances/**",
                "${adminServer.contextPath}/login",
            ).permitAll()
                .anyExchange().authenticated()
        }
        .formLogin { spec -> spec.loginPage("${adminServer.contextPath}/login") }
        .logout { spec -> spec.logoutUrl("${adminServer.contextPath}/logout") }
        .httpBasic { }
        .csrf { it.disable() }
        .build()
}
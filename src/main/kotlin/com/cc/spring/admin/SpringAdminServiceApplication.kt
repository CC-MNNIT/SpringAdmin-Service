package com.cc.spring.admin

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAdminServer
class SpringAdminServiceApplication

fun main(args: Array<String>) {
    runApplication<SpringAdminServiceApplication>(*args)
}

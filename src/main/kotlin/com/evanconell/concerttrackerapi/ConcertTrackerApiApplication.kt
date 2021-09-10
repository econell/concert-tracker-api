package com.evanconell.concerttrackerapi

import com.evanconell.concerttrackerapi.configuration.WebConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.evanconell"])
@EnableConfigurationProperties(WebConfig::class)
class ConcertTrackerApiApplication

fun main(args: Array<String>) {
    runApplication<ConcertTrackerApiApplication>(*args)
}

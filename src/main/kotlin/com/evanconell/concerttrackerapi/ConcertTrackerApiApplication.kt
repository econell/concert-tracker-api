package com.evanconell.concerttrackerapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConcertTrackerApiApplication

fun main(args: Array<String>) {
    runApplication<ConcertTrackerApiApplication>(*args)
}

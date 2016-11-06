package com.yakoobahmad

import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

class Akka {

    @PostConstruct
    def init(){
        // call as soon as constructor is done running and class is ready
        // do stuff like start actor system cluster and listeners ect...
    }

}

@Service('akkaService')
class AkkaService {

    def sendToOne(payload){
        println payload
    }

}

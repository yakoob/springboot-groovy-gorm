package com.yakoobahmad

import grails.gorm.annotation.Entity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@Entity
class Account {

    static enum Status {ACTIVE,INACTIVE}

    String name
    Status status
    Date created

    def isActive() {
        this.status == Status.ACTIVE
    }

    static def listActiveAccountsThisMonth(){
        /*
        Account.findAllByStatusAndCreatedBetween(Status.ACTIVE, new Date(), new Date())
         */

        return Account.findAllByStatus(Status.ACTIVE)


    }

    static constraints = {
        name nullable: true
        status nullable: true
        created nullable: true
    }
}

@Service('accountService')
@Transactional
class AccountService {

    @Autowired
    AkkaService akkaService

    def goToPaid(Long accountId){

        if (Account.read(accountId).isActive()){
            // do some stuff here
            // with dependencies
            akkaService.sendToOne("foo")
            return true
        }

        return false
    }

}

@RestController
@RequestMapping(value="/account")
class AccountController {

    @Autowired
    AccountService accountService

    @RequestMapping(value = "/list/current/month", method = RequestMethod.GET)
    def listActiveAccounts() {
        return Account.listActiveAccountsThisMonth().id
    }

    @RequestMapping(value = "/goToPaid/{accountId}", method = RequestMethod.PUT)
    def goToPaid(@PathVariable String accountId) {
        return accountService.goToPaid(accountId?.toLong())
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    def update(@PathVariable String id, @RequestBody Account account) {

        if (!Account.read(id))
            return new ResponseEntity(HttpStatus.NOT_FOUND)

        return new ResponseEntity<Account>(account.attach().save(failOnError:true), HttpStatus.OK)
    }

}
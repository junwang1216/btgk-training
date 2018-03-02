package groovy

import com.google.gson.Gson
import groovy.sql.Sql
import jodd.http.HttpRequest

import java.util.concurrent.Callable
import java.util.concurrent.CyclicBarrier
import java.util.concurrent.ExecutionException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

trait Testable {

    private def baseUrl = "http://localhost:8080"

    def db = [url:'jdbc:mysql://127.0.0.1:3306/admin', user:'root', password:'123456', driver:'com.mysql.jdbc.Driver']
    def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)

    def get(url, Map map) {
        HttpRequest.get((url.startsWith("http")) ? url : (baseUrl + url)).query(map).send().bodyText()
    }

    def getAsMap(url, Map map) {
        print url+":"+asString(map)
        println()
        def data = asMap(HttpRequest.get((url.startsWith("http")) ? url : (baseUrl + url)).query(map).send().bodyText())
        print url+":"+asString(data)
        println()
        data
    }

    def post(url, Map map) {
        HttpRequest.post((url.startsWith("http")) ? url : (baseUrl + url)).form(map).send().bodyText()
    }

    def postAsMap(url, Map map) {
        print url+":"+asString(map)
        println()
        def data = asMap(HttpRequest.post((url.startsWith("http")) ? url : (baseUrl + url)).form(map).send().bodyText())
        print url+":"+asString(data)
        println()
        data
    }

    def postAsMap(url, String json) {
        print url+":"+json
        println()
        def data = asMap(HttpRequest.post((url.startsWith("http")) ? url : (baseUrl + url)).bodyText(json,"application/json").send().bodyText())
        print url+":"+asString(data)
        println()
        data
    }


    def asMap(def json) {
        new Gson().fromJson(json, Map.class)
    }

    def asList(def json) {
        new Gson().fromJson(json, List.class)
    }
    def asString(def json){
        new Gson().toJson(json)
    }


    def concurrent(int count, Closure closure) {
        def values = []
        def futures = []

        ExecutorService executor = Executors.newFixedThreadPool(count)
        CyclicBarrier barrier = new CyclicBarrier(count)

        (1..count).each {
            futures << executor.submit({
                barrier.await()
                closure.call()
            } as Callable)
        }


        futures.each {
            try {
                values << it.get()
            } catch (ExecutionException e) {
                values << e.cause
            }
        }

        values
    }


}
package groovy.spec

import geb.spock.GebSpec
import groovy.Testable

/**
 * Created by wangjun on 2017/6/16.
 */
class AdminTest extends GebSpec implements Testable {

    def "Admin Test"() {

        when:
        def adminLoginURL = "http://localhost:8080/admin/passport/login"
        println(adminLoginURL)

        browser.go(adminLoginURL)

        then:
        true

        when:
        $("input", name: "mobile").value(GenerateCellPhone.generate())
        Thread.sleep(1000)
        $("input", name: "password").value(System.currentTimeMillis())
        Thread.sleep(1000)

        $("a", class: "to-login").click()
        Thread.sleep(1000)

        then:
        true

    }
}

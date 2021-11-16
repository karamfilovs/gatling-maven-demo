package petclinic.simulations


import com.google.gson.Gson
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import petclinic.dtos.Vet

import scala.concurrent.duration.DurationInt



class PetClinicSimulation extends Simulation {
    private val PetClinicBaseUrl = "http://localhost:9966/petclinic/api"
    private val headers = Map("Content-Type" -> "application/json", "Authorization" -> "Basic YWRtaW46YWRtaW4=")
    private val httpProtocols = http.baseUrl(PetClinicBaseUrl).headers(headers)
    private val GeneralFeeder = Iterator.continually(Map("name" -> "test"))
    private val VetFeeder = csv("data/vets.csv").random
    private val virtualUsers: String = sys.props.getOrElse("users", "1")
    private val rampUpTime: String = sys.props.getOrElse("rampUp", "10")
    private val thinkingTime: String = sys.props.getOrElse("thinkTime", "1")
    private val Gson: Gson = new Gson().newBuilder().setPrettyPrinting().create();


    def createVet(vet: Vet) = {
      exec(http("Create new vet")
        .post(s"$PetClinicBaseUrl/vets")
        .header("Authorization", "Basic YWRtaW46YWRtaW4=")
        .body(StringBody(Gson.toJson(vet)))
        .check(status.is(201)))
    }




    val createVetScenario = scenario("Create new vet")
      .feed(VetFeeder)
      .exec(createVet(Vet("${firstName}", "${lastName}", null))).pause(thinkingTime)

    setUp(createVetScenario.inject(rampUsers(virtualUsers.toInt).during(rampUpTime.toInt.seconds)))
      .protocols(httpProtocols)
}

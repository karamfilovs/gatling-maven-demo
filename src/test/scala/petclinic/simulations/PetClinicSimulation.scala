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
    private val GeneralFeeder = Iterator.continually(Map("name" -> "test"))
    private val VetFeeder = csv("data/vets.csv").random
    private val virtualUsers: String = sys.props.getOrElse("users", "1")
    private val rampUpTime: String = sys.props.getOrElse("rampUp", "10")
    private val thinkingTime: String = sys.props.getOrElse("thinkTime", "1")
    private val Gson: Gson = new Gson().newBuilder().setPrettyPrinting().create()
    private val httpConf = http.baseUrl(PetClinicBaseUrl)


    def createVet(vet: Vet) = {
      exec(http("Create new vet")
        .post(s"${PetClinicBaseUrl}/vets")
        .headers(headers)
        .body(StringBody(Gson.toJson(vet)))
        .check(status.is(201)))
    }

    def updateVet(id: Int, vet: Vet) = {
        exec(http("Update existing vet")
          .post(s"${PetClinicBaseUrl}/vets/$id")
          .headers(headers)
          .body(StringBody(Gson.toJson(vet)))
          .check(status.is(201)))
    }

    def getAllVets = {
        exec(http("Get all vets")
          .get(s"${PetClinicBaseUrl}/vets")
          .headers(headers)
          .check(status.is(200)))
    }

    def getSingleVet(id: String) = {
        exec(http("Get all vets")
          .get(s"${PetClinicBaseUrl}/vets/$id")
          .headers(headers)
          .check(status.is(200)))
    }


    val createVetScenario = scenario("Create new vet")
      .feed(VetFeeder)
      .exec(createVet(Vet("${firstName}", "${lastName}", null))).pause(thinkingTime)

    setUp(createVetScenario.inject(rampUsers(virtualUsers.toInt).during(rampUpTime.toInt.seconds)))
      .protocols(httpConf)
}

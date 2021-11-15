package petclinic.scenarios

import io.gatling.core.Predef.scenario
import petclinic.requests.{AddVetRequest, ObtainTokenRequest}

object AddVetScenario {
  val addVetScenario = scenario("Create Vet Scenario")
    .exec(ObtainTokenRequest.obtainToken)
    .exec(AddVetRequest.addVet)
}

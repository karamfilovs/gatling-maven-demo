package petclinic.simulations

import io.gatling.core.Predef.atOnceUsers
import io.gatling.core.scenario.Simulation
import petclinic.config.Config.users
import petclinic.requests.AddVetRequest
import petclinic.scenarios.AddVetScenario
import petclinic.scenarios.AddVetScenario.addVetScenario

class AddVetSimulation extends Simulation {
  private val createUserExec = AddVetScenario.addVetScenario
    .inject(atOnceUsers(users))

  setUp(createUserExec)
}


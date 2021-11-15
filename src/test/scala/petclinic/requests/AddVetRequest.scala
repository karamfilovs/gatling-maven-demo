package petclinic.requests

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import petclinic.config.Config.app_url
object AddVetRequest {
  val sentHeaders = Map("Authorization" -> "bearer ${token}")

  val addVet = exec(http("Add Vet Request")
    .post(app_url + "/vet")
    .headers(sentHeaders)
    .formParam("name", "John")
    .formParam("password", "John5P4ss")
    .check(status is 201)
    .check(regex("Created").exists))


}

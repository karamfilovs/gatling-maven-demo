package petclinic.requests



import io.gatling.http.Predef.{http, status, _}
import petclinic.config.Config.app_url
import io.gatling.core.Predef.{findCheckBuilder2ValidatorCheckBuilder, _}




object ObtainTokenRequest {
  val obtainToken = http("RequestName").get(app_url + "/token")
    .check(status is 200)
    .check(jsonPath("$..token").saveAs("token"))
}

package petclinic.config

object Config {
  val app_url = "http://localhost:9966"

  val users: Int = Integer.getInteger("users", 10).toInt
  val rampUp: Int = Integer.getInteger("rampup", 1).toInt
  val throughput: Int = Integer.getInteger("throughput", 100).toInt
}

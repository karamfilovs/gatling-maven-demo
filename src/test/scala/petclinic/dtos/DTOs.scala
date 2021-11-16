package petclinic.dtos

import com.google.gson.Gson

case class Speciality(id: Int = 1, name: String = "surgery")
case class Vet(firstName: String, lastName: String, specialities: Array[Speciality])


object Test extends App {
  val Gson = new Gson().newBuilder().setPrettyPrinting().create()
  println(Gson.toJson(Vet("Alex", "Karamfilov", null)))
  println(Gson.toJson(Vet("Ivan", "Ivanov", Array(Speciality(2, "denistry")))))
  println("""{ "name": "Alex"}""")
}
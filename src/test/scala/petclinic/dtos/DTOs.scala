package petclinic.dtos

case class Speciality(id: Int, name: String)
case class Vet(firstName: String, lastName: String, specialities: Array[Speciality])
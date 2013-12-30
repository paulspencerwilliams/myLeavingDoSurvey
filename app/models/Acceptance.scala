package models

case class Acceptance(
  name: String,
  availabilities: Seq[Availability], 
  activities: Seq[Activity]) 

case class Availability(date: String, available: Boolean)
case class Activity(name: String, acceptable: Boolean)

object Acceptance {
  def create(acceptance: Acceptance) {}
}

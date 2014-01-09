package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Acceptance(
  name: String,
  availabilities: Seq[Availability]) 

case class Availability(date: String, available: Boolean)

object Acceptance {
  def create(acceptance: Acceptance) {
    DB.withTransaction { implicit c=> 
      SQL("insert into acceptances (name) values ({name})")
        .on ( 'name -> acceptance.name)
        .executeInsert()
      acceptance.availabilities.foreach(availability =>
          SQL("insert into availabilities (date, available) values ({date},{available})")
              .on('date ->availability.date,'available ->availability.available)
            .executeInsert() 
        )
    }
  }

}

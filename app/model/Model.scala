package model

import play.api.db._
import play.api.Play.current
import scala.collection.mutable.ListBuffer

case class RDBGroup(val number: Int, val regularDate: String, val hiwiName: Option[String])

object RDBGroup {
    def list() = {
        DB.withConnection { conn => 
            val groupsResultSet = conn.createStatement().executeQuery("SELECT nr, regular_date, hiwi_name FROM rdb_group");
            
            val groups = new ListBuffer[RDBGroup]();
            while(groupsResultSet.next()) {
                val number = groupsResultSet.getInt(1)
                val regularDate = groupsResultSet.getString(2)
                val hiwiName = groupsResultSet.getString(3)

                groups += RDBGroup(number, regularDate, if(hiwiName == null) None else Some(hiwiName))
            }

            groups.toList
        }
    }
}

// "teammate" is RDB1, teammate2 is SQL Lab
case class RegistrationData(val matriculationNumber: String, val firstname: String, val lastname: String, val email: String, val pick1: Int, pick2: Int, pick3: Int, val attendToRDB: Boolean, val attendToSQL: Boolean, val teammate: String, val teammate2: String) {


    def save() {
        DB.withConnection { conn =>
            val insertRegistrationStmt = conn.prepareStatement("INSERT INTO registration (matriculationnumber, firstname, lastname, email, pick1, pick2, pick3, attend_to_rdb, attend_to_sql, teammate, teammate2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")

            insertRegistrationStmt.setString(1, matriculationNumber)
            insertRegistrationStmt.setString(2, firstname)
            insertRegistrationStmt.setString(3, lastname)
            insertRegistrationStmt.setString(4, email)
            insertRegistrationStmt.setInt(5, pick1)
            insertRegistrationStmt.setInt(6, pick2)
            insertRegistrationStmt.setInt(7, pick3)
            insertRegistrationStmt.setString(8, if(attendToRDB) "t" else "f")
            insertRegistrationStmt.setString(9, if(attendToSQL) "t" else "f")
            insertRegistrationStmt.setString(10, if(teammate.length() == 0) null else teammate)
            insertRegistrationStmt.setString(11, if(teammate2.length() == 0) null else teammate2)
            
            insertRegistrationStmt.executeUpdate();
        }
    }
}

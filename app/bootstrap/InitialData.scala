package bootstrap

import com.google.inject.Inject
import models.Employee
import play.Logger
import repository.EmployeeRepository

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class InitialData @Inject()(employeeRepo: EmployeeRepository)(implicit ec: ExecutionContext) {

  def insert: Future[Unit] = for {
    emps <- employeeRepo.getAll() if (emps.length == 0)
    _ <- employeeRepo.insertAll(Data.employees)
  } yield {}

  try {
    Logger.info("DB initialization.................")
    Await.result(insert, Duration.Inf)
  } catch {
    case ex: Exception =>
      Logger.error("Error in database initialization ", ex)
  }

}

object Data {

  val employees =
    List(
      Employee("Bob", "bob@xyz.com", "ABC Solution", "Senior Consultant"),
      Employee("Rob", "rob@abc.com", "ABC solution", "Consultant"),
      Employee("Joe", "Joe@xyz.com", "ABC solution", "Consultant")
    )
}

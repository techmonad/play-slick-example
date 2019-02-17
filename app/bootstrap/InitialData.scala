package bootstrap

import com.google.inject.Inject
import models.Employee
import org.slf4j.LoggerFactory
import repository.EmployeeRepository

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class InitialData @Inject()(employeeRepo: EmployeeRepository)(implicit ec: ExecutionContext) {

  val logger = LoggerFactory.getLogger(this.getClass)

  def insert: Future[Unit] = for {
    employees <- employeeRepo.getAll() if (employees.length == 0)
    _ <- employeeRepo.insertAll(Data.employees)
  } yield {}

  try {
    logger.info("DB initialization.................")
    Await.result(insert, Duration.Inf)
  } catch {
    case ex: Exception =>
      logger.error("Error in database initialization ", ex)
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

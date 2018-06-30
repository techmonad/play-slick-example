package route

import models.Employee
import play.api.libs.json.Json
import play.api.test.{FakeRequest, PlaySpecification, WithApplication}
import utils.JsonFormat._

class RouteSpec extends PlaySpecification {

  "Routes" should {

    "get employee list" in new WithApplication {
      val Some(result) = route(app, FakeRequest(GET, "/emp/list"))
      status(result) must equalTo(OK)
      contentType(result) must beSome("application/json")
      contentAsString(result) must equalTo("""{"status":"success","data":[{"name":"Bob","email":"bob@xyz.com","companyName":"ABC Solution","position":"Tech Lead","id":1},{"name":"Rob","email":"rob@abc.com","companyName":"ABC Solution","position":"Consultant","id":2},{"name":"Joe","email":"joe@xyz.com","companyName":"ABC Solution ","position":"Senior Consultant","id":3}],"msg":"Getting Employee list successfully."}""")
    }


    "create employee" in new WithApplication() {
      val emp = Employee("jaz", "jaz@bar.com", "ABC solution", "Senior Consultant")
      val Some(result) = route(app, FakeRequest(POST, "/emp/create").withBody(Json.toJson(emp)))
      status(result) must equalTo(OK)
      contentType(result) must beSome("application/json")
      contentAsString(result) must equalTo("""{"status":"success","data":{"id":4},"msg":"Employee has been created successfully."}""")

    }

    "edit employee" in new WithApplication() {
      val Some(result) = route(app, FakeRequest(GET, "/emp/edit?empId=1"))
      status(result) must equalTo(OK)
      contentType(result) must beSome("application/json")
      contentAsString(result) must equalTo("""{"status":"success","data":{"name":"Bob","email":"bob@xyz.com","companyName":"ABC Solution","position":"Tech Lead","id":1},"msg":"Getting Employee successfully."}""")
    }


    "edit invalid employee" in new WithApplication() {
      val Some(result) = route(app, FakeRequest(GET, "/emp/edit?empId=111"))
      status(result) must equalTo(OK)
      contentType(result) must beSome("application/json")
      contentAsString(result) must equalTo("""{"status":"error","data":"{}","msg":"Employee does not exist."}""")
    }

    "update employee" in new WithApplication() {
      val emp = Employee("BOB", "bob@xyz.com", "ABC solution", "Senior Consultant", Some(1))
      val Some(result) = route(app, FakeRequest(POST, "/emp/update").withBody(Json.toJson(emp)))
      status(result) must equalTo(OK)
      contentType(result) must beSome("application/json")
      contentAsString(result) must equalTo("""{"status":"success","data":"{}","msg":"Employee has been updated successfully."}""")

    }


    "delete employee" in new WithApplication() {
      val Some(result) = route(app, FakeRequest(GET, "/emp/delete?empId=1"))
      status(result) must equalTo(OK)
      contentType(result) must beSome("application/json")
      contentAsString(result) must equalTo("""{"status":"success","data":"{}","msg":"Employee has been deleted successfully."}""")
    }

  }


}

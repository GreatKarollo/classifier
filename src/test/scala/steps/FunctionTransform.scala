package steps

/**
  * Date: 11/06/2017.
  * Author: Karol Sudol
  */


import com.karolsudol.analytics.Function.transform
import com.karolsudol.analytics.Schema._
import cucumber.api.scala.{EN, ScalaDsl}
import org.apache.spark.sql.Dataset
import org.scalatest.Matchers

class FunctionTransform extends ScalaDsl with EN with Matchers {


  When("""^I transform table "([^"]*)" and postcode "([^"]*)" and store the results in table "([^"]*)"$""") {
    (ds:Dataset[RawData]) =>
      transform(ds)
  }


}

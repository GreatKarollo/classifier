package com.karolsudol.analytics

import org.apache.spark.sql.types._

/**
  * Date: 09/06/2017.
  * Author: Karol Sudol
  */
object Schema extends Serializable {


  val readFileSchema = new StructType()
    .add("rowID", LongType)
    .add("Feature_0", DoubleType)
    .add("Feature_1", DoubleType)
    .add("Feature_2", DoubleType)
    .add("Feature_3", DoubleType)
    .add("Feature_4", DoubleType)
    .add("Feature_5", DoubleType)
    .add("Feature_6", DoubleType)
    .add("Feature_7", DoubleType)
    .add("Feature_8", DoubleType)
    .add("Feature_9", DoubleType)
    .add("Class", IntegerType)


  case class RawData (
                       rowID: Long,
                       Feature_0:Double,
                       Feature_1:Double,
                       Feature_2:Double,
                       Feature_3:Double,
                       Feature_4:Double,
                       Feature_5:Double,
                       Feature_6:Double,
                       Feature_7:Double,
                       Feature_8:Double,
                       Feature_9:Double,
                       Class:Int
                     )

  case class Prediction (rowID: BigInt,
                         predictedLabel: String ,
                         probability: Int,
                         prediction: Double
                        )




  case class Transformed (rowID: BigInt,
                          features: org.apache.spark.ml.linalg.Vector
                         )


  case class Joined (
                      rowID: Long,
                      Class:Int,
                      prediction: Int,
                      probability: Int

                    )


}

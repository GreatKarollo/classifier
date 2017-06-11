package com.karolsudol.analytics

import com.karolsudol.analytics.Schema._
import org.apache.spark.ml.PipelineModel
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.DenseVector
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions.{col, lit, round, udf}
import org.apache.spark.sql.types.IntegerType


/**
  * Date: 09/06/2017.
  * Author: Karol Sudol
  */
object Function {

  val vectorToColumn = udf{ (x: DenseVector, index: Int) => x(index) }

  val featureCols = Array("Feature_0", "Feature_1", "Feature_2", "Feature_3", "Feature_4", "Feature_5", "Feature_6", "Feature_7", "Feature_8", "Feature_9")
  val assembler = new VectorAssembler().setInputCols(featureCols).setOutputCol("features")

  def transform(ds: Dataset[RawData])  = {
    assembler.transform(ds)
      .select(
        col("rowID"),
        col("features")
      )
  }


  def predict(ds: Dataset[Transformed], model: PipelineModel) = {
    model.transform(ds)
      .select(
        col("rowID"),
        col("predictedLabel"),
        (round(vectorToColumn(col("probability"),lit(0)),2)* 100 ).cast(IntegerType).as("probability"),
        col("prediction")
      )
  }

}

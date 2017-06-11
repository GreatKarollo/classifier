package com.karolsudol.analytics

import com.karolsudol.analytics.Function._
import com.karolsudol.analytics.Schema._
import org.apache.spark.ml.PipelineModel
import org.apache.spark.sql.expressions.scalalang.typed
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.{ProcessingTime, StreamingQuery}
import org.apache.spark.sql.{Dataset, SparkSession}
/**
  * Date: 09/06/2017.
  * Author: Karol Sudo
  */
object FileStream {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .master("local[*]")
      .appName("StreamingApp")
      .getOrCreate()

    import spark.implicits._

    //implicit val encoder = Encoders.bean[Result](classOf[Result])



    val dataInPath = "/"//args(1).toString
    val DTModel = PipelineModel.load(s"${dataInPath}treeModel")

    val rawDS: Dataset[RawData] = spark.read.parquet(s"${dataInPath}dsNullDropped.parquet").as[RawData]
    val streamDS: Dataset[RawData] = spark.readStream.schema(readFileSchema).parquet("/OUT/streamFile.parquet").as[RawData]

    val transformedDS= transform(streamDS).as[Transformed]
    val predictedDS = predict(transformedDS,DTModel).as[Prediction]

    val joinedDS: Dataset[Joined] = predictedDS.join(rawDS, Seq("rowID"))
      .select(
        col("rowID"),
        col("Class"),
        col("prediction").cast("Int"),
        col("probability")
      )
    .as[Joined]

    val result = joinedDS.groupByKey(_.Class).agg(typed.avg(_.probability),typed.count(_.rowID))
      .withColumnRenamed("TypedAverage(com.karolsudol.analytics.Schema$Joined)", "AvgProb")
      .withColumnRenamed("TypedSumDouble(com.karolsudol.analytics.Schema$Joined)", "Count")
      .withColumnRenamed("value", "Class")


      val query: StreamingQuery = result
        .writeStream.format("console")
        .outputMode("complete")
        .queryName("averageProbability")
        .trigger(ProcessingTime("5 seconds")) // check for files every 5s
        .start()

    query.awaitTermination()




  }

}

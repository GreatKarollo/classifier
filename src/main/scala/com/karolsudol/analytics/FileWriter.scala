package com.karolsudol.analytics

import com.karolsudol.analytics.Schema.RawData
import org.apache.spark.sql.{Dataset, SparkSession}

/**
  * Date: 10/06/2017.
  * Author: Karol Sudol
  */
object FileWriter {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .master("local[*]")
      .appName("FileWriter")
      .getOrCreate()

    import spark.implicits._

    val dataInPath = "/"


    val rawDS: Dataset[RawData] = spark.read.parquet(s"${dataInPath}dsNullDropped.parquet").as[RawData]

    val rawDS_1to300: Dataset[RawData] = rawDS.filter(_.rowID <= 300).as[RawData]
    val rawDS_300to400: Dataset[RawData] = rawDS.filter(_.rowID <= 400).filter(_.rowID > 300).as[RawData]
    val rawDS_400to500: Dataset[RawData] = rawDS.filter(_.rowID <= 500).filter(_.rowID > 400).as[RawData]
    val rawDS_500to600: Dataset[RawData] = rawDS.filter(_.rowID <= 600).filter(_.rowID > 500).as[RawData]
    val rawDS_gt600: Dataset[RawData] = rawDS.filter(_.rowID > 600).as[RawData]



    println("****************")
    println("****************")
    println("writing_rawDS_1to300")
    rawDS_1to300.show(20)
    rawDS_1to300.write.mode("overwrite").parquet("/OUT/streamFile.parquet")
    Thread.sleep(1000*20)


    println("****************")
    println("****************")
    println("writing_rawDS_300to400")
    rawDS_300to400.show(20)
    rawDS_300to400.write.mode("append").parquet("/OUT/streamFile.parquet")
    Thread.sleep(1000*20)


    println("****************")
    println("****************")
    println("writing_rawDS_400to500")
    rawDS_400to500.show(20)
    rawDS_400to500.write.mode("append").parquet("/OUT/streamFile.parquet")
    Thread.sleep(1000*20)


    println("****************")
    println("****************")
    println("writing_rawDS_500to600")
    rawDS_500to600.show(20)
    rawDS_500to600.write.mode("append").parquet("/OUT/streamFile.parquet")
    Thread.sleep(1000*20)


    println("****************")
    println("****************")
    println("writing_rawDS_gt600")
    rawDS_gt600.show(20)
    rawDS_gt600.write.mode("append").parquet("/OUT/streamFile.parquet")
    Thread.sleep(1000*20)





  }

}

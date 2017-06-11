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

    val rawDS_1to1000: Dataset[RawData] = rawDS.filter(_.rowID <= 1000).as[RawData]
    val rawDS_1000to2000: Dataset[RawData] = rawDS.filter(_.rowID <= 2000).filter(_.rowID > 1000).as[RawData]
    val rawDS_2000to3000: Dataset[RawData] = rawDS.filter(_.rowID <= 3000).filter(_.rowID > 2000).as[RawData]
    val rawDS_3000to4000: Dataset[RawData] = rawDS.filter(_.rowID <= 4000).filter(_.rowID > 3000).as[RawData]
    val rawDS_gt4000: Dataset[RawData] = rawDS.filter(_.rowID > 4000).as[RawData]



    println("****************")
    println("****************")
    println("writing_rawDS_1to100")
    rawDS_1to1000.show(20)
    rawDS_1to1000.write.mode("overwrite").parquet("/OUT/streamFile.parquet")
    Thread.sleep(1000*20)


    println("****************")
    println("****************")
    println("writing_rawDS_100to200")
    rawDS_1000to2000.show(20)
    rawDS_1000to2000.write.mode("append").parquet("/OUT/streamFile.parquet")
    Thread.sleep(1000*20)


    println("****************")
    println("****************")
    println("writing_rawDS_200to300")
    rawDS_2000to3000.show(20)
    rawDS_2000to3000.write.mode("append").parquet("/OUT/streamFile.parquet")
    Thread.sleep(1000*20)


    println("****************")
    println("****************")
    println("writing_rawDS_300to400")
    rawDS_3000to4000.show(20)
    rawDS_3000to4000.write.mode("append").parquet("/OUT/streamFile.parquet")
    Thread.sleep(1000*20)


    println("****************")
    println("****************")
    println("writing_rawDS_gt4000")
    rawDS_gt4000.show(20)
    rawDS_gt4000.write.mode("append").parquet("/OUT/streamFile.parquet")
    Thread.sleep(1000*20)





  }

}

package hupo.hedgefund.generic

import hupo.hedgefund.common.Entity

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by kunjiang on 16/8/30.
  */
trait MergeService {

  /*
    * 分别从 public 和 private repository里获取数据, 然后merge
   */
  def get[F, T <: Entity with Product](publicOp: F => Future[Option[T]], privateOp: F => Future[Option[T]])(paramPublic: F, paramPrivate: F)
    (implicit merger: (T, T) => T): Future[T] = {
    val publicDataFut = publicOp(paramPublic)
    val privateDataFut = privateOp(paramPrivate)

    val resultTuple = for (publicData <- publicDataFut;
                           privateData <- privateDataFut
    ) yield (publicData, privateData);

    resultTuple map {
      case (None, Some(privateData)) => privateData
      case (Some(publicData), None) => publicData
      case (Some(publicData), Some(privateData)) => merger(publicData, privateData) // merge fields by fields
      case (None, None) => throw new IllegalArgumentException(s"No data found for ${paramPublic.toString} ${paramPrivate.toString}")
    }
  }

  /*
    * 分别从 public 和 private repository里获取Seq数据, 然后逐行merge
   */
  def batchGet[F, T <: Entity with Product](publicOp: F => Future[Seq[T]], privateOp: F => Future[Seq[T]])(paramPublic: F, paramPrivate: F)
    (implicit merger: (T, T) => T): Future[Seq[T]] = {
    val publicDataSeqFut = publicOp(paramPublic)
    val privateDataSeqFut = privateOp(paramPrivate)

    val resultTuple = for (publicDataSeq <- publicDataSeqFut;
                           privateDataSeq <- privateDataSeqFut
    ) yield (publicDataSeq, privateDataSeq);

    resultTuple map { case (publicDataSeq, privateDataSeq) =>
      val privateDataMap = privateDataSeq.map(result => (result.uid, result)).toMap

      publicDataSeq map { publicData =>
        privateDataMap.get(publicData.uid) match {
          case Some(privateData) => merger(publicData, privateData) // merge field by field
          case None => publicData
        }
      }
    }
  }

  /*
   * 只更新 private repository 的数据
   */
  def update[F, T <: Entity with Product](publicOp: F => Future[T], privateOp: F => Future[T])(param: F): Future[T] = {
    privateOp(param)
  }

  def mergeOptions[T <: Entity with Product](publicData: Option[T], privateData: Option[T]) (implicit merger: (T, T) => T): T = {
    (publicData, privateData) match {
      case (None, Some(privateData)) => privateData
      case (Some(publicData), None) => publicData
      case (Some(publicData), Some(privateData)) => merger(publicData, privateData) // merge fields by fields
      case (None, None) => throw new IllegalArgumentException("Cann't merge two none objects")
    }
  }
}


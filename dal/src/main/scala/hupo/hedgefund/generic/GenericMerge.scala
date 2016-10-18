package hupo.hedgefund.generic

import shapeless._
import shapeless.ops.hlist._
import syntax.std.tuple._

object ChooseNotEmpty extends Poly1 {
  implicit def default[T, U] = at[(T, U)] {
    case (Some(originalValue), None) => Some(originalValue)
    case (None, Some(newValue)) => Some(newValue)
    case (originalValue, Nil) => originalValue
    case (originalValue, null) => originalValue
    case (originalValue, newValue) => newValue
  }
}

object GenericMerge {
  def merge[C, HF <: Poly, Repr <: HList, ZRepr <: HList, MRepr <: HList](base: C, update: C)
    (implicit
      gen: Generic.Aux[C, Repr],
      zipper: Zip.Aux[Repr :: Repr :: HNil, ZRepr],
      mapper: Mapper.Aux[ChooseNotEmpty.type, ZRepr, MRepr]): C = {

    val baseGen = gen.to(base)
    val updateGen = gen.to(update)
    val zipped = baseGen zip updateGen
    gen.from((zipped map ChooseNotEmpty).asInstanceOf[Repr])
  }
}


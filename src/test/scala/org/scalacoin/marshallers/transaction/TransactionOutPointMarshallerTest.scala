package org.scalacoin.marshallers.transaction

import org.scalacoin.protocol.transaction.TransactionOutPoint
import org.scalatest.{FlatSpec, MustMatchers}
import spray.json._
import DefaultJsonProtocol._
/**
 * Created by chris on 12/27/15.
 */
class TransactionOutPointMarshallerTest extends FlatSpec with MustMatchers {

  val str =
    """
      |{
      | "txid" : "808105ed5feff1c05daf6efd202e5966fcdda71ca961e393a1fc83f2b03315d1",
      | "vout" : 0,
      | "scriptSig" : {
      |   "asm" : "0 3045022100b4062edd75b5b3117f28ba937ed737b10378f762d7d374afabf667180dedcc62022005d44c793a9d787197e12d5049da5e77a09046014219b31e9c6b89948f648f1701 3045022100b3b0c0273fc2c531083701f723e03ea3d9111e4bbca33bdf5b175cec82dcab0802206650462db37f9b4fe78da250a3b339ab11e11d84ace8f1b7394a1f6db0960ba401 5221025e9adcc3d65c11346c8a6069d6ebf5b51b348d1d6dc4b95e67480c34dc0bc75c21030585b3c80f4964bf0820086feda57c8e49fa1eab925db7c04c985467973df96521037753a5e3e9c4717d3f81706b38a6fb82b5fb89d29e580d7b98a37fea8cdefcad53ae",
      |   "hex" : "00483045022100b4062edd75b5b3117f28ba937ed737b10378f762d7d374afabf667180dedcc62022005d44c793a9d787197e12d5049da5e77a09046014219b31e9c6b89948f648f1701483045022100b3b0c0273fc2c531083701f723e03ea3d9111e4bbca33bdf5b175cec82dcab0802206650462db37f9b4fe78da250a3b339ab11e11d84ace8f1b7394a1f6db0960ba4014c695221025e9adcc3d65c11346c8a6069d6ebf5b51b348d1d6dc4b95e67480c34dc0bc75c21030585b3c80f4964bf0820086feda57c8e49fa1eab925db7c04c985467973df96521037753a5e3e9c4717d3f81706b38a6fb82b5fb89d29e580d7b98a37fea8cdefcad53ae"
      | },
      | "sequence" : 4294967295
      |}
    """.stripMargin

  "" must "parse the transaction outpoint from a json input" in {
    val json = str.parseJson
    val outPoint : TransactionOutPoint = TransactionOutPointMarshaller.TransactionOutPointFormatter.read(json)

    outPoint.txId must be ("808105ed5feff1c05daf6efd202e5966fcdda71ca961e393a1fc83f2b03315d1")
    outPoint.vout must be (0)
  }
}

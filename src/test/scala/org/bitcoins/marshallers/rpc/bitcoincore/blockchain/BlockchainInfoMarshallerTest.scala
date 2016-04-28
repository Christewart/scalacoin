package org.bitcoins.marshallers.rpc.bitcoincore.blockchain

import org.bitcoins.marshallers.rpc.bitcoincore.blockchain.softforks.SoftForkMarshaller
import org.bitcoins.marshallers.rpc.bitcoincore.blockchain.softforks.SoftForkMarshaller.SoftForkFormatter
import org.bitcoins.protocol.rpc.bitcoincore.blockchain.BlockchainInfo
import org.bitcoins.protocol.rpc.bitcoincore.blockchain.softforks.SoftForks
import org.scalatest.{FlatSpec, MustMatchers}
import spray.json._

/**
 * Created by Tom on 1/11/2016.
 */
class BlockchainInfoMarshallerTest extends FlatSpec with MustMatchers {
  val str =
    """
      |{
      |    "chain" : "test",
      |    "blocks" : 632532,
      |    "headers" : 632532,
      |    "bestblockhash" : "00000000c3dbad564236cc6b127ae53126e76632825f8b68bca89251b85e43b4",
      |    "difficulty" : 1.00000000,
      |    "verificationprogress" : 0.99999997,
      |    "chainwork" : "00000000000000000000000000000000000000000000000730748cb316d3d01f",
      |    "pruned" : false,
      |    "softforks" : [
      |        {
      |            "id" : "bip34",
      |            "version" : 2,
      |            "enforce" : {
      |                "status" : true,
      |                "found" : 100,
      |                "required" : 51,
      |                "window" : 100
      |            },
      |            "reject" : {
      |                "status" : true,
      |                "found" : 100,
      |                "required" : 75,
      |                "window" : 100
      |            }
      |        },
      |        {
      |            "id" : "bip66",
      |            "version" : 3,
      |            "enforce" : {
      |                "status" : true,
      |                "found" : 100,
      |                "required" : 51,
      |                "window" : 100
      |            },
      |            "reject" : {
      |                "status" : true,
      |                "found" : 100,
      |                "required" : 75,
      |                "window" : 100
      |            }
      |        },
      |        {
      |            "id" : "bip65",
      |            "version" : 4,
      |            "enforce" : {
      |                "status" : true,
      |                "found" : 100,
      |                "required" : 51,
      |                "window" : 100
      |            },
      |            "reject" : {
      |                "status" : true,
      |                "found" : 100,
      |                "required" : 75,
      |                "window" : 100
      |            }
      |        }
      |    ]
      |}
    """.stripMargin

  val json = str.parseJson

  "BlockchainInfoMarshaller" must "parse blockchain information" in {
    val detail : BlockchainInfo = BlockchainInfoMarshaller.BlockchainInfoFormatter.read(json)
    detail.chain must be ("test")
    detail.blockCount must be (632532)
    detail.headerCount must be (632532)
    detail.bestBlockHash must be ("00000000c3dbad564236cc6b127ae53126e76632825f8b68bca89251b85e43b4")
    detail.difficulty must be (1.00000000)
    detail.verificationProgress must be (0.99999997)
    detail.chainWork must be ("00000000000000000000000000000000000000000000000730748cb316d3d01f")
    detail.pruned must be (false)
    detail.softForks.size must be (3)
  }

  it must "write blockchain info" in {
    val json = str.parseJson
    val detail : BlockchainInfo = BlockchainInfoMarshaller.BlockchainInfoFormatter.read(json)
    val writtenBlockchain = BlockchainInfoMarshaller.BlockchainInfoFormatter.write(detail)
    writtenBlockchain.asJsObject.fields("chain") must be (JsString("test"))
    writtenBlockchain.asJsObject.fields("blocks") must be (JsNumber(632532))
    writtenBlockchain.asJsObject.fields("headers") must be (JsNumber(632532))
    writtenBlockchain.asJsObject.fields("bestblockhash") must be (JsString("00000000c3dbad564236cc6b127ae53126e76632825f8b68bca89251b85e43b4"))
    writtenBlockchain.asJsObject.fields("difficulty") must be (JsNumber(1.00000000))
    writtenBlockchain.asJsObject.fields("verificationprogress") must be (JsNumber(0.99999997))
    writtenBlockchain.asJsObject.fields("chainwork") must be (JsString("00000000000000000000000000000000000000000000000730748cb316d3d01f"))
    writtenBlockchain.asJsObject.fields("pruned") must be (JsBoolean(false))
  }

}

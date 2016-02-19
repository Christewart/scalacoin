package org.scalacoin.protocol.script

import org.scalacoin.util.ScalacoinUtil
import org.scalatest.{FlatSpec, MustMatchers}

/**
 * Created by chris on 2/17/16.
 */
class ScriptSignatureFactoryTest extends FlatSpec with MustMatchers {

  "ScriptSignatureFactory" must "parse a script correctly given hex input" in {
    val hex = "30450221008949f0cb400094ad2b5eb399d59d01c14d73d8fe6e96df1a7150deb388ab8935022079656090d7" +
      "f6bac4c9a94e0aad311a4268e082a725f8aeae0573fb12ff866a5f01"

    val scriptSig = ScriptSignatureFactory.factory(hex)

    scriptSig.hex must be (hex)
    scriptSig.bytes must be (ScalacoinUtil.decodeHex(hex))

  }


  it must "give the exact same result whether parsing bytes or parsing hex" in {
    val signatureHex = "30450221008949f0cb400094ad2b5eb399d59d01c14d73d8fe6e96df1a7150deb388ab8935022079656090d7" +
      "f6bac4c9a94e0aad311a4268e082a725f8aeae0573fb12ff866a5f01"
    val signatureBytes : Seq[Byte] = ScalacoinUtil.decodeHex(signatureHex)

    val scriptSigFromHex = ScriptSignatureFactory.factory(signatureHex)
    val scriptSigFromBytes = ScriptSignatureFactory.factory(signatureBytes)

    scriptSigFromHex must be (scriptSigFromBytes)


  }
}
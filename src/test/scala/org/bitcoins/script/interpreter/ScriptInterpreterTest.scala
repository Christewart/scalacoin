package org.bitcoins.script.interpreter

import java.io.File

import com.sun.org.apache.bcel.internal.generic.NOP
import org.bitcoins.protocol.script.{ScriptPubKey}
import org.bitcoins.script.ScriptProgram
import org.bitcoins.script.bitwise.{OP_EQUAL, OP_EQUALVERIFY}
import org.bitcoins.script.constant._
import org.bitcoins.script.control.OP_VERIFY
import org.bitcoins.script.crypto.{OP_CHECKSIG, OP_HASH160}
import org.bitcoins.script.flag.ScriptFlagFactory
import org.bitcoins.script.interpreter.testprotocol.{CoreTestCaseProtocol, CoreTestCase}
import org.bitcoins.script.reserved.OP_NOP
import org.bitcoins.script.stack.OP_DUP
import org.bitcoins.util.{BitcoinSLogger, TransactionTestUtil, TestUtil}
import org.scalatest.{MustMatchers, FlatSpec}
import org.slf4j.LoggerFactory
import CoreTestCaseProtocol._
import spray.json._
/**
 * Created by chris on 1/6/16.
 */
class ScriptInterpreterTest extends FlatSpec with MustMatchers with ScriptInterpreter with BitcoinSLogger {

  "ScriptInterpreter" must "evaluate all the scripts from the bitcoin core script_tests.json" in {


    val source = scala.io.Source.fromFile("src/test/scala/org/bitcoins/script/interpreter/script_tests.json")

    //use this to represent a single test case from script_valid.json
/*    val lines =
        """
          |
          |[["1",
"0x616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161616161",
"P2SH,STRICTENC",
"201 opcodes executed. 0x61 is NOP"]]
   """.stripMargin*/
    val lines = try source.getLines.filterNot(_.isEmpty).map(_.trim) mkString "\n" finally source.close()
    val json = lines.parseJson
    val testCasesOpt : Seq[Option[CoreTestCase]] = json.convertTo[Seq[Option[CoreTestCase]]]
    val testCases : Seq[CoreTestCase] = testCasesOpt.flatten


    for {
      testCase <- testCases
      (creditingTx,outputIndex) = TransactionTestUtil.buildCreditingTransaction(testCase.scriptPubKey.scriptPubKey)
      (tx,inputIndex) = TransactionTestUtil.buildSpendingTransaction(creditingTx,testCase.scriptSig.scriptSignature,outputIndex)
    } yield {
      require(testCase.scriptPubKey.asm == testCase.scriptPubKey.scriptPubKey.asm)
      logger.info("Raw test case: " + testCase.raw)
      logger.info("Parsed ScriptSig: " + testCase.scriptSig)
      logger.info("Parsed ScriptPubKey: " + testCase.scriptPubKey)
      logger.info("Flags: " + testCase.flags)
      logger.info("Comments: " + testCase.comments)
      val scriptPubKey = ScriptPubKey.fromAsm(testCase.scriptPubKey.asm)
      val flags = ScriptFlagFactory.fromList(testCase.flags)
      logger.info("Flags after parsing: " + flags)
      val program = ScriptProgram(tx,scriptPubKey,inputIndex,flags)
      withClue(testCase.raw) {
        ScriptInterpreter.run(program) must equal (testCase.expectedResult)
      }
    }
  }

}

package org.scalacoin.script

import org.scalacoin.script.constant.{ScriptNumberFactory, OP_0, ScriptNumberImpl}
import org.scalacoin.util.TestUtil
import org.scalatest.{FlatSpec, MustMatchers}

/**
 * Created by chris on 2/6/16.
 */
class ScriptProgramTest extends FlatSpec with MustMatchers  {

  "ScriptProgram" must "determine if the stack top is true" in {
    val stack = List(ScriptNumberImpl(1))
    val script = List()
    val program = ScriptProgram(TestUtil.testProgram, stack,script)
    program.stackTopIsTrue must be (true)
  }

  it must "determine if the stack stop is false" in {
    val stack = List(ScriptNumberFactory.zero)
    val script = List()
    val program = ScriptProgram(TestUtil.testProgram, stack,script)
    program.stackTopIsTrue must be (false)

    val program2 = ScriptProgram(program, List(OP_0), ScriptProgram.Stack)
    program2.stackTopIsTrue must be (false)

    //stack top should not be true for negative zero
    val program3 = ScriptProgram(program, List(ScriptNumberFactory.negativeZero), ScriptProgram.Stack)
    program3.stackTopIsTrue must be (false)
  }
}

package logisticspipes.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import net.minecraft.core.BlockPos

class PositionRotationTest {

    private fun rotate(vararg ops: Char): (BlockPos) -> BlockPos {
        val rotation = PositionRotation()
        ops.forEach {
            when (it) {
                'L' -> rotation.rotateLeft()
                'R' -> rotation.rotateRight()
                'X' -> rotation.mirrorX()
                'Z' -> rotation.mirrorZ()
                else -> error("unknown step $it")
            }
        }
        return rotation::apply
    }

    private val point = BlockPos(1, 7, 3)

    @Test
    fun `each step moves the horizontal plane and leaves the height`() {
        assertEquals(BlockPos(3, 7, -1), rotate('L')(point))
        assertEquals(BlockPos(-3, 7, 1), rotate('R')(point))
        assertEquals(BlockPos(-1, 7, 3), rotate('X')(point))
        assertEquals(BlockPos(1, 7, -3), rotate('Z')(point))
    }

    @Test
    fun `steps compose in the order they are recorded`() {
        // two quarter turns the same way is a half turn, whichever way round
        assertEquals(BlockPos(-1, 7, -3), rotate('L', 'L')(point))
        assertEquals(BlockPos(-1, 7, -3), rotate('R', 'R')(point))
        // and mirroring both axes is the same half turn
        assertEquals(BlockPos(-1, 7, -3), rotate('X', 'Z')(point))
    }

    @Test
    fun `opposite steps cancel`() {
        assertEquals(point, rotate('L', 'R')(point))
        assertEquals(point, rotate('R', 'L')(point))
        assertEquals(point, rotate('X', 'X')(point))
        assertEquals(point, rotate('L', 'L', 'L', 'L')(point))
    }

    @Test
    fun `a mirror after a turn is not the same as before it`() {
        // order matters: this is what a composed transform has to get right
        assertEquals(BlockPos(-3, 7, -1), rotate('L', 'X')(point))
        assertEquals(BlockPos(3, 7, 1), rotate('X', 'L')(point))
    }
}

import io.gabzim.veb.VEBTree
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class VEBTreeTest {

    @Test
    fun `should return successor if tree is constructed in sorted order`() {
        val tree = VEBTree(64)
        tree.insert(1)
        tree.insert(3)
        tree.insert(17)
        tree.insert(19)
        tree.insert(63)
        assertEquals(tree.successor(17), 19)
    }

    @Test
    fun `should return successor if tree is constructed in sorted order but element is not in tree`() {
        val tree = VEBTree(64)
        tree.insert(1)
        tree.insert(3)
        tree.insert(17)
        tree.insert(19)
        tree.insert(63)
        assertEquals(tree.successor(18), 19)
    }

    @Test
    fun `should return successor if tree is constructed in reverse order`() {
        val tree = VEBTree(64)
        tree.insert(63)
        tree.insert(19)
        tree.insert(17)
        tree.insert(3)
        tree.insert(1)
        assertEquals(tree.successor(17), 19)
    }

    @Test
    fun `should return NONE if tree is empty`() {
        val tree = VEBTree(64)
        assertEquals(tree.successor(17), VEBTree.NONE)
    }

    @Test
    fun `should return NONE if there is no successor`() {
        val tree = VEBTree(64)
        tree.insert(17)
        assertEquals(tree.successor(17), VEBTree.NONE)
    }

    @Test
    fun `should return NONE if there item is last item in tree`() {
        val tree = VEBTree(64)
        tree.insert(0)
        tree.insert(8)
        tree.insert(16)
        tree.insert(32)
        assertEquals(tree.successor(32), VEBTree.NONE)
    }


}
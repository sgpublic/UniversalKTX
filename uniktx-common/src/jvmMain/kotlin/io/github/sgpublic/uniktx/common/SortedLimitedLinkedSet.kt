package io.github.sgpublic.uniktx.common

import java.util.LinkedList
import java.util.Spliterator

/**
 * 自动排序的哈希链表
 * @param limit 限制列表长度，小于等于 0 则不限制
 *
 * @author Madray Haven
 * @Date 2023/5/26 9:31
 */
class SortedLimitedLinkedSet<T: Comparable<T>>(
    private val limit: Int = -1,
): List<T>, Set<T>, MutableCollection<T> {
    private val data = LinkedList<T>()
    private val set = HashSet<T>(limit + 1)

    override fun clear() {
        data.clear()
        set.clear()
    }

    fun indexOf(operator: (T) -> Boolean): Int {
        val listIterator = data.iterator()
        var index = -1
        while (listIterator.hasNext()) {
            index += 1
            if (operator.invoke(listIterator.next())) {
                return index
            }
        }
        return -1
    }

    override fun addAll(elements: Collection<T>): Boolean {
        var result = false
        for (element in elements) {
            result = result || add(element)
        }
        return result
    }

    fun replaceForIndex(operator: (T) -> T?, onReplace: (Int, T) -> Unit) {
        val listIterator = data.listIterator()
        var index = -1
        while (listIterator.hasNext()) {
            index += 1
            val currentItem = listIterator.next()
            val newItem = operator.invoke(currentItem)
                ?: continue
            listIterator.set(newItem)
            set.remove(currentItem)
            set.add(newItem)
            onReplace.invoke(index, newItem)
        }
    }

    fun addForIndex(element: T): Int {
        if (set.contains(element)) {
            return -1
        }
        var index = -1
        for (existItem in data) {
            index += 1
            if (existItem >= element) {
                set.add(element)
                data.add(index, element)
                break
            }
        }
        if (!set.contains(element)) {
            index = data.size
            set.add(element)
            data.addLast(element)
        }
        while (limit in 1 until size) {
            set.remove(data.removeLast())
        }
        return index
    }

     fun removeForIndex(operator: (T) -> Boolean, onRemove: (Int, T) -> Unit) {
        val listIterator = data.listIterator()
        var index = -1
        while (listIterator.hasNext()) {
            index += 1
            val next = listIterator.next()
            if (operator.invoke(next)) {
                listIterator.remove()
                set.remove(next)
                onRemove.invoke(index, next)
            }
        }
    }

    override fun add(element: T): Boolean {
        return addForIndex(element) >= 0
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        data.removeAll(elements)
        set.removeAll(elements)
        return true
    }

    override fun remove(element: T): Boolean {
        data.remove(element)
        set.remove(element)
        return true
    }

    override val size: Int get() = data.size
    override fun get(index: Int): T = data[index]
    override fun indexOf(element: T): Int = data.indexOf(element)
    override fun isEmpty(): Boolean = data.isEmpty()
    override fun iterator(): MutableIterator<T> = data.iterator()
    override fun spliterator(): Spliterator<T> = data.spliterator()
    override fun listIterator(): MutableListIterator<T> = data.listIterator()
    override fun listIterator(index: Int): MutableListIterator<T> = data.listIterator()
    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> = data.subList(fromIndex, toIndex)
    override fun containsAll(elements: Collection<T>): Boolean = set.containsAll(elements)
    override fun contains(element: T): Boolean = set.contains(element)

    override fun toString(): String = data.toString()
    override fun hashCode(): Int = data.hashCode()
    override fun equals(other: Any?): Boolean {
        if (other !is SortedLimitedLinkedSet<*>) {
            return false
        }
        return set == other.set
    }

    override fun lastIndexOf(element: T): Int {
        throw UnsupportedOperationException()
    }
    override fun retainAll(elements: Collection<T>): Boolean {
        throw UnsupportedOperationException()
    }
}
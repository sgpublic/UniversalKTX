package io.github.sgpublic.uniktx.common

import java.util.*

fun <ItemT> PriorityQueue<ItemT>.toSortedList(): LinkedList<ItemT> {
    return LinkedList<ItemT>().also {
        while (isNotEmpty()) {
            it.add(poll() ?: continue)
        }
    }
}
package id.tiooooo.nontonterus.core.utils

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.Stack

infix fun Stack<Screen>.pushOnce(item: Screen) {
    val lastScreen = lastItemOrNull

    if (lastScreen == null || lastScreen::class != item::class) {
        push(item)
    }
}
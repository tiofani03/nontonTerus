package id.tiooooo.nontonterus.core.common

import org.koin.core.qualifier.named

val DefaultDispatcher = named("DefaultDispatcher")
val IoDispatcher = named("IoDispatcher")
val MainDispatcher = named("MainDispatcher")
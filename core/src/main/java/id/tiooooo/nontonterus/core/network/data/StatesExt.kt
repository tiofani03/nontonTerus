package id.tiooooo.nontonterus.core.network.data

inline fun <T> States<T>.onLoading(action: () -> Unit): States<T> {
    if (this is States.Loading) action()
    return this
}

inline fun <T> States<T>.onSuccess(action: (data: T) -> Unit): States<T> {
    if (this is States.Success) action(data)
    return this
}

inline fun <T> States<T>.onError(action: (message: String, meta: Map<String, Any?>) -> Unit): States<T> {
    if (this is States.Error) action(message, meta)
    return this
}

inline fun <T> States<T>.onEmpty(action: () -> Unit): States<T> {
    if (this is States.Empty) action()
    return this
}


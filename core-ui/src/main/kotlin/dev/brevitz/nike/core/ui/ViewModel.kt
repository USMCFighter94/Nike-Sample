package dev.brevitz.nike.core.ui

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlin.properties.Delegates

abstract class ViewModel<ViewState : Any>(initialState: ViewState) {
    val disposables = CompositeDisposable()

    private val stateSubject = BehaviorSubject.create<ViewState>()
    private var state: ViewState by Delegates.observable(initialState) { _, _, newState ->
        stateSubject.onNext(newState)
    }

    fun currentState() = state

    fun observe(): Observable<ViewState> = stateSubject.hide()

    fun updateState(f: (ViewState) -> ViewState) {
        state = f(state)
    }

    fun stop() {
        disposables.clear()
    }
}

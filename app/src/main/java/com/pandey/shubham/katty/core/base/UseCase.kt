package com.pandey.shubham.katty.core.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by shubhampandey
 */
abstract class UseCase<in T, out R> where R: Any {

    abstract suspend fun run(param: T): R

    operator fun invoke(params: T, scope: CoroutineScope = MainScope(), onResult: (R) -> Unit) {
        scope.launch {
            val deferred = async(Dispatchers.IO) {
                run(params)
            }
            onResult(deferred.await())
        }
    }
}
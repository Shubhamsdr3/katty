package com.pandey.shubham.katty.core.base

import com.pandey.shubham.katty.core.utils.DefaultScope
import kotlinx.coroutines.CoroutineScope

/**
 * Created by shubhampandey
 */


abstract class UseCase<in T, out R> where R: Any {

    abstract fun run(param: T?, scope: CoroutineScope): R

    operator fun invoke(params: T?, scope: CoroutineScope = DefaultScope()): R = run(params, scope)
}
package com.stepango.koptional

import java.util.NoSuchElementException

/**
 * A container object which may or may not contain a non-null value.
 * If a value is present, [isPresent] will return `true` and
 * [get] will return the value.
 *
 * Additional methods that depend on the presence or absence of a contained
 * value are provided, such as [orElse] (return a default value if value not present)
 * and [ifPresent] (execute a block of code if the value is present).
 *
 * This is a value-based class; use of identity-sensitive operations
 * (including reference equality (`==`), identity hash code, or synchronization)
 * on instances of [Optional] may have unpredictable results and should be avoided.
 */
sealed class Optional<T : Any>(
        /**
         * If `non-null`, the value; if `null`, indicates no value is present
         */
        protected val value: T? = null
) {

    /**
     * @return the non-null value held by this [Optional]
     * @throws NoSuchElementException if there is no value present
     * @see [isPresent]
     */
    fun get(): T = value ?: throw NoSuchElementException("No value present")

    /**
     * @return `true` if there is a value present, otherwise `false`
     */
    val isPresent: Boolean
        get() = value != null

    /**
     * If a value is present, invoke the specified consumer with the value,
     * otherwise do nothing.
     *
     * @param consumer block to be executed if a value is present
     * @return current [Optional] instance
     */
    inline fun ifPresent(consumer: (T) -> Unit) = apply { value?.let { consumer(value) } }

    /**
     * If a value is not present, invoke the specified action,
     * otherwise do nothing.
     *
     * @param action block to be executed if a value is not present
     * @return current [Optional] instance
     */
    inline fun ifEmpty(action: () -> Unit) = apply { if (!isPresent) action() }

    /**
     * If a value is present, and the value matches the given predicate,
     * return an [Optional] describing the value, otherwise return an
     * empty [Optional].
     *
     * @param predicate a predicate to apply to the value, if present
     * @return an [Optional] describing the value of this [Optional]
     * if a value is present and the value matches the given predicate,
     * otherwise an empty [Optional]
     */
    inline fun filter(predicate: (T) -> Boolean): Optional<T>
            = value?.let { if (predicate(it)) this else empty<T>() } ?: this

    /**
     * If a value is present, apply the provided mapping function to it,
     * and if the result is non-null, return an [Optional] describing the
     * result. Otherwise return an empty [Optional]
     *
     * This method supports post-processing on optional values, without
     * the need to explicitly check for a return status. For example, the
     * following code traverses a stream of file names, selects one that has
     * not yet been processed, and then opens that file, returning an
     * [Optional]
     *
     * ```
     *val fis = names.filter { name -> !isProcessedYet(name) }
     *               .findFirst()
     *               .map { name -> FileInputStream(name) }
     * ```
     *
     * Here, `findFirst` returns an Optional<String>, and then
     * `map` returns an `Optional<FileInputStream>` for the desired
     * file if one exists.
     *
     * @param U The type of the result of the mapping function
     * @param mapper a mapping function to apply to the value, if present
     * @return an [Optional] describing the result of applying a mapping
     * function to the value of this [Optional], if a value is present,
     * otherwise an empty [Optional]
     * @throws NullPointerException if the mapping function is null
     */
    inline fun <U : Any> map(mapper: (T) -> U): Optional<U>
            = value?.let { ofNullable(mapper(it)) } ?: empty<U>()

    /**
     * If a value is present, apply the provided [Optional]-bearing
     * mapping function to it, return that result, otherwise return an empty
     * [Optional].  This method is similar to [map],
     * but the provided mapper is one whose result is already an [Optional],
     * and if invoked, `flatMap` does not wrap it with an additional
     * [Optional].
     *
     * @param U The type parameter to the [Optional] returned by
     * @param mapper a mapping function to apply to the value, if present
     *           the mapping function
     * @return the result of applying an [Optional]-bearing mapping
     * function to the value of this [Optional], if a value is present,
     * otherwise an empty [Optional]
     * @throws NullPointerException if the mapping function is null or returns
     * a null result
     */
    inline fun <U : Any> flatMap(mapper: (T) -> Optional<U>): Optional<U>
            = value?.let { mapper(it) } ?: empty<U>()

    /**
     * @param other the value to be returned if there is no value present, may
     * be null
     * @return the value, if present, otherwise [other]
     */
    fun orElse(other: T): T = value ?: other

    /**
     * @param other a block whose result is returned if no value
     * is present
     * @return the value if present otherwise the result of [other]
     * @throws NullPointerException if value is not present and [other] is
     * null
     */
    fun orElseGet(other: () -> T): T = value ?: other()

    /**
     * Return the contained value, if present, otherwise throw an exception
     * to be created by the provided supplier.
     *
     * @param X Type of the exception to be thrown
     * @param exceptionSupplier The supplier which will return the exception to
     * be thrown
     * @return the present value
     * @throws X if there is no value present
     */
    inline fun <X : Throwable> orElseThrow(exceptionSupplier: () -> X): T
            = value ?: throw exceptionSupplier()

    /**
     * Indicates whether some other object is "equal to" this Optional. The
     * other object is considered equal if:
     * * it is also an [Optional] and;
     * * both instances have no value present or;
     * * the present values are "equal to" each other via [equals].
     *
     * @param other an object to be tested for equality
     * @return `true` if the other object is "equal to" this object
     * otherwise `false`
     */
    override fun equals(other: Any?): Boolean = when {
        this === other        -> true
        other !is Optional<*> -> false
        else                  -> value == other.value
    }

    override fun toString(): String = value?.let { "Optional[$value]" } ?: "Optional.EMPTY"

    override fun hashCode(): Int = value?.hashCode() ?: 0

    /**
     * Common instance for [empty].
     */
    object EMPTY : Optional<Nothing>()

    class Some<T : Any>(value: T) : Optional<T>(value)

    companion object {

        /**
         * Returns an empty [Optional] instance.  No value is present for this
         * Optional.
         *
         * Though it may be tempting to do so, avoid testing if an object
         * is empty by comparing with `==` against instances returned by
         * [empty]. There is no guarantee that it is a singleton.
         * Instead, use [isPresent]}.
         *
         * @param [T] Type of the non-existent value
         * @return an empty [Optional]
         */
        @Suppress("UNCHECKED_CAST") fun <T : Any> empty(): Optional<T> = EMPTY as Optional<T>

        /**
         * Returns an [Optional] with the specified present non-null value.
         *
         * @param [T] the class of the value
         * @param value the value to be present, which must be non-null
         * @return an [Optional] with the value present
         * @throws NullPointerException if value is null
         */
        fun <T : Any> of(value: T): Optional<T> = Some(value)

        /**
         * Returns an [Optional] describing the specified value, if non-null,
         * otherwise returns an [Optional.EMPTY].
         *
         * @param [T] the class of the value
         * @param value the possibly-null value to describe
         * @return an [Optional] with a present value if the specified value
         * is non-null, otherwise an empty [Optional]
         */
        fun <T : Any> ofNullable(value: T?): Optional<T> = value?.let { of(it) } ?: empty<T>()
    }

}

fun <T : Any> T?.toOptional() = Optional.ofNullable(this)

fun <T : Any> Optional<T>.orNull(): T? = if (isPresent) get() else null
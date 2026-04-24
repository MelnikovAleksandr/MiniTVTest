package ru.asmelnikov.minitvtest.domain

sealed class Resource<T>(
    val data: T? = null,
    val errors: ErrorsTypes? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(
        data: T? = null,
        errors: ErrorsTypes? = null
    ) :
        Resource<T>(data, errors)
}

sealed class ErrorsTypes(val errorMessage: String? = null) {
    data class ParseError(val message: String? = null) : ErrorsTypes(errorMessage = message)
    data class InsertDataBaseError(val message: String? = null) :
        ErrorsTypes(errorMessage = message)
    data class UnknownError(val message: String? = null) : ErrorsTypes(errorMessage = message)
}
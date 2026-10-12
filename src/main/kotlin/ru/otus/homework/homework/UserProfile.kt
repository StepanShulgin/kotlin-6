@file:Suppress("RemoveRedundantQualifierName")

package ru.otus.homework.homework

import kotlin.properties.Delegates

/**
 * Профиль пользователя
 */
interface UserProfile {
    /**
     * Полное имя
     * Не должно принимать пустые строки
     */
    var fullName: String

    /**
     * Email.
     * Не должен принимать пустые и некорректные строки
     */
    var email: String

    /**
     * Профиль с логированием
     */
    interface Logging : UserProfile, WithLogging

    companion object {
        /**
         * Создает профиль пользователя
         */
        fun create(fullName: String, email: String): UserProfile {
            require(fullName.isNotBlank()) { "Full name should not be empty" }
            require(email.isNotBlank() && emailRegex.matches(email)) { "Invalid email" }
            return ProfileImplementation(fullName, email)
        }

        /**
         * Creates user profile with logging
         */
        fun createWithLogging(fullName: String, email: String): UserProfile.Logging {
            return LoggingProfile(create(fullName, email))
        }
    }
}

/**
 * Проверка емейла на корректность
 */
private val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(?:\\.[A-Za-z0-9-]+)+$")

/**
 * Реализация простого [UserProfile].
 */
private class ProfileImplementation(fullName: String, email: String) : UserProfile {
    override var fullName: String by NonEmptyStringDelegate()

    override var email: String by Delegates.vetoable("") { _, _, newValue ->
        emailRegex.matches(newValue)
    }

    init {
        this.fullName = fullName
        this.email = email
    }
}

private class LoggingProfile(private val profile: UserProfile) : UserProfile.Logging, UserProfile by profile {
    private val log = mutableListOf<String>()

    override var fullName: String
        get() = profile.fullName
        set(value) {
            val oldValue = profile.fullName
            profile.fullName = value
            if (profile.fullName != oldValue) {
                log += "Changing `fullName` from '$oldValue' to '${profile.fullName}'"
            }
        }

    override var email: String
        get() = profile.email
        set(value) {
            val oldValue = profile.email
            profile.email = value
            if (profile.email != oldValue) {
                log += "Changing `email` from '$oldValue' to '${profile.email}'"
            }
        }

    override fun getLog(): List<String> = log.toList()
}

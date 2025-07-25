package com.multrm.shared.data

import com.multrm.shared.domain.KtorClient
import com.multrm.shared.domain.Operation
import com.multrm.shared.domain.models.CityWeather
import com.multrm.shared.domain.models.CityWeatherRemote
import com.multrm.shared.domain.models.toDomainCityWeather
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class KtorClientImpl (
    private val client: HttpClient
): KtorClient {
    override suspend fun getWeather(
        cityName: String,
        days: Int,
    ): Operation<CityWeather> = safeApiCall {
        client.get("forecast.json") {
            url {
                parameters.append("key", "25ea3a217a4741d299c100631250707")
                parameters.append("lang", "ru")
                parameters.append("q", cityName)
                parameters.append("days", days.toString())
            }
        }
            .body<CityWeatherRemote>()
            .toDomainCityWeather()
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): Operation<T> {
        return try {
            Operation.Success(apiCall())
        } catch (e: Exception) {
            Operation.Failure(e)
        }
    }
}
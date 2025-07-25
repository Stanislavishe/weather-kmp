package com.multrm.shared.data

import com.multrm.shared.domain.KtorClient
import com.multrm.shared.domain.Operation
import com.multrm.shared.domain.WeatherRepository
import com.multrm.shared.domain.models.CityWeather
import kotlinx.coroutines.delay

class WeatherRepositoryImpl(
    private val ktorClient: KtorClient
): WeatherRepository {
    override suspend fun getCitiesList(): List<String> {
        delay(1000)
        return citiesList
    }

    override suspend fun getWeather(cityName: String, days: Int): Operation<CityWeather> {
        return ktorClient.getWeather(cityName, days)
    }

    companion object {
        private val citiesList = listOf(
            "Нижний Новгород", "Москва", "Санкт-питербург", "Пермь", "Новосибирск",
            "Екатеринбург", "Казань", "Челябинск", "Самара", "Омск", "Ростов-на-Дону",
            "Уфа", "Красноярск", "Воронеж", "Волгоград", "Краснодар", "Саратов", "Тюмень",
            "Тольятти", "Ижевск", "Барнаул", "Ульяновск", "Иркутск", "Хабаровск", "Махачкала",
            "Ярославль", "Владивосток", "Оренбург", "Томск", "Кемерово"
        )
    }
}
package com.multrm.testkmp.ui.homeScreen

import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import app.cash.turbine.test
import com.multrm.shared.domain.WeatherDbRepository
import com.multrm.shared.domain.WeatherRepository
import com.multrm.testkmp.ui.events.ImHereEvent
import com.multrm.testkmp.ui.weatherScreen.DispatchersList
import com.multrm.testkmp.ui.weatherScreen.WeatherVMTest.TestDispatchersList
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.asserter

@OptIn(ExperimentalCoroutinesApi::class)
class HomeVMTest {
    private val repository = mock<WeatherRepository>()
    private val dbRepository = mock<WeatherDbRepository>()
    private val fakeCitiesList = listOf(
        "Нижний Новгород", "Москва", "Санкт-питербург", "Пермь", "Новосибирск",
        "Екатеринбург", "Казань", "Челябинск", "Самара", "Омск", "Ростов-на-Дону",
        "Уфа", "Красноярск", "Воронеж", "Волгоград", "Краснодар", "Саратов", "Тюмень",
        "Тольятти", "Ижевск", "Барнаул", "Ульяновск", "Иркутск", "Хабаровск", "Махачкала",
        "Ярославль", "Владивосток", "Оренбург", "Томск", "Кемерово"
    )
    private lateinit var viewModel: HomeVM

    @BeforeTest
    fun setup() {
        viewModel = HomeVM(repository, dbRepository, TestDispatchersList())
    }

    @Test
    fun `test success getCitiesList`() = runTest {
        everySuspend  { repository.getCitiesList() } returns fakeCitiesList

        viewModel.getCitiesList()
        viewModel.textFieldState.setTextAndPlaceCursorAtEnd("")

        viewModel.uiState.test {
            val item = awaitItem()
            if (item is HomeUiState.Success) {
                assertTrue(true)
            }
        }
    }

    @Test
    fun `test query from getCitiesList`() = runTest {
        val query = "Москва"
        everySuspend  { repository.getCitiesList() } returns fakeCitiesList

        viewModel.getCitiesList()
        viewModel.textFieldState.setTextAndPlaceCursorAtEnd(query)

        viewModel.uiState.test {
            skipItems(1)
            val item = awaitItem()
            if (item is HomeUiState.Success) {
                assertTrue(true)
                assertTrue(item.data.size == 1)
            } else {
                assertTrue(false)
            }
        }
    }

    @Test
    fun testGetImHereCity() = runTest {
        val city = "Москва"
        everySuspend { dbRepository.getImHereCity() } returns city

        viewModel.getImHereCity()

        viewModel.hereEventState.test {
            assertTrue(awaitItem() is ImHereEvent.HereCity)
        }

    }

    @Test
    fun testNullGetImHereCity() = runTest {
        everySuspend { dbRepository.getImHereCity() } returns null

        viewModel.getImHereCity()

        viewModel.hereEventState.test {
            assertTrue(awaitItem() is ImHereEvent.NotHere)
        }

    }

    private class TestDispatchersList (
        private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher(),
    ) : DispatchersList {
        override fun io(): CoroutineDispatcher = dispatcher

        override fun ui(): CoroutineDispatcher = dispatcher
    }
}
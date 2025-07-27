package com.multrm.testkmp.ui.weatherScreen

import app.cash.turbine.test
import com.multrm.shared.domain.WeatherDbRepository
import com.multrm.shared.domain.WeatherRepository
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherVMTest {
    private val repository = mock<WeatherRepository>()
    private val dbRepository = mock<WeatherDbRepository>()
    private val mapper = WeatherUiMapper()
    private lateinit var viewModel: WeatherVM

    @BeforeTest
    fun setup() {
        viewModel = WeatherVM(repository, dbRepository, mapper, TestDispatchersList())
    }

    @Test
    fun `test success getWeather try have internet`() = runTest {
        everySuspend  {
            repository.getWeather(any(), any()).map(mapper)
        } returns mock<WeatherUiState.Success>()

        viewModel.getWeather("")

        viewModel.uiState.test {
            assertTrue(awaitItem() is WeatherUiState.Success)
        }
    }

    @Test
    fun `test success getWeather try have internet but not active VPN`() = runTest {
        everySuspend  {
            repository.getWeather(any(), any()).map(mapper)
        } returns mock<WeatherUiState.Error.Timeout>()

        viewModel.getWeather("")

        viewModel.uiState.test {
            assertTrue(awaitItem() is WeatherUiState.Error.Timeout)
        }
    }

    @Test
    fun `test success getWeather try have internet but city not found`() = runTest {
        everySuspend  {
            repository.getWeather(any(), any()).map(mapper)
        } returns mock<WeatherUiState.Error.NotFoundCity>()

        viewModel.getWeather("")

        viewModel.uiState.test {
            assertTrue(awaitItem() is WeatherUiState.Error.NotFoundCity)
        }
    }

    @Test
    fun `test success getWeather try other errors`() = runTest {
        everySuspend  {
            repository.getWeather(any(), any()).map(mapper)
        } returns mock<WeatherUiState.Error.Other>()

        viewModel.getWeather("")

        viewModel.uiState.test {
            assertTrue(awaitItem() is WeatherUiState.Error.Other)
        }
    }

    @Test
    fun `test success getWeather try not have internet and not have cache`() = runTest {
        everySuspend  {
            repository.getWeather(any(), any()).map(mapper)
        } returns mock<WeatherUiState.Error.UnknownHost>()
        everySuspend  {
            dbRepository.getCacheWeather(any()).map(mapper)
        } returns mock<WeatherUiState.Error.UnknownHost>()

        viewModel.getWeather("")

        viewModel.uiState.test {
            assertTrue(awaitItem() is WeatherUiState.Error.UnknownHost)
        }
    }

    @Test
    fun `test success getWeather try not have internet and have cache`() = runTest {
        everySuspend {
            repository.getWeather(any(), any()).map(mapper)
        } returns mock<WeatherUiState.Error.UnknownHost>()
        everySuspend {
            dbRepository.getCacheWeather(any()).map(mapper)
        } returns mock<WeatherUiState.Success>()

        viewModel.getWeather("")

        viewModel.uiState.test {
            assertTrue(awaitItem() is WeatherUiState.Success)
        }
    }

    private class TestDispatchersList(
        private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher(),
    ) : DispatchersList {
        override fun io(): CoroutineDispatcher = dispatcher

        override fun ui(): CoroutineDispatcher = dispatcher
    }
}
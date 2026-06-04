package com.muindi.stephen.coopbanktest.presentation.cards

import app.cash.turbine.test
import com.muindi.stephen.coopbanktest.data.repository.FakeCardRepository
import com.muindi.stephen.coopbanktest.domain.common.Resource
import com.muindi.stephen.coopbanktest.domain.models.CardModel
import com.muindi.stephen.coopbanktest.domain.usecases.CardsUseCase
import com.muindi.stephen.coopbanktest.testutils.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CardsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepository: FakeCardRepository
    private lateinit var viewModel: CardsViewModel

    @Before
    fun setup() {
        fakeRepository = FakeCardRepository()
        viewModel = CardsViewModel(
            getCardsUseCase = CardsUseCase(fakeRepository)
        )
    }

    @Test
    fun `when cards load successfully, uiState contains cards`() = runTest {
        val cards = listOf(
            CardModel(
                balance = 100000.00,
                cardNumber = "4111 4444 4444 1234",
                creditLimit = 200000.00,
                currency = "KES",
                currentSpend = 15000.50,
                dueDate = "08/28",
                expiryDate = "08/28",
                holderName = "Wanjiku Kimani",
                id = "12345",
                linkedAccountName = "Stephen MM",
                name = "SAFARI TRAVEL CARD",
                status = CardStatus.BLOCKED,
                type = CardType.Debit,
                userId = "123",
                wallets = emptyList()
            ),
            CardModel(
                balance = 100000.00,
                cardNumber = "0901 3450 5555 4656",
                creditLimit = 200000.00,
                currency = "KES",
                currentSpend = 15000.50,
                dueDate = "08/28",
                expiryDate = "08/28",
                holderName = "Wanjiku Kimani",
                id = "12345",
                linkedAccountName = "Steve MK",
                name = "EVERYDAY CHECKING",
                status = CardStatus.ACTIVE,
                type = CardType.Prepaid,
                userId = "123",
                wallets = emptyList()
            )
        )

        fakeRepository.emitCards(cards)

        viewModel.allCards.test {

            val result = awaitItem()
            assert(result is Resource.Loading)

            val data = awaitItem()
            assert(data is Resource.Data)

            assert((data as Resource.Data).value.size == 2)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when error occurs, uiState has error`() = runTest {
        fakeRepository.emitError("No Cards")

        viewModel.allCards.test {

            val result = awaitItem()

            assert(result is Resource.Loading)

            val error = awaitItem()

            assert(error is Resource.Error)
            assert((error as Resource.Error).message == "No Cards")
            cancelAndIgnoreRemainingEvents()
        }
    }
}
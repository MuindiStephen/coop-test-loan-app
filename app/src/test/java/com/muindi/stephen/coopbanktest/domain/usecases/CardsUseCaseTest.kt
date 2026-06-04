package com.muindi.stephen.coopbanktest.domain.usecases

import com.muindi.stephen.coopbanktest.data.repository.FakeCardRepository
import com.muindi.stephen.coopbanktest.domain.common.Resource
import com.muindi.stephen.coopbanktest.domain.models.CardModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test


class CardsUseCaseTest {
    private val fakeCardRepository = FakeCardRepository()
    private val cardUseCase = CardsUseCase(fakeCardRepository)

    @Test
    fun `when repository emits cards, usecase emits same cards`() = runTest {
        val expectedCards = listOf(
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

        fakeCardRepository.emitCards(expectedCards)

        val result = cardUseCase().first()
        assertTrue(result is Resource.Data)
        assertEquals(
            expectedCards, (result as Resource.Data).value
        )

    }

    @Test
    fun `when repository emits error, usecase emits error`() = runTest {
        fakeCardRepository.emitError("Network Error")

        val result = cardUseCase().first()
        assertTrue(result is Resource.Error)
        assertEquals(
            "Network Error", (result as Resource.Error).message
        )
    }
}
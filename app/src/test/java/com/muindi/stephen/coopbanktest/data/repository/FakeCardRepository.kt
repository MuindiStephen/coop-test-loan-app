package com.muindi.stephen.coopbanktest.data.repository

import com.muindi.stephen.coopbanktest.domain.common.Resource
import com.muindi.stephen.coopbanktest.domain.models.CardModel
import com.muindi.stephen.coopbanktest.domain.repository.CardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


class FakeCardRepository : CardsRepository {

    private var cardsFlow = MutableStateFlow<Resource<List<CardModel>>>(Resource.Loading)

    /**
     * Test helper to return listOf cards
     */
    fun emitCards(cards: List<CardModel>) {
        cardsFlow.value = Resource.Data(cards)
    }

    /**
     *Test helper to handle or return an error message
     */
    fun emitError(message: String) {
        cardsFlow.value = Resource.Error(message)
    }


    override fun getCards(): Flow<Resource<List<CardModel>>> = cardsFlow

    override suspend fun getUser() = throw NotImplementedError()
    override suspend fun getTransactions(cardId: String, limit: Int) = throw NotImplementedError()
    override suspend fun toggleBlockUnblockCardStatus(cardId: String) {}

}
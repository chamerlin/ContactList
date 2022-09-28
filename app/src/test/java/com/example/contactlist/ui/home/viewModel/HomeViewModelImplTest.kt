package com.example.contactlist.ui.home.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.contactlist.MainCoroutineRule
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository
import com.jraska.livedata.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class HomeViewModelImplTest {
    private lateinit var viewModel: HomeViewModelImpl
    private val repo = mock<ContactRepository>()

    @Rule
    @JvmField
    val mainCoroutineRule = MainCoroutineRule()

    @Rule
    @JvmField
    val taskCoroutineRule = InstantTaskExecutorRule()

    @Before
    @OptIn(ExperimentalCoroutinesApi::class)
    fun setUp() {
        mainCoroutineRule.runBlockingTest {
            viewModel = HomeViewModelImpl(repo)
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `emptyScreen should be false if getContacts return something`() = runTest {
        mainCoroutineRule.runBlockingTest {
            Mockito.`when`(repo.getContacts()).thenReturn(listOf(Contact(0, "john doe", "0123456789")))
            viewModel.onViewCreated()
            viewModel.emptyScreen.test().assertValue { !it }
        }
    }

    @Test
    fun `contacts should return an empty list when getContacts is empty`() {
        mainCoroutineRule.runBlockingTest {
            Mockito.`when`(repo.getContacts()).thenReturn(emptyList())
            viewModel.onViewCreated()
            viewModel.contacts.test().assertValue{ it.isEmpty() }
        }
    }

    @Test
    fun `getContact should return empty screen if there is nothing`() {
        viewModel.getAllContact()
        viewModel.emptyScreen.test().assertValue { it }
    }

    @Test
    fun `contact should be deleted`() {
        viewModel.onDeleteClicked(0)
        viewModel.getAllContact()
        viewModel.contacts.test().assertValue { it.isNullOrEmpty() }
    }
}
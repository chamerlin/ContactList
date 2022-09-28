package com.example.contactlist.ui.contact.add.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.contactlist.MainCoroutineRule
import com.example.contactlist.data.repository.ContactRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock

class AddContactViewModelImplTest {
    private lateinit var viewModel: AddContactViewModelImpl
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
            viewModel = AddContactViewModelImpl(repo)
        }
    }

    @Test
    fun `addContact should return true when all input fields are filled`() {
        viewModel.name.value = "john doe"
        viewModel.phone.value = "0123456789"
        assertTrue(viewModel.save())
    }
}